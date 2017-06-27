package com.pluralsight.flink;

import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Flink Custom Partitioner Example
 * <p>
 * Created by shifengyuan.
 * Date: 2017/6/27
 * Time: 10:38
 */

public class PartitionCustomExample {

    public static class MyPartitioner implements Partitioner<Double> {
        @Override
        public int partition(Double key, int numPartitions) {
            return key.intValue() - 1;
        }
    }

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<Integer, Double>> data = env.fromElements(
                new Tuple2<>(1, 3.9451754385964914),
                new Tuple2<>(2, 3.515873015873016),
                new Tuple2<>(3, 4.071969696969697),
                new Tuple2<>(4, 3.75),
                new Tuple2<>(5, 4.30327868852459),
                new Tuple2<>(6, 4.037671232876712),
                new Tuple2<>(7, 4.093333333333334),
                new Tuple2<>(8, 3.988188976377952),
                new Tuple2<>(9, 4.166666666666667),
                new Tuple2<>(10,4.0158730158730161),
                new Tuple2<>(11, 2.9691358024691357),
                new Tuple2<>(12, 4.20952380952381),
                new Tuple2<>(13, 4.20952380952381),
                new Tuple2<>(14, 2.8706896551724137)
        );

        DataSet<Tuple2<Integer, Double>> partitionedData = data.partitionCustom(new MyPartitioner(), 1)
                .sortPartition(1, Order.DESCENDING) // 每个partition内部倒序
                .setParallelism(1);// 利用1个并行把最终的结果集倒序


        partitionedData.print();
    }
}
