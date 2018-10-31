package top.aries.kind.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-26 11:31
 * Created by IntelliJ IDEA.
 */
public class AboutJFrame extends JInternalFrame {

    public AboutJFrame() {
        setTitle("关于我们");
        setLocation(0, 20);    //设置窗体位置
        setResizable(false);         //设置窗体是否可以调整大小，参数为布尔值
        setClosable(true);          //关闭按钮
        setIconifiable(true);       //最小化按钮
        initComponents();
    }

    private void initComponents() {

        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();

        jLabel2.setFont(new Font("宋体", 2, 24));
        jLabel2.setForeground(new Color(255, 51, 102));
        jLabel2.setText("魔兽世界，等你而来");

        jLabel3.setFont(new Font("微软雅黑", 2, 18));
        jLabel3.setText("官网：www.kindaries.top");

        jLabel4.setFont(new Font("微软雅黑", 0, 18));
        jLabel4.setText("研发团队正在加班搭建中，敬请期待......");

        GroupLayout layout = new GroupLayout(getContentPane());     //为Container(容器)创建GroupLayout(布局管理器)
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(                                             //指定布局的水平组(确定组件在 X轴 方向上的位置)
                /**
                 *创建并行组(沿指定方向（水平/垂直）并行排列元素)
                 *LEADING -- 左对齐    BASELINE -- 底部对齐  CENTER -- 中心对齐
                 */
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()    //创建串行组(按顺序沿指定方向（水平/垂直）逐个放置元素)
                                /**
                                 * addGap(int min, int pref, int max)
                                 *将指定大小的间隙添加到此 Group
                                 *参数：
                                 *min  - 间隙的最小大小
                                 *pref - 间隙的首选大小
                                 *max  - 间隙的最大大小
                                 */
                                .addGap(20, 20, 20)
                                //使用指定大小将 Component 添加到此 Group
                                .addComponent(jLabel1, 20, 20, 20)
                                .addGap(20)
                                .addComponent(jLabel2)
                                .addContainerGap(60, 60)  //添加一个表示容器边缘和触到容器边框的组件之间首选间隙的元素
                        )
                        .addComponent(jLabel3)
                        .addComponent(jLabel4));

        layout.setVerticalGroup(                                             //指定布局的垂直组(确定组件在 Y轴 方向上的位置)
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)    //创建并行组
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(66, 66)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2).addComponent(jLabel1))
                                .addGap(20)
                                .addComponent(jLabel3)
                                .addGap(20)
                                .addComponent(jLabel4)
                                .addContainerGap(88, 88)
                        ));

        pack();
    }

}
