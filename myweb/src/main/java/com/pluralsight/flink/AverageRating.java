package com.pluralsight.flink;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 每种genres的rating
 * <p>
 * Created by shifengyuan.
 * Date: 2017/6/26
 * Time: 20:23
 */
public class AverageRating {
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
                .with(new JoinFunction<Tuple3<Long, String, String>, Tuple2<Long, Double>, Tuple3<String, String, Double>>() {
                    @Override
                    public Tuple3<String, String, Double> join(Tuple3<Long, String, String> movie,
                                                               Tuple2<Long, Double> rating) throws Exception {
                        String movieName = movie.f1;
                        String movieGenre = movie.f2.split("\\|")[0];
                        Double ratingScore = rating.f1;
                        return new Tuple3<>(movieName, movieGenre, ratingScore);
                    }
                })
                .groupBy(1) // genre
                .reduceGroup(new GroupReduceFunction<Tuple3<String, String, Double>, Tuple2<String, Double>>() {
                    @Override
                    public void reduce(Iterable<Tuple3<String, String, Double>> iterable,
                                       Collector<Tuple2<String, Double>> collector) throws Exception {
                        String genre = null;
                        int count = 0;
                        double totalScore = 0;

                        for (Tuple3<String, String, Double> movie : iterable) {
                            genre = movie.f1;
                            totalScore += movie.f2;
                            count++;
                        }

                        collector.collect(new Tuple2<String, Double>(genre, totalScore / count));
                    }
                })
                .collect();

        String res = distribution.stream()
                .sorted((r1,r2) -> Double.compare(r1.f1, r2.f1))
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        System.out.println(res);
    }
}
