package com.pluralsight.flink;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.types.DoubleValue;
import org.apache.flink.types.StringValue;
import org.apache.flink.util.Collector;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 每种genres的rating
 * <p>
 * Created by shifengyuan.
 * Date: 2017/6/26
 * Time: 20:23
 */
public class OptimizedAverageRating {
    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple3<Long, String, String>> movies = env.readCsvFile("myweb/doc/movie-rating/movies.csv")
                .ignoreFirstLine()
                .parseQuotedStrings('"')
                .ignoreInvalidLines()
                .types(Long.class, String.class, String.class);

        DataSet<Tuple2<Long, Double>> ratings = env.readCsvFile("myweb/doc/movie-rating/ratings.csv")
                .ignoreFirstLine()
                .includeFields(false, true, true, false)
                .types(Long.class, Double.class);

        List<Tuple2<String, Double>> distribution = movies.join(ratings)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple3<Long, String, String>,
                        Tuple2<Long, Double>,
                        Tuple3<StringValue, StringValue, DoubleValue>>() {

                    // 使用flink封装的基本类型，优化提升效率
                    private StringValue movie_name = new StringValue();
                    private StringValue movie_genre = new StringValue();
                    private DoubleValue rating_score = new DoubleValue();
                    private Tuple3<StringValue, StringValue, DoubleValue> result = new Tuple3<>(movie_name,movie_genre,rating_score);

                    @Override
                    public Tuple3<StringValue, StringValue, DoubleValue> join(Tuple3<Long, String, String> movie,
                                                               Tuple2<Long, Double> rating) throws Exception {
                        movie_name.setValue(movie.f1);
                        movie_genre.setValue(movie.f2.split("\\|")[0]);
                        rating_score.setValue(rating.f1);
                        return result;
                    }
                })
                .groupBy(1) // genre
                .reduceGroup(new GroupReduceFunction<Tuple3<StringValue, StringValue, DoubleValue>,
                        Tuple2<String, Double>>() {
                    @Override
                    public void reduce(Iterable<Tuple3<StringValue, StringValue, DoubleValue>> values,
                                       Collector<Tuple2<String, Double>> out) throws Exception {
                        String genre = null;
                        int count = 0;
                        double totalScore = 0;

                        for (Tuple3<StringValue, StringValue, DoubleValue> movie : values) {
                            genre = movie.f1.getValue();
                            totalScore += movie.f2.getValue();
                            count++;
                        }

                        out.collect(new Tuple2<String, Double>(genre, totalScore / count));
                    }
                })
                .collect();

        String res = distribution.stream()
                .sorted(Comparator.comparingDouble(r -> r.f1))
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        System.out.println(res);
    }
}
