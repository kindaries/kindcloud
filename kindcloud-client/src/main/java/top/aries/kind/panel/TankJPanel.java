package top.aries.kind.panel;

import top.aries.kind.model.Bomb;
import top.aries.kind.model.Bullet;
import top.aries.kind.model.Tank;
import top.aries.kind.model.TankMap;
import top.aries.kind.thread.MessageThread;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * 坦克大战显示面板
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-31 17:14
 * Created by IntelliJ IDEA.
 */
public class TankJPanel extends JPanel implements Runnable {

    public MessageThread msg;       //负责接收消息的线程

    public Tank mytank = null;      //我的坦克
    private Image img;
    private Vector<Tank> eks = new Vector<>(); //敌方坦克
    private Vector<Bomb> bs = new Vector<>();  //爆炸集合
    private TankMap tankMap = new TankMap();   //游戏地图

    private Integer foers = 0;               //敌方坦克数量

    private boolean isStop = true;

    /**
     * 构造
     */
    public TankJPanel(Integer foers, MessageThread msg, String userName) {
        this.foers = foers;
        this.msg = msg;

        setBounds(0, 0, 345, 400);

        mytank = new Tank(100, 200, 0, 0, userName);
        mytank.setTankMap(tankMap);
        // 创建敌人坦克
        for (int i = 0; i < (foers > 11 ? 11 : foers); i++) {
            Tank ek = new Tank(i * 30 + 12, 10, 2, 1, "NPC");
            ek.setTankMap(tankMap);
            eks.add(ek);
            new Thread(ek).start();                         //机器人会自己动
        }
        this.foers -= 11;
    }

    public void addTank(Integer type, String userName) {
        Integer x = (int) (Math.random() * 325);// 随机位置
        Integer y = (int) (Math.random() * 380);
        Integer drect = (int) (Math.random() * 4);// 随机方向
        Tank ek = new Tank(x, y, drect, type, userName);
        ek.setTankMap(tankMap);
        if (type == 0) {
            mytank = ek;
        } else if (type == 1) {
            eks.add(ek);
            new Thread(ek).start();                          //机器人会自己动
        } else {
            eks.add(ek);
        }
    }

    public void tankOnline(Tank tank) {
        tank.setTankMap(tankMap);
        if (mytank.getUserName().equals(tank.getUserName())) {
            mytank = tank;
            return;
        } else
            for (int i = 0; i < eks.size(); i++) {
                if (eks.get(i).getUserName().equals(tank.getUserName())) {
                    eks.setElementAt(tank, i);
                    return;
                }
            }
        tank.setType(3);
        eks.add(tank);

    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/1.png"));
        // 画背景
        g.fillRect(0, 0, 345, 400);
        // 画自己的坦克
        if (mytank.islive)
            drawTank(mytank, g);
        // 画自己的子弹
        for (int i = 0; i < mytank.mybs.size(); i++) {  //循环时删除集合时，不要用foreach，用for
            Bullet b = mytank.mybs.get(i);
            if (b.islive) {
                g.setColor(Color.white);
                g.fill3DRect(b.getX(), b.getY(), 2, 2, false);
            } else
                mytank.mybs.remove(b);
        }
        // 画敌人坦克
        for (int i = 0; i < eks.size(); i++) {
            Tank ek = eks.get(i);
            if (ek.islive)
                drawTank(ek, g);
            // 画敌人子弹
            for (int j = 0; j < ek.mybs.size(); j++) {
                Bullet eb = ek.mybs.get(j);
                if (eb.islive) {
                    g.setColor(Color.green);
                    g.fill3DRect(eb.getX(), eb.getY(), 2, 2, false);
                } else
                    ek.mybs.remove(eb);
            }
        }
        // 画爆炸,这里有个bug第一次爆炸没有爆炸效果图出来，检查原因是只一闪而过
        // 添加休眠好了点，不过影响后面爆炸效果，不明白为什么第一次画得快些
        for (int i = 0; i < bs.size(); i++) {
            // System.out.println(bs.size());
            Bomb bb = bs.get(i);
            if (bb.islive) {
                if (bb.getTime() > 5) {
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    g.drawImage(img, bb.getX(), bb.getY(), 20, 20, this);
                } else if (bb.getTime() > 3) {
                    g.drawImage(img, bb.getX(), bb.getY(), 15, 15, this);
                } else if (bb.getTime() > 0) {
                    g.drawImage(img, bb.getX(), bb.getY(), 1, 1, this);
                }
            }
            bb.livedown();
            if (bb.getTime() == 0)
                bs.remove(bb);
        }
    }

