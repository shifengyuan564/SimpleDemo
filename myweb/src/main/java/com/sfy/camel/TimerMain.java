package com.sfy.camel;

/**
 * Created by shifengyuan.
 * Date: 2016/10/17
 * Time: 9:26
 */

import org.slf4j.*;
import org.apache.camel.*;
import org.apache.camel.impl.*;
import org.apache.camel.builder.*;


import org.slf4j.*;
import org.apache.camel.builder.*;
import org.apache.camel.main.Main;

public class TimerMain extends Main {
    static Logger LOG = LoggerFactory.getLogger(TimerMain.class);

    public static void main(String[] args) throws Exception {
        TimerMain main = new TimerMain();
        main.enableHangupSupport();
        main.addRouteBuilder(createRouteBuilder());
        main.run(args);
    }

    static RouteBuilder createRouteBuilder() {
        return new TimerRouteBuilder();
    }
}
