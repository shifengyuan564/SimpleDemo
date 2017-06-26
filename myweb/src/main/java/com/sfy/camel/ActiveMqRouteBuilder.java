package com.sfy.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shifengyuan.
 * Date: 2016/10/17
 * Time: 19:14
 */
public class ActiveMqRouteBuilder extends RouteBuilder {
    public void configure() {
        from("activemq:queue:start")
        .to("bean:testBean?method=hello")
        .to("stream:out");
    }

    public static final void main(String[] args) throws Exception {
        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("spring-config-camel.xml");
        CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
        try {
            camelContext.start();
            ProducerTemplate template = camelContext.createProducerTemplate();
            for (int i = 0; i < 5; i++) {
                template.sendBody("activemq:queue:start", "body" + i);
            }
            Thread.sleep(1000);
        } finally {
            camelContext.stop();
            appContext.close();
        }
    }
}
