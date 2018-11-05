package top.aries.kind.panel;

import top.aries.kind.thread.ClientThread;
import top.aries.kind.thread.ServerThread;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.BindException;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-11-02 9:20
 * Created by IntelliJ IDEA.
 */
public class ServerJPanel extends JPanel {

    public ServerThread serverThread;       //服务器
    public boolean isStart = false;         //服务器是否已启动
    public DefaultListModel listModel;      //用户列表
    public JTextArea contentArea;           //消息框

    /**
     * 初始化页面
     */
    public ServerJPanel() {

        setBounds(0, 0, 345, 450);

        JSplitPane centerSplit;         //主体面板
        JScrollPane leftPanel;          //左边消息面板
        JScrollPane rightPanel;         //右边用户面包
        JPanel southPanel;              //下面输入框面板

        contentArea = new JTextArea();              //初始化消息框
        contentArea.setEditable(false);
        contentArea.setForeground(Color.blue);
        contentArea.setLineWrap(true);        //激活自动换行功能

        leftPanel = new JScrollPane(contentArea);
        leftPanel.setBorder(new TitledBorder("消息显示区"));


        listModel = new DefaultListModel();
        JList userList = new JList(listModel);
        rightPanel = new JScrollPane(userList);
        rightPanel.setBorder(new TitledBorder("在线用户"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        centerSplit.setDividerLocation(255);        //按照百分比设置分割条的位置
        centerSplit.setEnabled(false);              //禁止拖动

        JButton btn_send = new JButton("发送");
        JTextField txt_message = new JTextField();
        //单击发送按钮时事件
        btn_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String message = txt_message.getText().trim();
                send(message);
                txt_message.setText(null);
            }
        });

        //文本框按回车键时事件
        txt_message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String message = txt_message.getText().trim();
                send(message);
                txt_message.setText(null);
            }
        });
        southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new TitledBorder("写消息"));
        southPanel.add(txt_message, "Center");
        southPanel.add(btn_send, "East");

        setLayout(new BorderLayout());
        add(centerSplit, "Center");
        add(southPanel, "South");


    }

    /**
     * 启动服务器
     */
    public boolean serverStart() {

        if (isStart) {
            JOptionPane.showConfirmDialog(this, "服务器已处于启动状态！", "错误", 0);
            return true;
        }
        try {
            serverThread = new ServerThread(this);
            serverThread.start();
            isStart = true;
            contentArea.append("服务器已启动！\r\n人数上限：30,端口：8888\r\n");
            return true;
        } catch (BindException e) {
            e.printStackTrace();
            isStart = false;
            JOptionPane.showConfirmDialog(this, "端口号已被占用，请换一个！", "错误", 0);
            contentArea.append("8888端口号已被占用\r\n");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            isStart = false;
            JOptionPane.showConfirmDialog(this, "启动服务器异常！", "错误", 0);
            contentArea.append("启动服务器异常！\r\n");
            return false;
        }

    }

    /**
     * 关闭服务器
     */
    @SuppressWarnings("deprecation")
    public boolean closeServer() {

        if (!isStart) {
            JOptionPane.showConfirmDialog(this, "服务器已处于关闭状态！", "错误", 0);
            return true;
        }

        try {

            for (int i = serverThread.clients.size() - 1; i >= 0; i--) {
                ClientThread clientThread = serverThread.clients.get(i);
                //给所有在线用户发送关闭命令
                clientThread.getWriter().println("CLOSE");
                clientThread.getWriter().flush();
                //释放资源
                clientThread.stop();                  //停止此条为客户端服务的线程
                clientThread.getReader().close();
                clientThread.getWriter().close();
                clientThread.getSocket().close();
                serverThread.clients.remove(i);
            }

            if (serverThread.serverSocket != null) {
                serverThread.serverSocket.close();       //关闭服务器端连接
            }
            if (serverThread != null)
                serverThread.stop();

            listModel.removeAllElements();  //清空用户列表
            contentArea.append("服务器已关闭！\r\n");
            isStart = false;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            isStart = true;
            return false;
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(String message) {
        if (!isStart) {
            JOptionPane.showConfirmDialog(this, "服务器还未启动,不能发送消息！", "错误", 0);
            return;
        }
        /*if (clients.size() == 0) {
            JOptionPane.showConfirmDialog(table, "没有用户在线,不能发送消息！", "错误", 0);
            return;
        }*/
        if (message == null || message.equals("")) {
            JOptionPane.showConfirmDialog(this, "消息不能为空！", "错误", 0);
            return;
        }
        sendServerMessage(message);// 群发服务器消息
        contentArea.append("服务器说：\r\n" + message + "\r\n");
        contentArea.selectAll();              //滚动条拉到最下

    }

    /**
     * 群发服务器消息
     *
     * @param message
     */
    public void sendServerMessage(String message) {
        for (int i = serverThread.clients.size() - 1; i >= 0; i--) {
            ClientThread clientThread = serverThread.clients.get(i);
            clientThread.getWriter().println("服务器：\r\n" + message + "(通知)");
            clientThread.getWriter().flush();
        }
    }

}
