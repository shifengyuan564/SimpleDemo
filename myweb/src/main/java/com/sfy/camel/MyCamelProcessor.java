package com.sfy.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by shifengyuan.
 * Date: 2016/10/17
 * Time: 14:25
 */

public class MyCamelProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody("MyCamelProcessor ------ " + new Date().toString());
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-config-camel.xml");
        SpringCamelContext camel = (SpringCamelContext) ac.getBean("myCamel");

        camel.startRoute("myCamelRoute");  // now start Camel manually
        System.in.read();
    }
}
