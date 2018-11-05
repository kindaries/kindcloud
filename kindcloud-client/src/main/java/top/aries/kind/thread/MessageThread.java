package top.aries.kind.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import top.aries.kind.model.Bullet;
import top.aries.kind.model.Tank;
import top.aries.kind.panel.ClientJPanel;
import top.aries.kind.panel.TankJPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
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

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private ClientJPanel clientJPanel;
    private TankJPanel tankJPanel;

    // 接收消息线程的构造方法
    public MessageThread(ClientJPanel clientJPanel) throws IOException {
        this.socket = new Socket("127.0.0.1", 8888);// 根据服务器ip端口号建立连接
        this.writer = new PrintWriter(socket.getOutputStream());                             //写入流
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //读取流
        this.clientJPanel = clientJPanel;
        //发送客户端用户基本信息(用户名和ip地址)
        send(clientJPanel.userName + "@" + socket.getLocalAddress().toString());
    }

    public void setTankJPanel(TankJPanel tankJPanel) {
        this.tankJPanel = tankJPanel;
    }

    public void send(String msg) {
        writer.println(msg);
        writer.flush();
    }

    // 被动的关闭连接
    public synchronized void closeCon() {
        // 清空用户列表
        clientJPanel.listModel.removeAllElements();
        clientJPanel.isClient = false; //修改状态为断开
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
        while (true) {               //接收消息
            try {
                message = reader.readLine();
                StringTokenizer stringTokenizer = new StringTokenizer(message, "/@");
                String command = stringTokenizer.nextToken();   //命令
                if ("CLOSE".equals(command)) {         //服务器已关闭命令
                    clientJPanel.contentArea.append("服务器已关闭！\r\n");
                    clientJPanel.contentArea.selectAll();              //滚动条拉到最下
                    closeCon(); //被动的关闭连接
                    return; //结束线程
                } else if ("ADD".equals(command)) {     //有用户上线更新在线列表
                    String userName = "";
                    if ((userName = stringTokenizer.nextToken()) != null) {
                        clientJPanel.listModel.addElement(userName);
                    }
                } else if ("DELETE".equals(command)) {  //有用户下线更新在线列表
                    String userName = stringTokenizer.nextToken();
                    clientJPanel.listModel.removeElement(userName);
                } else if ("USERLIST".equals(command)) {    //加载在线用户列表
                    int size = Integer.parseInt(stringTokenizer.nextToken());
                    String userName = null;
                    for (int i = 0; i < size; i++) {
                        userName = stringTokenizer.nextToken();
                        clientJPanel.listModel.addElement(userName);
                    }
                } else if ("MAX".equals(command)) { //人数已达上限
                    clientJPanel.contentArea.append(stringTokenizer.nextToken() + "\r\n");
                    clientJPanel.contentArea.selectAll();              //滚动条拉到最下
                    closeCon(); //被动的关闭连接
                    //clientJPanel.contentArea.append("服务器缓冲区已满！\r\n");
                    return; //结束线程
                } else if (message.indexOf("TANKONLINE@") > -1) {
                    String jsonStr = message.substring(11);
                    Tank tank = JSON.parseObject(jsonStr, Tank.class);

                    JSONObject aaa = JSONObject.parseObject(jsonStr);
                    List<Bullet> mybs = JSON.parseObject(aaa.get("mybs").toString(), new TypeReference<List<Bullet>>(){});
                    for (Bullet b : mybs) {
                        b.start();
                        b.yield();
                    }
                    tank.mybs=mybs;

                    if (tankJPanel != null)
                         tankJPanel.tankOnline(tank);
                } else {    //普通消息
                    clientJPanel.contentArea.append(message + "\r\n");
                    clientJPanel.contentArea.selectAll();              //滚动条拉到最下
                }
            } catch (Exception e) {
                e.printStackTrace();
                clientJPanel.contentArea.append("服务器异常！\r\n");
                clientJPanel.contentArea.selectAll();              //滚动条拉到最下
                closeCon(); //被动的关闭连接
                return; //结束线程
            }
        }
    }
}
