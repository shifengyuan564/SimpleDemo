package com.pluralsight.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by shifengyuan.
 * Date: 2017/7/1
 * Time: 15:18
 */
public class FilterStrings {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 开启netcat，输入命令：nc -l -p 9999
        DataStream<String> dataStream = env.socketTextStream("localhost", 9999)
                .filter(new FilterFunction<String>() {
                            @Override
                            public boolean filter(String input) throws Exception {
                                try {
                                    Double.parseDouble(input.trim());
                                    return true;
                                } catch (Exception ex) {
                                }

                                return false;
                            }
                        }
                );

        dataStream.print();
        env.execute();
    }
}
