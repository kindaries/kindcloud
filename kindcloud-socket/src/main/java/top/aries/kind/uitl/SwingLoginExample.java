package top.aries.kind.uitl;

import javax.swing.*;
import java.net.URL;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-25 15:30
 * Created by IntelliJ IDEA.
 */
public class SwingLoginExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("魔兽世界");//创建 JFrame 实例
        frame.setLocation(950, 100);            //设置窗体位置
        frame.setSize(350, 500);                //设置窗体大小
        frame.setResizable(false);           //设置窗体是否可以调整大小，参数为布尔值
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //加载图片
        URL imgURL = SwingLoginExample.class.getResource("/img/bg-login1.jpg");
        ImageIcon icon = new ImageIcon(imgURL);
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, 350, 500);
        //获取窗口的最底层，将label放入
        frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        //获取frame的顶层容器,并设置为透明
        JPanel j = (JPanel) frame.getContentPane();
        j.setOpaque(false);


        JPanel panel = new JPanel();    //创建面板,这个类似于 HTML 的 div 标签

        //必须设置为透明的。否则看不到图片
        panel.setOpaque(false);

        frame.add(panel);               //添加面板

        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        frame.setVisible(true);          //显示窗体
    }

    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        JLabel logoLabel = new JLabel("欢迎来到魔兽世界，魔兽世界有你更精彩~");
        logoLabel.setBounds(20, 110, 300, 25);    //指定标签位置setBounds(x, y, width, height)
        panel.add(logoLabel);

        JLabel userLabel = new JLabel("UserName:");            //创建userLabel标签
        userLabel.setBounds(40, 150, 80, 25);    //指定标签位置setBounds(x, y, width, height)
        panel.add(userLabel);

        JTextField userText = new JTextField(20);             //创建文本输入框
        userText.setBounds(130, 150, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("PassWord:");
        passwordLabel.setBounds(40, 180, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);   //创建密码输入框
        passwordText.setBounds(130, 180, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");           //创建登录按钮
        loginButton.setBounds(70, 220, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");       //创建注册按钮
        registerButton.setBounds(180, 220, 80, 25);
        panel.add(registerButton);


    }
}
