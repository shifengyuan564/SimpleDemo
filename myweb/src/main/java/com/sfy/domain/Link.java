package com.sfy.domain;

import java.io.Serializable;

/**
 * Description: 链接 实体类
 * Author: shifengyuan@jd.com
 * Date: 2016/3/16
 */
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
