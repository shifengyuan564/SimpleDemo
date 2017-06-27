package com.pluralsight.flink;

import org.apache.flink.api.common.functions.Partitioner;
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

    public static class MyPartitioner implements Partitioner<Integer> {
        @Override
        public int partition(Integer key, int numPartitions) {
            return key % numPartitions;
        }
    }

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<Integer, String>> data = env.fromElements(
                new Tuple2<>(1, "a"),
                new Tuple2<>(1000, "b"),
                new Tuple2<>(2, "c"),
                new Tuple2<>(1000, "d"),
                new Tuple2<>(3, "e"),
                new Tuple2<>(1000, "f"),
                new Tuple2<>(4, "g"));

        DataSet<Tuple2<Integer, String>> partitionedData = data.partitionCustom(new MyPartitioner(), 0);
        partitionedData.print();
    }
}
