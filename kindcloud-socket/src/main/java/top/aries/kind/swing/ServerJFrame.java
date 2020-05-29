package top.aries.kind.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-29 16:45
 * Created by IntelliJ IDEA.
 */
public class ServerJFrame extends JFrame {

    private JDesktopPane table;         //分层面板
    private static Integer index = 1;

    /**
     * 一个Swing对象
     */
    public ServerJFrame(String userName) {

        setTitle("魔兽世界");//创建 JFrame 实例
        setLocation(950, 100);            //设置窗体位置
        setSize(350, 500);                //设置窗体大小
        setResizable(false);           //设置窗体是否可以调整大小，参数为布尔值
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL imgURL = MainJFrame.class.getResource("/img/bg-login1.jpg");     //加载图片
        ImageIcon icon = new ImageIcon(imgURL);
        //将图片放入label中
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, 350, 500);
        //获取窗口的最底层，将label放入
        getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        //获取frame的顶层容器,并设置为透明
        JPanel j = (JPanel) getContentPane();
        j.setOpaque(false);

        JMenuBar jMenuBar = new JMenuBar();      //菜单栏
        JMenu jMenu1 = new JMenu();              //菜单
        JMenu jMenu2 = new JMenu();              //菜单
        JMenuItem about = new JMenuItem();       //菜单项
        JMenuItem quit = new JMenuItem();        //菜单项
        table = new JDesktopPane();             //分层面板

        initComponents(table);

        jMenu1.setText("个人中心");
        jMenu2.setText("帮助");
        about.setText("关于");
        ImageIcon aboutIco = new ImageIcon(MainJFrame.class.getResource("/img/about.png"));
        aboutIco.setImage(aboutIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        about.setIcon(aboutIco);
        about.addActionListener(new ActionListener() {          //添加事件
            public void actionPerformed(ActionEvent evt) {
                aboutActionPerformed(table);
            }
        });

        quit.setText("退出");
        ImageIcon quit1Ico = new ImageIcon(MainJFrame.class.getResource("/img/quit1.png"));
        quit1Ico.setImage(quit1Ico.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        quit.setIcon(quit1Ico);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitActionPerformed(table);
            }
        });

        jMenu1.add(quit);
        jMenu2.add(about);
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        setJMenuBar(jMenuBar);

        //必须设置为透明的。否则看不到背景图片
        table.setOpaque(false);

        add(table);               //添加面板

    }

    public JDesktopPane getTable() {
        return table;
    }

    /**
     * 初始化页面
     *
     * @param table
     */
    private void initComponents(JDesktopPane table) {

        JLabel logoLabel = new JLabel("欢迎来到魔兽世界，魔兽世界有你更精彩~");
        logoLabel.setBounds(0, 0, 300, 25);   //指定标签位置setBounds(x, y, width, height)
        table.add(logoLabel);

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
            this.dispose();
            new LoginJFrame().setVisible(true);
        }
    }

    /**
     * 显示消息到页面
     *
     * @param msg
     */
    public void setMessage(JDesktopPane table, String msg) {
        JLabel logoLabel = new JLabel(msg);
        logoLabel.setBounds(0, index * 20, 300, 25);   //指定标签位置setBounds(x, y, width, height)
        table.add(logoLabel);
        table.repaint();
        table.revalidate();
        index++;
    }

    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServerJFrame serverJFrame = new ServerJFrame("admin");
                serverJFrame.setVisible(true);
                JDesktopPane table = serverJFrame.getTable();
                serverJFrame.setMessage(table, "欢迎管理员：admin");
                serverJFrame.setMessage(table, "服务器已启动......");
            }
        });

    }
}
