package top.aries.kind.socket;

import top.aries.kind.swing.ServerJFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-29 16:32
 * Created by IntelliJ IDEA.
 */
public class HelloServer {

    public HelloServer(String userName) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(8888);
                    System.out.println("启动服务器....");
                    Socket s = ss.accept();
                    System.out.println("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");

                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    //读取客户端发送来的消息
                    String mess = br.readLine();
                    System.out.println("客户端：" + mess);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    bw.write(mess + "\n");
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
