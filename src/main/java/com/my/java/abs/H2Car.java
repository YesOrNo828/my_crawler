package com.my.java.abs;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yexianxun on 2016/11/23.
 */
public class H2Car extends Car {

    protected static Set<String> names = new HashSet<>();

    static {
        names.add("h2");
        names.add("哈弗2");
    }

    @Override
    public Set<String> getNames() {
        return names;
    }

    @Override
    public String drive() {
        return "哈弗2 is driving";
    }
}
