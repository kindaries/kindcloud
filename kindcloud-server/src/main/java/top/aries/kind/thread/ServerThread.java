package top.aries.kind.thread;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 服务器线程
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-30 13:45
 * Created by IntelliJ IDEA.
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private ArrayList<ClientThread> clients;    //客户端列表

    private DefaultListModel listModel; //用户列表
    private JTextArea contentArea;      //消息框

    private final int MAX_CLIENT = 3;          //人数上限

    // 服务器线程的构造方法
    public ServerThread(ServerSocket serverSocket, DefaultListModel listModel, JTextArea contentArea) {
        this.serverSocket = serverSocket;
        this.listModel = listModel;
        this.contentArea = contentArea;
        this.clients = new ArrayList<>();
    }

    public ArrayList<ClientThread> getClients() {
        return clients;
    }

    public void run() {
        while (true) {// 不停的等待客户端的链接
            try {
                Socket socket = serverSocket.accept();
                if (clients.size() == MAX_CLIENT) {         //如果已达人数上限
                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    PrintWriter w = new PrintWriter(socket.getOutputStream());
                    // 接收客户端的基本用户信息
                    String inf = r.readLine();
                    StringTokenizer st = new StringTokenizer(inf, "@");
                    String userName = st.nextToken();
                    // 反馈连接成功信息
                    w.println("MAX@服务器：对不起，" + userName + "，服务器在线人数已达上限，请稍后尝试连接！");
                    w.flush();
                    // 释放资源
                    r.close();
                    w.close();
                    socket.close();
                    continue;
                }
                ClientThread client = new ClientThread(socket, clients, contentArea, listModel);
                client.start();// 开启对此客户端服务的线程
                clients.add(client);
                listModel.addElement(client.getUserName());// 更新在线列表
                contentArea.append(client.getUserName() + "上线！\r\n");
                contentArea.selectAll();              //滚动条拉到最下
            } catch (IOException e) {
                e.printStackTrace();
                contentArea.append("服务器运行异常！\r\n");
                contentArea.selectAll();              //滚动条拉到最下
            }
        }
    }

}
