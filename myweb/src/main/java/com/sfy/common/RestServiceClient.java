package com.sfy.common;

import com.google.gson.Gson;
import com.sfy.domain.Code;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by shifengyuan.
 * Date: 2016/9/20
 * Time: 9:01
 * <p>
 * java后台调用Rest服务的客户端
 */
public class RestServiceClient {

    private transient final Logger logger = Logger.getLogger(getClass());

    private static ResourceBundle rb = null;
    private static String queryURI = null;

    static {
        rb = ResourceBundle.getBundle("rest-service");
        queryURI = rb.getString("opensinglewindow.cus.uri");
    }


    public static Code getName(String dictName, String key) {
        RestTemplate restTemplate = new RestTemplate();
        queryURI = queryURI + dictName + "/name?key={key}";

        Code result = restTemplate.getForObject(queryURI, Code.class, key);
        return result;
    }

    /* 测试 */
    public static void main(String[] args) {
        System.out.println(RestServiceClient.getName("c003","0100").getName());
    }
}
