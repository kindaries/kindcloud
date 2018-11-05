package top.aries.kind.panel;

import top.aries.kind.thread.MessageThread;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-11-02 11:40
 * Created by IntelliJ IDEA.
 */
public class ClientJPanel extends JPanel {

    public MessageThread messageThread;     //负责接收消息的线程
    public boolean isClient = false;        //服务器是否已启动
    public DefaultListModel listModel;      //用户列表
    public JTextArea contentArea;           //消息框

    public String userName;

    /**
     * 初始化页面
     */
    public ClientJPanel(String userName) {

        this.userName = userName;

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
        //写消息的文本框中按回车键时事件
        txt_message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String message = txt_message.getText().trim();
                send(userName, message);
                txt_message.setText(null);
            }
        });

        //单击发送按钮时事件
        btn_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String message = txt_message.getText().trim();
                send(userName, message);
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
     * 连接服务器
     *
     * @return
     */
    public boolean connectServer() {

        if (isClient) {
            JOptionPane.showConfirmDialog(this, "已连接上服务器！", "错误", 0);
            return true;
        }

        // 连接服务器
        try {
            //开启接收消息的线程
            messageThread = new MessageThread(this);
            messageThread.start();
            isClient = true;
            return true;
        } catch (Exception e) {
            contentArea.append("与端口号为：8888  IP地址为：127.0.0.1的服务器连接失败！" + "\r\n");
            contentArea.selectAll();              //滚动条拉到最下
            return false;
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(String userName, String message) {
        if (!isClient) {
            JOptionPane.showMessageDialog(this, "还没有连接服务器，无法发送消息！", "错误", 0);
            return;
        }
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(this, "消息不能为空！", "错误", 0);
            return;
        }
        messageThread.send(userName + "@" + "ALL" + "@" + message);
    }


    /**
     * 客户端主动关闭连接
     */
    @SuppressWarnings("deprecation")
    public synchronized boolean closeConnection() {

        if (!isClient) {
            JOptionPane.showConfirmDialog(this, "还没有连接服务器！", "错误", 0);
            return true;
        }

        try {
            messageThread.send("CLOSE");//发送断开连接命令给服务器
            messageThread.stop();    //停止接受消息线程
            messageThread.closeCon();//释放资源

            contentArea.append("与服务器断开连接！\r\n");
            contentArea.selectAll();              //滚动条拉到最下
            isClient = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            contentArea.append("断开连接异常！\r\n");
            contentArea.selectAll();              //滚动条拉到最下
            return false;
        }
    }

}
