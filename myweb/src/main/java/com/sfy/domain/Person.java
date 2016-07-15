package com.sfy.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: person的实体类（必须有set方法，否则controller里返回的ResponseBody就没用了）
 * Author: shifengyuan@jd.com
 * Date: 2016/1/7
 */
public class Person implements Serializable {

    private int id;
    private String name;
    private boolean status;

    private Date birth;
    private double score;

    public Person(int id, String name, boolean status, Date birth) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.birth = birth;
    }

    public Person(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
