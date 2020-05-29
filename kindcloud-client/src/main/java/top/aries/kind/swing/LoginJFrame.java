package top.aries.kind.swing;

import top.aries.kind.uitl.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-29 13:48
 * Created by IntelliJ IDEA.
 */
public class LoginJFrame extends JFrame {

    private JDesktopPane table;         //分层面板
    private JTextField userText;
    private JPasswordField passwordText;

    /**
     * 一个Swing对象
     */
    public LoginJFrame() {

        setIconImage(Toolkit.getDefaultToolkit().createImage(LoginJFrame.class.getResource("/img/ww.png")));
        setTitle("魔兽世界");
        setLocation(950, 100);            //设置窗体位置
        setSize(350, 500);                //设置窗体大小
        setResizable(false);           //设置窗体是否可以调整大小，参数为布尔值
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL imgURL = LoginJFrame.class.getResource("/img/bg-login1.jpg");     //加载图片
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
        JMenuItem login = new JMenuItem();       //菜单项
        JMenuItem register = new JMenuItem();    //菜单项
        JMenuItem quit = new JMenuItem();        //菜单项

        table = new JDesktopPane();             //分层面板
        initComponents(table);
        //必须设置为透明的。否则看不到背景图片
        table.setOpaque(false);
        j.add(table);               //添加面板

        jMenu1.setText("个人中心");
        jMenu2.setText("帮助");
        about.setText("关于");
        ImageIcon aboutIco = new ImageIcon(LoginJFrame.class.getResource("/img/about.png"));
        aboutIco.setImage(aboutIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        about.setIcon(aboutIco);
        about.addActionListener(new ActionListener() {          //添加事件
            public void actionPerformed(ActionEvent evt) {
                aboutActionPerformed(table);
            }
        });

        login.setText("登录");
        login.setPreferredSize(new Dimension(62, 22));
        ImageIcon loginIco = new ImageIcon(LoginJFrame.class.getResource("/img/login.png"));
        loginIco.setImage(loginIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        login.setIcon(loginIco);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goLoginActionPerformed(table);
            }
        });

        register.setText("注册");
        ImageIcon registerIco = new ImageIcon(LoginJFrame.class.getResource("/img/register.png"));
        registerIco.setImage(registerIco.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        register.setIcon(registerIco);
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goRegisterActionPerformed(table);
            }
        });

        quit.setText("退出");
        ImageIcon quit1Ico = new ImageIcon(LoginJFrame.class.getResource("/img/quit1.png"));
        quit1Ico.setImage(quit1Ico.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        quit.setIcon(quit1Ico);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitActionPerformed(table);
            }
        });

        jMenu1.add(login);
        jMenu1.add(register);
        jMenu1.add(quit);
        jMenu2.add(about);
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        setJMenuBar(jMenuBar);

    }

    /**
     * 初始化登录页面
     *
     * @param table
     */
    private void initComponents(JDesktopPane table) {

        JLabel logoLabel = new JLabel("欢迎来到魔兽世界，魔兽世界有你更精彩~");
        logoLabel.setBounds(45, 110, 300, 25);   //指定标签位置setBounds(x, y, width, height)
        table.add(logoLabel);

        JLabel userLabel = new JLabel("昵称:");             //创建userLabel标签
        userLabel.setBounds(65, 150, 80, 25);    //指定标签位置setBounds(x, y, width, height)
        table.add(userLabel);

        userText = new JTextField(20);             //创建文本输入框
        userText.setBounds(100, 150, 165, 25);
        table.add(userText);

        JLabel passwordLabel = new JLabel("口令:");
        passwordLabel.setBounds(65, 180, 80, 25);
        table.add(passwordLabel);

        passwordText = new JPasswordField(20);   //创建密码输入框
        passwordText.setBounds(100, 180, 165, 25);
        table.add(passwordText);

        JButton loginButton = new JButton("登录");           //创建登录按钮
        loginButton.setBounds(70, 220, 80, 25);
        table.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginActionPerformed(table);
            }
        });

        JButton registerButton = new JButton("注册");       //创建注册按钮
        registerButton.setBounds(180, 220, 80, 25);
        table.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goRegisterActionPerformed(table);
            }
        });

    }

    /**
     * 初始化注册页面
     *
     * @param table
     */
    private void registerComponents(JDesktopPane table) {

        JLabel logoLabel = new JLabel("欢迎来到魔兽世界，魔兽世界有你更精彩~");
        logoLabel.setBounds(45, 70, 300, 25);   //指定标签位置setBounds(x, y, width, height)
        table.add(logoLabel);

        JLabel userLabel = new JLabel("口令:");            //创建userLabel标签
        userLabel.setBounds(65, 100, 80, 25);    //指定标签位置setBounds(x, y, width, height)
        table.add(userLabel);
        JTextField userText = new JTextField(20);             //创建文本输入框
        userText.setBounds(100, 100, 165, 25);
        table.add(userText);

        JLabel emailLabel = new JLabel("邮箱:");
        emailLabel.setBounds(65, 130, 80, 25);
        table.add(emailLabel);
        JTextField emailText = new JTextField(20);
        emailText.setBounds(100, 130, 165, 25);
        table.add(emailText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(65, 160, 80, 25);
        table.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 160, 165, 25);
        table.add(passwordText);

        JLabel inviteLabel = new JLabel("校验:");
        inviteLabel.setBounds(65, 190, 80, 25);
        table.add(inviteLabel);
        JTextField inviteText = new JTextField(20);
        inviteText.setBounds(100, 190, 66, 25);
        table.add(inviteText);
        JButton getInvButton = new JButton("获取校验码");
        getInvButton.setBounds(166, 190, 99, 25);
        table.add(getInvButton);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(70, 230, 80, 25);
        table.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                registerActionPerformed(table);
            }
        });

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(180, 230, 80, 25);
        table.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goLoginActionPerformed(table);
            }
        });

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
     * 打开注册页面
     *
     * @param table
     */
    private void goRegisterActionPerformed(JDesktopPane table) {
        table.removeAll();
        registerComponents(table);
        table.repaint();
        table.revalidate();
    }

    /**
     * 打开登录页面
     *
     * @param table
     */
    private void goLoginActionPerformed(JDesktopPane table) {
        table.removeAll();
        initComponents(table);
        table.repaint();
        table.revalidate();
    }

    /**
     * 注册
     *
     * @param table
     */
    private void registerActionPerformed(JDesktopPane table) {
        //todo 注册方法
        if (true) {
            goLoginActionPerformed(table);
        }
    }

    /**
     * 登录
     *
     * @param table
     */
    private void loginActionPerformed(JDesktopPane table) {
        String userName = userText.getText().trim();
        String password = new String(passwordText.getPassword()).trim();
        if (StringUtil.isEmpty(userName)) {
            JOptionPane.showConfirmDialog(table, "账号不能为空！", "请重新输入", JOptionPane.DEFAULT_OPTION);
            return;
        }
        if (StringUtil.isEmpty(password)) {
            JOptionPane.showConfirmDialog(table, "密码不能为空！", "请重新输入", JOptionPane.DEFAULT_OPTION);
            return;
        }
        if (!"123456".equals(password)) {
            JOptionPane.showConfirmDialog(table, "账号或密码错误！", "请重新输入", JOptionPane.DEFAULT_OPTION);
            return;
        }

        dispose();

        //todo 启动服务器或者客户端
        new ClientJFrame(userName).setVisible(true);

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
        }
    }

    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJFrame().setVisible(true);
            }
        });

    }

}
