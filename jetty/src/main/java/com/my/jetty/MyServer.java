package com.my.jetty;

import com.my.jetty.handler.HelloHandler;
//import com.my.jetty.servlet.MyEchoServlet;
import com.my.jetty.servlet.MyEchoServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by yexianxun on 2016/12/9.
 */
public class MyServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
//        server.setHandler(new HelloHandler());
        try {
            HandlerList handlerList = new HandlerList();
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
//            context.addServlet(new ServletHolder(new DefaultServlet()), "/");
            context.addServlet(new ServletHolder(new MyEchoServlet()), "/echo");
            handlerList.addHandler(context);
//            handlerList.addHandler(new DefaultHandler());
            handlerList.addHandler(new HelloHandler());
            server.setHandler(handlerList);

            server.start();
            server.join();
        } catch (Exception e) {

            e.printStackTrace();
        }
        System.out.println(MyServer.class + "has started");
    }
}
