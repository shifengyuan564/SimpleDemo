package com.sfy.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sfy.common.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Description:测试各种插件
 * Author: shifengyuan@jd.com
 * Date: 2016/1/10
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final static Log logger = LogFactory.getLog(TestController.class);

    private final static String BUCKET = "icepicture";
    public static final String QINIU_DOMAIN = "http://ph17xx7d2.bkt.clouddn.com/";
    public static Auth auth = null;

    static {
        String access_key = "fXQupGnjT646qKI3Za8zn5_nc_u648hFzTDZhaLK";
        String secret_key = "uNFy9_Foff3Xf2p1fG2s23dowryMZEYz_Igm7s6U";
        auth = Auth.create(access_key, secret_key);
    }


    @RequestMapping(value = "/jsontest", method = RequestMethod.GET)
    public String index(@RequestParam(value = "locale", required = false) Locale locale,
                        HttpServletRequest request, HttpServletResponse response, Model view) {

        logger.debug("-------------------------进入index.vm---------------------------");
        return "test/jsonTest";
    }

    @RequestMapping(value = "/typeahead", method = RequestMethod.GET)
    public String typeahead(HttpServletRequest request, HttpServletResponse response, Model view) {

        logger.debug("-------------------------进入typeahead-demo.vm---------------------------");
        return "test/typeahead-demo";
    }

    @RequestMapping(value = "/datagrid", method = RequestMethod.GET)
    public String datagrid() {

        logger.debug("-------------------------进入datagridJQ.vm---------------------------");
        return "test/datagridJQ";
    }

    @RequestMapping(value = "/liyi", method = RequestMethod.GET)
    public String liyi() {
        return "test/liyi";
    }

    @RequestMapping(value = "/haiguan", method = RequestMethod.GET)
    public String haiguan() {
        return "test/haiguan";
    }


    @RequestMapping(value = "/bigscreen", method = RequestMethod.GET)
    public String bigscreen() {
        return "test/bigscreen";
    }

    // 上传到七牛云
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String qiniuUpload(@RequestParam("file") MultipartFile multipartFile) {

        return upload2Qiniu(multipartFile ,BUCKET);
    }

    /**
     * @param file 		待上传的文件
     * @param bucket 七牛上的存储空间
     *
     * @return 七牛上显示的文件名
     */
    private String upload2Qiniu(MultipartFile file, String bucket) {

        String imgUrl="";

        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            byte[] uploadBytes = file.getBytes();
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, file.getOriginalFilename(), upToken);

                if (response.isOK()) {
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    System.out.println("----" + QINIU_DOMAIN+putRet.key);
                    imgUrl = QINIU_DOMAIN +  putRet.key;
                }

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return imgUrl;
    }
}
