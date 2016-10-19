package com.my.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yexianxun on 2016/10/17.
 */
public class Server {

    public static void main(String[] args) {
        try{
            ServerSocket server = null;
            try {
                server = new ServerSocket(4700);
                //创建一个ServerSocket在端口4700监听客户请求
            } catch (Exception e) {
                System.out.println("can not listen to:" + e);
                //出错，打印出错信息
            }
            Socket socket = null;
            try {
                socket = server.accept();
                //使用accept()阻塞等待客户请求，有客户
                //请求到来则产生一个Socket对象，并继续执行
            } catch (Exception e) {
                System.out.println("Error." + e);
//出错，打印出错信息
            }
            String line;
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Client:" + is.readLine());
            line = sin.readLine();
            while (!line.equals("bye")) {
                os.println(line);
                os.flush();
                System.out.println("Server:" + line);
                System.out.println("Client:" + is.readLine());
                line = sin.readLine();
            } //继续循环
            os.close(); //关闭Socket输出流
            is.close(); //关闭Socket输入流
            socket.close(); //关闭Socket
            server.close(); //关闭ServerSocket
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

}
