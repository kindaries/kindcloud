package top.aries.kind.thread;

import top.aries.kind.panel.ServerJPanel;

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

    public ServerSocket serverSocket;
    public Socket socket;

    public ArrayList<ClientThread> clients;    //客户端列表

    private ServerJPanel serverJPanel;         //服务器面板

    private final int MAX_CLIENT = 30;          //人数上限

    // 服务器线程的构造方法
    public ServerThread(ServerJPanel serverJPanel) throws IOException {
        this.serverSocket = new ServerSocket(8888);
        this.clients = new ArrayList<>();
        this.serverJPanel = serverJPanel;
    }

    public void run() {
        while (true) {    //不停的等待客户端的链接
            try {
                socket = serverSocket.accept();
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
                ClientThread client = new ClientThread(serverJPanel);
                client.start();// 开启对此客户端服务的线程
                clients.add(client);
                serverJPanel.listModel.addElement(client.getUserName());// 更新在线列表
                serverJPanel.contentArea.append(client.getUserName() + "上线！\r\n");
                serverJPanel.contentArea.selectAll();              //滚动条拉到最下
            } catch (IOException e) {
                e.printStackTrace();
                serverJPanel.contentArea.append("服务器运行异常！\r\n");
                serverJPanel.contentArea.selectAll();              //滚动条拉到最下
                serverJPanel.isStart = false;
                return;
            }
        }
    }

}
