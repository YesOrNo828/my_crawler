package com.my.cahce;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by yexianxun@corp.netease.com on 2018/5/22.
 */
@Service
public class UserService {

    @Cacheable(value = "userCache")
    public User getUser(int id) {
        return queryUser(id);
    }

    private User queryUser(int id) {
        System.out.println("query db id=" + id);
        return new User(id, "name_" + id);
    }

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring/*.xml");
        UserService userService = (UserService) ac.getBean("userService");
        User user = userService.getUser(1);
        System.out.println(user);
        user = userService.getUser(1);
        System.out.println(user);

    }
}
