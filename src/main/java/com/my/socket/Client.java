package com.my.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by yexianxun on 2016/10/17.
 */
public class Client {
    private static final String ip = "127.0.0.1";
    private static final int port = 4700;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(ip, port);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter os = new PrintWriter(socket.getOutputStream());
        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String readLine = console.readLine(); //从系统标准输入读入一字符串
        while (!readLine.equals("bye")) {
            os.println(readLine);
            os.flush();
            System.out.println("Client:" + readLine);
            System.out.println("Server:" + is.readLine());
            readLine = console.readLine();

        }

        os.close(); //关闭Socket输出流

        is.close(); //关闭Socket输入流

        socket.close(); //关闭Socket
    }
}
