package com.sfy.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.core.env.Environment;

import javax.inject.Inject;


/**
 * Created by shifengyuan.
 * Date: 2016/10/17
 * Time: 13:27
 */
public class FileMoveWithCamel {

    public static void main(String args[]) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("file:E:/camel-inbox/?delay=30000")
                .to("file:E:/camel-outbox");
            }
        });
        context.start();
        boolean loop =true;
        while(loop){
            Thread.sleep(25000);
            loop = false;
        }
        context.stop();
    }
}
