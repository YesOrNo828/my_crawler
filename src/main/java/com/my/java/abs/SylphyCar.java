package com.my.java.abs;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yexianxun on 2016/11/23.
 */
public class SylphyCar extends Car {
    protected static Set<String> names = new HashSet<>();
    static {
        names.add("sylphy");
        names.add("轩逸");
    }

    @Override
    public Set<String> getNames() {
        return names;
    }

    @Override
    public String drive() {
        return "轩逸 is driving";
    }
}
