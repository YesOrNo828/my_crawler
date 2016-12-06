package com.my.java.abs;

/**
 * Created by yexianxun on 2016/11/23.
 */
public class Test {

    public static void main(String[] args) {
        SylphyCar sylphyCar = new SylphyCar();
        H2Car h2Car = new H2Car();
        System.out.println("11111111111");
        sylphyCar.listName();
        sylphyCar.drive();
        System.out.println("22222222222");
        h2Car.listName();
        h2Car.drive();
        System.out.println("33333333333");
        sylphyCar.listName();
        sylphyCar.drive();

    }
}
