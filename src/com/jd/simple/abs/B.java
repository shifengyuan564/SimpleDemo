package com.jd.simple.abs;

public class B extends A {

    @Override
    public String abst() {
        return this.getClass().getName();
    }
}
