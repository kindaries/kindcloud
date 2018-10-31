package top.aries.kind.swing;

import javafx.embed.swing.JFXPanel;
import top.aries.kind.thread.MessageThread;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

/**
 * 客户端主页面
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-30 10:37
 * Created by IntelliJ IDEA.
 */
public class ClientJFrame extends JFrame {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private MessageThread messageThread;    //负责接收消息的线程

    private JDesktopPane table;         //分层面板
    private DefaultListModel listModel; //用户列表
    private JTextArea contentArea;      //消息框

    private String userName;

    /**
     * 一个Swing对象
     */
    public ClientJFrame(String userName) {
        this.userName = userName;

        setIconImage(Toolkit.getDefaultToolkit().createImage(ClientJFrame.class.getResource("/img/ww.png")));
        setTitle("魔兽世界");//创建 JFrame 实例
        setLocation(950, 100);            //设置窗体位置
        setSize(350, 500);                //设置窗体大小
        setResizable(false);           //设置窗体是否可以调整大小，参数为布尔值
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL imgURL = ClientJFrame.class.getResource("/img/bg-login1.jpg");     //加载图片
        ImageIcon icon = new ImageIcon(imgURL);
        //将图片放入label中
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, 350, 500);
        //获取窗口的最底层，将label放入
        getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        //获取frame的顶层容器,并设置为透明
        JPanel j = (JPanel) getContentPane();
        j.setOpaque(false);

        JMenuBar jMenuBar = new JMenuBar();              //菜单栏
        JMenu jMenu1 = new JMenu("个人中心");          //菜单
        JMenu jMenu2 = new JMenu("帮助");              //菜单
        JMenuItem start = new JMenuItem("连接");       //菜单项
        JMenuItem stop = new JMenuItem("断开");        //菜单项
        JMenuItem quit = new JMenuItem("退出");        //菜单项
        JMenuItem about = new JMenuItem("关于");       //菜单项

        table = new JDesktopPane();                     //分层面板
        initComponents(table);
        //必须设置为透明的。否则看不到背景图片
        table.setOpaque(false);
        j.add(table);               //添加面板

        ImageIcon startIco = new ImageIcon(ClientJFrame.class.getResource("/img/start.png"));
        startIco.setImage(startIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        start.setIcon(startIco);
        //启动服务器事件
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (messageThread != null && messageThread.isConnected()) {
                    JOptionPane.showConfirmDialog(table, "已连接上服务器！", "错误", 0);
                    return;
                }
                connectServer(userName);
            }
        });

        ImageIcon stopIco = new ImageIcon(ClientJFrame.class.getResource("/img/stop.png"));
        stopIco.setImage(stopIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        stop.setIcon(stopIco);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeConnection();
            }
        });

        ImageIcon quit1Ico = new ImageIcon(ClientJFrame.class.getResource("/img/quit1.png"));
        quit1Ico.setImage(quit1Ico.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        quit.setIcon(quit1Ico);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitActionPerformed(table);
            }
        });

        ImageIcon aboutIco = new ImageIcon(ClientJFrame.class.getResource("/img/about.png"));
        aboutIco.setImage(aboutIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        about.setIcon(aboutIco);
        about.addActionListener(new ActionListener() {          //添加事件
            public void actionPerformed(ActionEvent evt) {
                aboutActionPerformed(table);
            }
        });

        jMenu1.add(start);
        jMenu1.add(stop);
        jMenu1.add(quit);
        jMenu2.add(about);
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        setJMenuBar(jMenuBar);

        // 关闭窗口时事件
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                if (messageThread != null && messageThread.isConnected()) {
                    closeConnection();    //关闭服务器
                }
                System.exit(0);     //退出程序
            }
        });

        connectServer(userName);

    }

    /**
     * 初始化页面
     *
     * @param table
     */
    private void initComponents(JDesktopPane table) {


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

        table.setLayout(new BorderLayout());
        table.add(centerSplit, "Center");
        table.add(southPanel, "South");


    }


    /**
     * 打开关于页面
     *
     * @param table
     */
    private void aboutActionPerformed(JDesktopPane table) {
        JInternalFrame[] js = table.getAllFrames();
        for (JInternalFrame j : js) {
            if (j instanceof AboutJFrame) {
                j.isSelected();
                return;
            }
        }

        AboutJFrame aboutJFrame = new AboutJFrame();
        table.add(aboutJFrame);
        aboutJFrame.setVisible(true);

    }

    /**
     * 退出
     *
     * @param table
     */
    private void quitActionPerformed(JDesktopPane table) {
        int result = JOptionPane.showConfirmDialog(table, "真的要离开吗？", "退出", JOptionPane.WARNING_MESSAGE);
        if (result == 0) {
            if (messageThread != null && messageThread.isConnected()) {
                closeConnection();    //关闭服务器
            }
            this.dispose();
            new LoginJFrame().setVisible(true);
        }
    }

    /**
     * 连接服务器
     *
     * @param userName
     * @return
     */
    public boolean connectServer(String userName) {

        // 连接服务器
        try {
            socket = new Socket("127.0.0.1", 8888);// 根据服务器ip端口号建立连接
            writer = new PrintWriter(socket.getOutputStream());                             //写入流
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //读取流
            //发送客户端用户基本信息(用户名和ip地址)
            sendMessage(userName + "@" + socket.getLocalAddress().toString());
            //开启接收消息的线程
            messageThread = new MessageThread(socket, writer, reader, table, listModel, contentArea);
            messageThread.start();
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
    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(String userName, String message) {
        if (!messageThread.isConnected()) {
            JOptionPane.showMessageDialog(table, "还没有连接服务器，无法发送消息！", "错误", 0);
            return;
        }
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(table, "消息不能为空！", "错误", 0);
            return;
        }
        sendMessage(userName + "@" + "ALL" + "@" + message);
    }

    /**
     * 客户端主动关闭连接
     */
    @SuppressWarnings("deprecation")
    public synchronized boolean closeConnection() {

        if (messageThread == null || !messageThread.isConnected()) {
            JOptionPane.showConfirmDialog(table, "还没有连接服务器！", "错误", 0);
            return true;
        }

        try {
            sendMessage("CLOSE"); //发送断开连接命令给服务器
            messageThread.stop(); //停止接受消息线程
            // 释放资源
            messageThread.closeCon();

            contentArea.append(userName + "与服务器断开连接！\r\n");
            contentArea.selectAll();              //滚动条拉到最下
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            contentArea.append(userName + "断开连接异常！\r\n");
            contentArea.selectAll();              //滚动条拉到最下
            return false;
        }
    }

    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientJFrame("user").setVisible(true);
            }
        });

    }
}
