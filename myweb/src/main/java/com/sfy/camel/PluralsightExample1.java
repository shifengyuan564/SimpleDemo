package com.sfy.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by shifengyuan.
 * Date: 2016/10/24
 * Time: 20:28
 */
public class PluralsightExample1 implements Processor {

    /* example 1.1 */
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody("PluralsightExample1 ------ " + new Date().toString());
    }

    /* example 1.2 (跑这个例子不影响1.1) */
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://E:/logs/fulfillmentcenter1/out?noop=true")
                .to("file://E:/logs/fulfillmentcenter1/out/test");
            }
        });

    }
}
