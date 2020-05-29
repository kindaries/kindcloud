package top.aries.kind.thread;

import top.aries.kind.panel.ServerJPanel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 客户端线程
 * <p>
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-30 13:49
 * Created by IntelliJ IDEA.
 */
public class ClientThread extends Thread {

    private ArrayList<ClientThread> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String userName;
    private JTextArea contentArea;      //消息框
    private DefaultListModel listModel; //用户列表

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public String getUserName() {
        return userName;
    }

    //客户端线程的构造方法
    public ClientThread(ServerJPanel serverJPanel) {
        try {
            this.clients = serverJPanel.serverThread.clients;
            this.socket = serverJPanel.serverThread.socket;
            this.contentArea = serverJPanel.contentArea;
            this.listModel = serverJPanel.listModel;
            reader = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            //接收客户端的基本用户信息
            String inf = reader.readLine();
            StringTokenizer st = new StringTokenizer(inf, "@");
            userName = st.nextToken();
            // 反馈连接成功信息
            writer.println(userName + "与服务器连接成功！");
            writer.flush();
            //反馈当前在线用户信息
            if (clients.size() > 0) {
                String temp = "";
                for (int i = clients.size() - 1; i >= 0; i--) {
                    temp += clients.get(i).getUserName() + "@";
                }
                writer.println("USERLIST@" + clients.size() + "@" + temp);
                writer.flush();
            }
            //向所有在线用户发送该用户上线命令
            for (int i = clients.size() - 1; i >= 0; i--) {
                clients.get(i).getWriter().println("ADD@" + userName);
                clients.get(i).getWriter().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            contentArea.append("客户端构造异常！\r\n");
            contentArea.selectAll();              //滚动条拉到最下
        }
    }

    @SuppressWarnings("deprecation")
    public void run() {         //不断接收服务器的消息，进行处理。
        String message = null;
        while (true) {
            try {
                message = reader.readLine();    //接收服务器消息
            } catch (IOException e) {
                message = "CLOSE";
            }

            if (("CLOSE").equals(message)) {
                contentArea.append(userName + "下线！\r\n");
                contentArea.selectAll();              //滚动条拉到最下
                // 断开连接释放资源
                try {
                    reader.close();
                    writer.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();        //资源释放异常
                    contentArea.append("客户端资源释放异常！\r\n");
                    contentArea.selectAll();              //滚动条拉到最下
                }

                // 向所有在线用户发送该用户的下线命令
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println("DELETE@" + userName);
                    clients.get(i).getWriter().flush();
                }

                listModel.removeElement(userName);// 更新在线列表

                // 删除此条客户端服务线程
                for (int i = clients.size() - 1; i >= 0; i--) {
                    if (clients.get(i).getUserName() == userName) {
                        ClientThread temp = clients.get(i);
                        clients.remove(i);// 删除此用户的服务线程
                        temp.stop();// 停止这条服务线程
                        return;
                    }
                }
            } else if (message.indexOf("TANKONLINE@") > -1) {
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println(message);
                    clients.get(i).getWriter().flush();
                }
            } else {
                dispatcherMessage(message);// 转发消息
            }
        }
    }

    // 转发消息
    public void dispatcherMessage(String message) {
        StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
        String source = stringTokenizer.nextToken();
        String owner = stringTokenizer.nextToken();
        String content = stringTokenizer.nextToken();
        message = source + "说：\r\n" + content;
        contentArea.append(message + "\r\n");
        contentArea.selectAll();              //滚动条拉到最下
        if (owner.equals("ALL")) {// 群发
            for (int i = clients.size() - 1; i >= 0; i--) {
                clients.get(i).getWriter().println(message + "(群发)");
                clients.get(i).getWriter().flush();
            }
        }
    }
}
