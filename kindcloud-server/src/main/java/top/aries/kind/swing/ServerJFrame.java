package top.aries.kind.swing;

import top.aries.kind.thread.ClientThread;
import top.aries.kind.thread.ServerThread;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;

/**
 * 服务器主页面
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-29 16:45
 * Created by IntelliJ IDEA.
 */
public class ServerJFrame extends JFrame {

    private boolean isStart = false;    //服务器是否已启动
    private ServerSocket serverSocket;
    private ServerThread serverThread;          //服务器
    private ArrayList<ClientThread> clients;    //客户端列表

    private JDesktopPane table;         //分层面板
    private DefaultListModel listModel; //用户列表
    private JTextArea contentArea;      //消息框

    private String userName;

    /**
     * 一个Swing对象
     */
    public ServerJFrame(String userName) {

        this.userName = userName;

        setIconImage(Toolkit.getDefaultToolkit().createImage(ServerJFrame.class.getResource("/img/ww.png")));
        setTitle("服务器");//创建 JFrame 实例
        setLocation(950, 100);            //设置窗体位置
        setSize(350, 500);                //设置窗体大小
        setResizable(false);           //设置窗体是否可以调整大小，参数为布尔值
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL imgURL = ServerJFrame.class.getResource("/img/bg-login1.jpg");     //加载图片
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
        JMenuItem start = new JMenuItem("启动");       //菜单项
        JMenuItem stop = new JMenuItem("停止");        //菜单项
        JMenuItem quit = new JMenuItem("退出");        //菜单项
        JMenuItem about = new JMenuItem("关于");       //菜单项

        table = new JDesktopPane();                     //分层面板
        initComponents(table);
        //必须设置为透明的。否则看不到背景图片
        table.setOpaque(false);
        j.add(table);               //添加面板

        ImageIcon startIco = new ImageIcon(ServerJFrame.class.getResource("/img/start.png"));
        startIco.setImage(startIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        start.setIcon(startIco);
        //启动服务器事件
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverStart();
            }
        });

        ImageIcon stopIco = new ImageIcon(ServerJFrame.class.getResource("/img/stop.png"));
        stopIco.setImage(stopIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        stop.setIcon(stopIco);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeServer();
            }
        });

        ImageIcon quit1Ico = new ImageIcon(ServerJFrame.class.getResource("/img/quit1.png"));
        quit1Ico.setImage(quit1Ico.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        quit.setIcon(quit1Ico);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitActionPerformed(table);
            }
        });

        ImageIcon aboutIco = new ImageIcon(ServerJFrame.class.getResource("/img/about.png"));
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
                if (isStart) {
                    closeServer();    //关闭服务器
                }
                System.exit(0);     //退出程序
            }
        });

        serverStart();                //默认启动服务器

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
            if (isStart) {
                closeServer();      //关闭服务器
            }
            this.dispose();
            new LoginJFrame().setVisible(true);
        }
    }


    /**
     * 启动服务器
     */
    public void serverStart() {

        if (isStart) {
            JOptionPane.showConfirmDialog(table, "服务器已处于启动状态！", "错误", 0);
            return;
        }
        try {
            serverSocket = new ServerSocket(8888);
            serverThread = new ServerThread(serverSocket, listModel, contentArea);
            serverThread.start();
            clients = serverThread.getClients();
            isStart = true;
            contentArea.append("服务器已启动！\r\n人数上限：30,端口：8888\r\n");
        } catch (BindException e) {
            e.printStackTrace();
            isStart = false;
            JOptionPane.showConfirmDialog(table, "端口号已被占用，请换一个！", "错误", 0);
            contentArea.append("8888端口号已被占用\r\n");
        } catch (Exception e) {
            e.printStackTrace();
            isStart = false;
            JOptionPane.showConfirmDialog(table, "启动服务器异常！", "错误", 0);
            contentArea.append("启动服务器异常！\r\n");
        }

    }

    /**
     * 关闭服务器
     */
    @SuppressWarnings("deprecation")
    private void closeServer() {

        if (!isStart) {
            JOptionPane.showConfirmDialog(table, "服务器已处于关闭状态！", "错误", 0);
            return;
        }

        try {
            if (serverThread != null)
                serverThread.stop();// 停止服务器线程

            for (int i = clients.size() - 1; i >= 0; i--) {
                // 给所有在线用户发送关闭命令
                clients.get(i).getWriter().println("CLOSE");
                clients.get(i).getWriter().flush();
                // 释放资源
                clients.get(i).stop();      //停止此条为客户端服务的线程
                clients.get(i).getReader().close();
                clients.get(i).getWriter().close();
                clients.get(i).getSocket().close();
                clients.remove(i);
            }
            if (serverSocket != null) {
                serverSocket.close();       //关闭服务器端连接
            }
            listModel.removeAllElements();  //清空用户列表
            contentArea.append("服务器已关闭！\r\n");
            isStart = false;
        } catch (IOException e) {
            e.printStackTrace();
            isStart = true;
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(String message) {
        if (!isStart) {
            JOptionPane.showConfirmDialog(table, "服务器还未启动,不能发送消息！", "错误", 0);
            return;
        }
        /*if (clients.size() == 0) {
            JOptionPane.showConfirmDialog(table, "没有用户在线,不能发送消息！", "错误", 0);
            return;
        }*/
        if (message == null || message.equals("")) {
            JOptionPane.showConfirmDialog(table, "消息不能为空！", "错误", 0);
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
        for (int i = clients.size() - 1; i >= 0; i--) {
            clients.get(i).getWriter().println("服务器：\r\n" + message + "(通知)");
            clients.get(i).getWriter().flush();
        }
    }

    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerJFrame("admin").setVisible(true);
            }
        });

    }
}
