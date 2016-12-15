package com.my.java.abs;

import java.util.Set;

/**
 * Created by yexianxun on 2016/11/23.
 */
public abstract class Car {

    public abstract Set<String> getNames();

    public void listName() {
        Set<String> names = getNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println(drive());
    }

    public abstract String drive();
}
