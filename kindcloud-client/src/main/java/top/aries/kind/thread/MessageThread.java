package top.aries.kind.thread;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * 接收消息线程
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-30 15:56
 * Created by IntelliJ IDEA.
 */
public class MessageThread extends Thread {

    private boolean isConnected;    //是否连接上服务器
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private JDesktopPane table;
    private DefaultListModel listModel; //用户列表
    private JTextArea textArea;

    // 接收消息线程的构造方法
    public MessageThread(Socket socket, PrintWriter writer, BufferedReader reader, JDesktopPane table,
                         DefaultListModel listModel, JTextArea textArea) {
        this.isConnected = false;
        this.socket = socket;
        this.writer = writer;
        this.reader = reader;
        this.table = table;
        this.listModel = listModel;
        this.textArea = textArea;
    }

    public boolean isConnected() {
        return isConnected;
    }

    // 被动的关闭连接
    public synchronized void closeCon() {
        // 清空用户列表
        listModel.removeAllElements();
        isConnected = false; //修改状态为断开
        // 被动的关闭连接释放资源
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String message = "";
        while (true) {
            try {
                message = reader.readLine();
                StringTokenizer stringTokenizer = new StringTokenizer(
                        message, "/@");
                String command = stringTokenizer.nextToken();   //命令
                if (command.equals("CLOSE"))// 服务器已关闭命令
                {
                    textArea.append("服务器已关闭！\r\n");
                    textArea.selectAll();              //滚动条拉到最下
                    closeCon(); //被动的关闭连接
                    return; //结束线程
                } else if (command.equals("ADD")) {     //有用户上线更新在线列表
                    String userName = "";
                    if ((userName = stringTokenizer.nextToken()) != null) {
                        listModel.addElement(userName);
                    }
                } else if (command.equals("DELETE")) {  //有用户下线更新在线列表
                    String userName = stringTokenizer.nextToken();
                    listModel.removeElement(userName);
                } else if (command.equals("USERLIST")) {    //加载在线用户列表
                    int size = Integer.parseInt(stringTokenizer.nextToken());
                    String userName = null;
                    for (int i = 0; i < size; i++) {
                        userName = stringTokenizer.nextToken();
                        listModel.addElement(userName);
                    }
                } else if (command.equals("MAX")) { //人数已达上限
                    textArea.append(stringTokenizer.nextToken() + "\r\n");
                    textArea.selectAll();              //滚动条拉到最下
                    closeCon(); //被动的关闭连接

                    JOptionPane.showConfirmDialog(table, "服务器缓冲区已满！", "错误", 0);
                    return; //结束线程
                } else {    //普通消息
                    textArea.append(message + "\r\n");
                    textArea.selectAll();              //滚动条拉到最下
                    isConnected = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                textArea.append("服务器异常！\r\n");
                textArea.selectAll();              //滚动条拉到最下
                closeCon(); //被动的关闭连接
                return; //结束线程
            }
        }
    }
}
