package com.sfy.common;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

/**
 * @author: yuan
 * Date: 2018/10/25
 * Time: 11:36
 */
public class FileUtil {

    public static File multipartToFile(MultipartFile multfile) {

        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        return file;
    }



}
