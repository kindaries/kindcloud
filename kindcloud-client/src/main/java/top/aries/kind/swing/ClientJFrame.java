package top.aries.kind.swing;

import top.aries.kind.listener.TankListener;
import top.aries.kind.panel.ClientJPanel;
import top.aries.kind.panel.TankJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 客户端主页面
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-30 10:37
 * Created by IntelliJ IDEA.
 */
public class ClientJFrame extends JFrame {

    private JDesktopPane table;         //分层面板

    private ClientJPanel clientJPanel;
    private TankJPanel tankJPanel;

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

        JMenuBar jMenuBar = new JMenuBar();              //菜单栏
        JMenu jMenu1 = new JMenu("个人中心");          //菜单
        JMenu jMenu2 = new JMenu("坦克大战");          //菜单
        JMenu jMenu3 = new JMenu("帮助");              //菜单

        table = new JDesktopPane();                     //分层面板
        clientJPanel = new ClientJPanel(userName);
        table.add(clientJPanel);
        add(table);                                     //添加面板

        JMenuItem client = new JMenuItem("连接");
        JMenuItem discon = new JMenuItem("断开");
        ImageIcon startIco = new ImageIcon(ClientJFrame.class.getResource("/img/start.png"));
        startIco.setImage(startIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        client.setIcon(startIco);
        //启动服务器事件
        client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (clientJPanel.connectServer()) {
                    client.setEnabled(false);
                    discon.setEnabled(true);
                }
            }
        });
        ImageIcon stopIco = new ImageIcon(ClientJFrame.class.getResource("/img/stop.png"));
        stopIco.setImage(stopIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        discon.setIcon(stopIco);
        discon.setEnabled(false);
        discon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (clientJPanel.closeConnection()) {
                    client.setEnabled(true);
                    discon.setEnabled(false);
                }
            }
        });

        JMenuItem quit = new JMenuItem("退出");        //菜单项
        ImageIcon quit1Ico = new ImageIcon(ClientJFrame.class.getResource("/img/quit1.png"));
        quit1Ico.setImage(quit1Ico.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        quit.setIcon(quit1Ico);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitActionPerformed(table);
            }
        });

        JMenuItem about = new JMenuItem("关于");       //菜单项
        ImageIcon aboutIco = new ImageIcon(ClientJFrame.class.getResource("/img/about.png"));
        aboutIco.setImage(aboutIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        about.setIcon(aboutIco);
        about.addActionListener(new ActionListener() {          //添加事件
            public void actionPerformed(ActionEvent evt) {
                aboutActionPerformed(table);
            }
        });

        JMenuItem start = new JMenuItem("开始");
        JMenuItem online = new JMenuItem("联机");
        JMenuItem stop = new JMenuItem("结束");
        start.setIcon(startIco);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                client.setEnabled(false);
                discon.setEnabled(false);
                start.setEnabled(false);
                stop.setEnabled(true);

                table.removeAll();
                tankJPanel = new TankJPanel(20, null, userName);
                Thread tankThread = new Thread(tankJPanel);
                tankThread.start();
                table.add(tankJPanel);
                table.repaint();
                table.revalidate();
                addKeyListener(new TankListener(tankJPanel));
            }
        });
        online.setIcon(startIco);
        online.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!clientJPanel.isClient) {
                    return;
                }
                /*tankJPanel.setStop(false);
                tankJPanel = null;
                table.removeAll();*/

                client.setEnabled(false);
                discon.setEnabled(false);
                start.setEnabled(true);
                stop.setEnabled(true);

                table.removeAll();
                tankJPanel = new TankJPanel(0, clientJPanel.messageThread,userName);
                clientJPanel.messageThread.setTankJPanel(tankJPanel);

                Thread tankThread = new Thread(tankJPanel);
                tankThread.start();
                table.add(tankJPanel);
                table.repaint();
                table.revalidate();
                addKeyListener(new TankListener(tankJPanel));
            }
        });
        stop.setIcon(stopIco);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                if (clientJPanel.isClient) {
                    client.setEnabled(false);
                    discon.setEnabled(true);
                } else {
                    client.setEnabled(false);
                    discon.setEnabled(true);
                }
                start.setEnabled(true);
                stop.setEnabled(false);

                tankJPanel.setStop(false);
                tankJPanel = null;
                table.removeAll();
                clientJPanel = new ClientJPanel(userName);
                table.add(clientJPanel);
                table.repaint();                //更新
                table.revalidate();             //使更新有效
            }
        });

        JMenuItem notes = new JMenuItem("说明");
        notes.setIcon(aboutIco);
        notes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(table, "上:W, 下:S, 左:A, 右:D, 射击：空格");
            }
        });

        jMenu1.add(client);
        jMenu1.add(discon);
        jMenu1.add(quit);
        jMenu2.add(start);
        jMenu2.add(online);
        jMenu2.add(stop);
        jMenu2.add(notes);
        jMenu3.add(about);
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        jMenuBar.add(jMenu3);
        setJMenuBar(jMenuBar);

        // 关闭窗口时事件
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                if (clientJPanel.isClient) {
                    clientJPanel.closeConnection();    //关闭服务器
                }
                System.exit(0);     //退出程序
            }
        });


        if (clientJPanel.connectServer()) {
            client.setEnabled(false);
            discon.setEnabled(true);
        }

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
            if (clientJPanel.isClient) {
                clientJPanel.closeConnection();    //关闭服务器
            }
            this.dispose();
            new LoginJFrame().setVisible(true);
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
