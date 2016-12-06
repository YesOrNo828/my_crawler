package com.my.common.db.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by yexianxun on 2016/11/15.
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM TEST");
        statement.close();
        connection.close();
    }
}
