package com.pluralsight.flink;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * Created by shifengyuan.
 * Date: 2017/6/27
 * Time: 10:18
 */
public class Top10Movies {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<Long, Double>> sorted =  env.readCsvFile("myweb/doc/movie-rating/ratings.csv")
                .ignoreFirstLine()
                .includeFields(false, true, true, false)
                .types(Long.class, Double.class)
                .groupBy(0)
                .reduceGroup(new GroupReduceFunction<Tuple2<Long, Double>, Tuple2<Long, Double>>() {
                    @Override
                    public void reduce(Iterable<Tuple2<Long, Double>> values,
                                       Collector<Tuple2<Long, Double>> out) throws Exception {
                        Long movieId = null;
                        double total = 0;
                        int count = 0;

                        for (Tuple2<Long, Double> val : values) {
                            movieId = val.f0;
                            total += val.f1;
                            count++;
                        }

                        if (count > 50) {
                            out.collect(new Tuple2<>(movieId, total / count));
                        }
                    }
                })
                .partitionCustom(new Partitioner<Double>() {
                    @Override
                    public int partition(Double key, int numPartitions) {
                        return key.intValue() - 1;  // 0-4号 partition处理，rating从1.0-5.0
                    }
                }, 1)   // 1 means the second field
                .setParallelism(5)
                .sortPartition(1, Order.DESCENDING)
                .mapPartition(new MapPartitionFunction<Tuple2<Long, Double>, Tuple2<Long, Double>>() {
                    @Override
                    public void mapPartition(Iterable<Tuple2<Long, Double>> values,
                                             Collector<Tuple2<Long, Double>> out) throws Exception {
                        Iterator<Tuple2<Long, Double>> iter = values.iterator();
                        for (int i = 0; i < 10 && iter.hasNext(); i++) {
                            out.collect(iter.next());
                        }
                    }
                })
                .sortPartition(1, Order.DESCENDING)
                .setParallelism(1)
                .mapPartition(new MapPartitionFunction<Tuple2<Long, Double>, Tuple2<Long, Double>>() {
                    @Override
                    public void mapPartition(Iterable<Tuple2<Long, Double>> values,
                                             Collector<Tuple2<Long, Double>> out) throws Exception {
                        Iterator<Tuple2<Long, Double>> iter = values.iterator();
                        for (int i = 0; i < 10 && iter.hasNext(); i++) {
                            out.collect(iter.next());
                        }
                    }
                });

        DataSet<Tuple2<Long, String>> movies = env.readCsvFile("myweb/doc/movie-rating/movies.csv")
                .ignoreFirstLine()
                .parseQuotedStrings('"')
                .ignoreInvalidLines()
                .includeFields(true, true, false)
                .types(Long.class, String.class);

        DataSet<Tuple3<Long, String, Double>> result = movies.join(sorted)
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Long,String>, Tuple2<Long,Double>, Tuple3<Long, String, Double>>() {
                    @Override
                    public Tuple3<Long, String, Double> join(Tuple2<Long, String> movie,
                                                             Tuple2<Long, Double> rating) throws Exception {
                        return new Tuple3<>(movie.f0, movie.f1, rating.f1);
                    }
                });

        result.print();
    }
}
