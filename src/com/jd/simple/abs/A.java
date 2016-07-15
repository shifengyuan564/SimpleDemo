package com.jd.simple.abs;

public abstract class A {

    public abstract String abst();

    public String fun() {
        System.out.println(this.getClass().getName());
        return abst();
    }
}
