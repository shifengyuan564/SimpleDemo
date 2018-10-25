package com.sfy.camel;

/**
 * Created by shifengyuan.
 * Date: 2016/10/17
 * Time: 18:15
 */
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SimpleRouteBuilder extends RouteBuilder {
    public void configure() {
        from("timer:foo?period=1s")
        .transform()
        .simple("Heartbeat ${date:now:yyyy-MM-dd HH:mm:ss}")
        .to("stream:out");
    }

    public static final void main(String[] args) throws Exception {
        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring-config-camel.xml");
        CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
        try {
            camelContext.start();
            Thread.sleep(4000);
        } finally {
            camelContext.stop();
            appContext.close();
        }
    }
}
