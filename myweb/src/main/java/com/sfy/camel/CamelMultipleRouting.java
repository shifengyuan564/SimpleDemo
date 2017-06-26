package com.sfy.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shifengyuan.
 * Date: 2016/10/17
 * Time: 18:47
 */
public class CamelMultipleRouting {
    public static final void main(String[] args) throws Exception {
        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("spring-config-camel.xml");
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
