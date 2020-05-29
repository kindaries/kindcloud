package top.aries.kind.swing;

import top.aries.kind.panel.ServerJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 服务器主页面
 * <p>
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-29 16:45
 * Created by IntelliJ IDEA.
 */
public class ServerJFrame extends JFrame {

    private ServerJPanel serverJPanel;

    private JDesktopPane table;         //分层面板

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

        JMenuBar jMenuBar = new JMenuBar();              //菜单栏
        JMenu jMenu1 = new JMenu("个人中心");          //菜单
        JMenu jMenu2 = new JMenu("帮助");              //菜单
        JMenuItem start = new JMenuItem("启动");       //菜单项
        JMenuItem stop = new JMenuItem("停止");        //菜单项
        JMenuItem quit = new JMenuItem("退出");        //菜单项
        JMenuItem about = new JMenuItem("关于");       //菜单项

        table = new JDesktopPane();                     //分层面板
        serverJPanel = new ServerJPanel();
        table.add(serverJPanel);
        add(table);               //添加面板

        ImageIcon startIco = new ImageIcon(ServerJFrame.class.getResource("/img/start.png"));
        startIco.setImage(startIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        start.setIcon(startIco);
        //启动服务器事件
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (serverJPanel.serverStart()) {
                    start.setEnabled(false);
                    stop.setEnabled(true);
                }
            }
        });

        ImageIcon stopIco = new ImageIcon(ServerJFrame.class.getResource("/img/stop.png"));
        stopIco.setImage(stopIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        stop.setIcon(stopIco);
        stop.setEnabled(false);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (serverJPanel.closeServer()) {
                    start.setEnabled(true);
                    stop.setEnabled(false);
                }
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
                if (serverJPanel.isStart) {
                    serverJPanel.closeServer();    //关闭服务器
                }
                System.exit(0);     //退出程序
            }
        });

        if (serverJPanel.serverStart()) {                //默认启动服务器
            start.setEnabled(false);
            stop.setEnabled(true);
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
            if (serverJPanel.isStart) {
                serverJPanel.closeServer();      //关闭服务器
            }
            this.dispose();
            new LoginJFrame().setVisible(true);
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
