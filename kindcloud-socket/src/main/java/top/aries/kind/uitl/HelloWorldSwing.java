package top.aries.kind.uitl;

import javax.swing.*;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-25 15:25
 * Created by IntelliJ IDEA.
 */
public class HelloWorldSwing {

    /**
     * 创建并显示GUI
     * 出于线程安全的考虑
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);            //设置一个漂亮的外观风格,要写在new JFrame()对象之前
        JFrame frame = new JFrame("HelloWorldSwing");       //创建及设置窗口
        //设置窗体左上角与显示屏左上角的坐标
        frame.setLocation(950, 100);            //距离显示屏左边缘950,像素离显示屏上边缘100像素
        //frame.pack();             //自动调整窗口大小
        frame.setSize(320, 480);            //设置窗体的大小为320*480像素大小
        frame.setResizable(true);           //设置窗体是否可以调整大小，参数为布尔值
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //用户单击窗口的关闭按钮时程序执行的操作

        // 添加 "Hello World" 标签
        JLabel label = new JLabel("Hello World！");
        frame.getContentPane().add(label);

        frame.setVisible(true);         //显示窗体
    }

    public static void main(String[] args) {
        // 显示应用 GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
