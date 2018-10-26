package top.aries.kind.swing;

import com.java1234.view.AboutJava1234InterFrm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-25 17:52
 * Created by IntelliJ IDEA.
 */
public class MainJFrame extends JFrame {

    private JDesktopPane table;         //分层面板

    /**
     * 一个Swing对象
     */
    public MainJFrame() {

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
        JMenu jMenu = new JMenu();              //菜单
        JMenuItem about = new JMenuItem();              //菜单
        JMenuItem login = new JMenuItem();       //菜单项
        JMenuItem register = new JMenuItem();    //菜单项
        JMenuItem quit = new JMenuItem();        //菜单项
        table = new JDesktopPane();             //分层面板

        initComponents(table);

        jMenu.setText("个人中心");
        about.setText("关于我们");
        about.addActionListener(new ActionListener() {          //添加事件
            public void actionPerformed(ActionEvent evt) {
                aboutActionPerformed(table, evt);
            }
        });

        login.setText("登录");
        login.setPreferredSize(new Dimension(62, 22));
        ImageIcon loginIco = new ImageIcon(MainJFrame.class.getResource("/img/login.png"));
        loginIco.setImage(loginIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        login.setIcon(loginIco);

        register.setText("注册");
        ImageIcon registerIco = new ImageIcon(MainJFrame.class.getResource("/img/register.png"));
        registerIco.setImage(registerIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        register.setIcon(registerIco);

        quit.setText("退出");
        ImageIcon quit1Ico = new ImageIcon(MainJFrame.class.getResource("/img/quit1.png"));
        quit1Ico.setImage(quit1Ico.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        quit.setIcon(quit1Ico);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitActionPerformed(table, evt);
            }
        });

        jMenu.add(login);
        jMenu.add(register);
        jMenu.add(quit);
        jMenuBar.add(jMenu);
        jMenuBar.add(about);
        setJMenuBar(jMenuBar);

        //必须设置为透明的。否则看不到背景图片
        table.setOpaque(false);

        add(table);               //添加面板

    }

    private void initComponents(JDesktopPane table) {

        JLabel logoLabel = new JLabel("欢迎来到魔兽世界，魔兽世界有你更精彩~");
        logoLabel.setBounds(20, 110, 300, 25);    //指定标签位置setBounds(x, y, width, height)
        table.add(logoLabel);

        JLabel userLabel = new JLabel("UserName:");            //创建userLabel标签
        userLabel.setBounds(40, 150, 80, 25);    //指定标签位置setBounds(x, y, width, height)
        table.add(userLabel);

        JTextField userText = new JTextField(20);             //创建文本输入框
        userText.setBounds(130, 150, 165, 25);
        table.add(userText);

        JLabel passwordLabel = new JLabel("PassWord:");
        passwordLabel.setBounds(40, 180, 80, 25);
        table.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);   //创建密码输入框
        passwordText.setBounds(130, 180, 165, 25);
        table.add(passwordText);

        JButton loginButton = new JButton("login");           //创建登录按钮
        loginButton.setBounds(70, 220, 80, 25);
        table.add(loginButton);

        JButton registerButton = new JButton("register");       //创建注册按钮
        registerButton.setBounds(180, 220, 80, 25);
        table.add(registerButton);

    }

    private void aboutActionPerformed(JDesktopPane table, ActionEvent evt) {
        AboutJFrame aboutJFrame = AboutJFrame.getAboutJFrame();
        try {
            table.add(aboutJFrame);

        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
        } finally {
            aboutJFrame.setVisible(true);
        }
    }

    private void quitActionPerformed(JDesktopPane table, ActionEvent evt) {
        AboutJava1234InterFrm aboutJava1234InterFrm = new AboutJava1234InterFrm();
        aboutJava1234InterFrm.setVisible(true);
        table.add(aboutJava1234InterFrm);
    }

    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });

    }
}