    /**
     * 判断子弹是否打中
     *
     * @param b
     * @param ek
     * @return
     */
    public boolean isHitEnemy(Bullet b, Tank ek) {
        // 坦克宽20，高20    子弹长宽为2
        if (b.getX() >= ek.getX() - 2 && b.getX() <= ek.getX() + 20
                && b.getY() >= ek.getY() - 2 && b.getY() <= ek.getY() + 20) {
            b.islive = false;
            ek.islive = false;
            Bomb bb = new Bomb(ek.getX(), ek.getY());
            bs.add(bb);
            return true;
        }
        return false;
    }

    public void drawTank(Tank tank, Graphics g) {
        g.setFont(new Font("楷体", Font.PLAIN, 12));
        int x = tank.getX();
        int y = tank.getY();
        int drect = tank.getDrect();
        int type = tank.getType();
        String userName = tank.getUserName() == null ? "" : tank.getUserName();
        switch (type) {
            case 0:
                g.setColor(Color.cyan);     //自己是天蓝色
                break;
            case 1:
                g.setColor(Color.GREEN);    //敌方是草原绿
                break;
            case 2:
                g.setColor(Color.RED);
                break;
            default:
                break;
        }
        switch (drect) {
            case 0:
                // 坦克宽20，高20
                g.fill3DRect(x, y + 2, 5, 18, false);
                g.fill3DRect(x + 15, y + 2, 5, 18, false);
                g.fill3DRect(x + 5, y + 6, 10, 10, false);
                g.fill3DRect(x + 9, y, 2, 11, false);
                g.drawString(userName, x, y);
                break;
            case 1:
                g.fill3DRect(x, y, 18, 5, false);
                g.fill3DRect(x, y + 15, 18, 5, false);
                g.fill3DRect(x + 4, y + 5, 10, 10, false);
                g.fill3DRect(x + 9, y + 9, 11, 2, false);
                g.drawString(userName, x, y);
                break;
            case 2:
                g.fill3DRect(x, y, 5, 18, false);
                g.fill3DRect(x + 15, y, 5, 18, false);
                g.fill3DRect(x + 5, y + 4, 10, 10, false);
                g.fill3DRect(x + 9, y + 9, 2, 11, false);
                g.drawString(userName, x, y);
                break;
            case 3:
                g.fill3DRect(x + 2, y, 18, 5, false);
                g.fill3DRect(x + 2, y + 15, 18, 5, false);
                g.fill3DRect(x + 6, y + 5, 10, 10, false);
                g.fill3DRect(x, y + 9, 11, 2, false);
                g.drawString(userName, x, y);
                break;
        }
    }

    @Override
    public void run() {
        while (isStop) {
            try {
                Thread.sleep(50);   //画板刷新频率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*if (msg != null) {
                String jsonString = JSON.toJSONString(mytank);
                msg.send("TANKONLINE@" + jsonString);
            }*/

            // 判断自己坦克的子弹是否击中敌人坦克
            for (int i = 0; i < mytank.mybs.size(); i++) {
                Bullet mb = mytank.mybs.get(i);
                if (mb.islive) {
                    for (int j = 0; j < eks.size(); j++) {
                        Tank ek = eks.get(j);
                        if (ek.islive) {
                            if (isHitEnemy(mb, ek)) {
                                if (foers > 0) {
                                    addTank(1, "NPC");
                                    foers--;
                                }
                            }
                        }
                    }
                }
            }
            // 判断敌方坦克的子弹是否击中我方坦克
            for (int i = 0; i < eks.size(); i++) {
                Tank et = eks.get(i);
                for (int j = 0; j < et.mybs.size(); j++) {
                    Bullet etb = et.mybs.get(j);
                    if (etb.islive) {
                        isHitEnemy(etb, mytank);
                    }
                }
            }
            this.repaint(); //刷新
            if (!mytank.islive) {

                try {
                    Thread.sleep(2000);   //画板刷新频率
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //JOptionPane.showMessageDialog(this, "你被GG");
                mytank.islive = true;
            }
        }
    }

}
