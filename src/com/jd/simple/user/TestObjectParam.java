package com.jd.simple.user;

/**
 * Description: 当把对象作为参数传到函数中，并更改该对象，是否影响原对象
 *
 * Author: shifengyuan@jd.com
 * Date: 2015/12/2
 */
public class TestObjectParam {

    public static void changeUserAge(User user) {
        user.setAge(15);
    }

    public static void main(String args[]) {

        User u = new User();
        u.setAge(10);

        System.out.println("Before : " + u.getAge());   // Before : 10
        changeUserAge(u);
        System.out.println("After : " + u.getAge());    // After : 15   （说明是引用传递）

    }
}
