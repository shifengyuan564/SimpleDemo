package com.sfy.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.Date;

/**
 以下程序实现如下功能：
 1，使用timer组件，每隔1s发送一次消息.
 2，消息在到达to之前，被Processor劫持，Processor添加了当前时间作为消息内容。
 3，使用stream component，将接收到的消息打印到控制台。
 4，关于exchange，是用来交换的对象，通过exchange.getIn()可以获取从from传递过来的Message，exchange.getOut()或以设置将要发给to的Message。
 Message又分为headers和body，类似于html协议中的头协议和协议内容。headers是一个Map< String, Object >，body可以是任意Object。
 */

public class CamelHelloWorld {


    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext(); // 1. 创建 CamelContext.
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("timer://foo?fixedRate=true&period=1000").
                process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(new Date());
                    }
                }).
                to("stream:out"); // 2. 为路由配置组件或终端节点.
            }
        }); // 3. 添加路由到CamelContext

        context.setTracing(true);
        context.start(); // 4. 启动CamelContext.
        Thread.sleep(Integer.MAX_VALUE);  // 为了保持CamelContext处于工作状态，这里需要sleep主线程
        context.stop(); // 最后停止CamelContext
    }
}
