package top.aries.kind.model;

import java.util.List;
import java.util.Vector;

/**
 * 坦克类
 * 每个坦克就是一个线程，
 * 这里自己坦克并没有启动线程
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-31 16:28
 * Created by IntelliJ IDEA.
 */
public class Tank implements Runnable {

    private int x = 0;
    private int y = 0;  //坐标
    private int drect = 0;// 方向 0向上，1向右，2向下，3向左
    private int type = 0;// 坦克类型 0表示自己
    private int speed = 3;// 速度
    public List<Bullet> mybs = new Vector<>();// 子弹集
    private Bullet myBullet;// 子弹
    public boolean islive = true;
    private TankMap tankMap;
    public boolean start = true;
    private String userName;

    public Tank(int x, int y, int drect, int type, String userName) {
        super();
        this.x = x;
        this.y = y;
        this.drect = drect;
        this.type = type;
        this.userName = userName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDrect() {
        return drect;
    }

    public void setDrect(int drect) {
        this.drect = drect;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTankMap(TankMap tankMap) {
        this.tankMap = tankMap;
    }

    public String getUserName() {
        return userName;
    }

    public List<Bullet> getMybs() {
        return mybs;
    }

    public void setMybs(Vector<Bullet> mybs) {
        this.mybs = mybs;
    }

    //上移
    public void moveUp() {
        if (y - speed < 0)
            y = 0;
        else {
            y -= speed;
            tankMap.location[x][y] = 1;//标记此坦克坐标在地图上防止其他坦克过来占用导致重叠
//             这里只标记了坦克坐标那一个点，会有bug，部分坦克还是有重叠现象，
//             这里可以遍历整个坦克坐标（x到x+20，y到y+20）设置标记。
//             for(int i=x;i<x+20;i++){
//                 for (int j = y; j < y+20; j++) {
//                     map.location[x][y]=1;
//                }
//             }
        }
    }

    //下移
    public void moveDown() {
        if (y + speed > 380)
            y = 380;
        else {
            y += speed;
            tankMap.location[x][y] = 1;
        }
    }

    //右移
    public void moveRight() {
        if (x + speed > 325)
            x = 325;
        else {
            x += speed;
            tankMap.location[x][y] = 1;
        }
    }

    //左移
    public void moveLeft() {
        if (x - speed < 0)
            x = 0;
        else {
            x -= speed;
            tankMap.location[x][y] = 1;
        }
    }

    public void shot() {
        switch (drect) {
            case 0:
                myBullet = new Bullet(x + 9, y, 5, 0);
                mybs.add(myBullet);
                break;
            case 1:
                myBullet = new Bullet(x + 20, y + 9, 5, 1);
                mybs.add(myBullet);
                break;
            case 2:
                myBullet = new Bullet(x + 9, y + 20, 5, 2);
                mybs.add(myBullet);
                break;
            case 3:
                myBullet = new Bullet(x, y + 9, 5, 3);
                mybs.add(myBullet);
                break;
        }
    }

    @Override
    public void run() {
        while (islive) {
            if (start) {
                int step;
                int s;
                try {
                    switch (drect) {
                        case 0:
                            step = (int) (Math.random() * 30);
                            for (int i = 0; i < step; i++) {
                                moveUp();
                                if (y <= 0)
                                    break;// 撞墙跳出循环
                                if (y >= 20)// 防数组越界
                                    if (tankMap.location[x][y - 20] == 1) {
                                        tankMap.location[x][y - 20] = 0;//这里没分开判断
                                        break;
                                    }
                                Thread.sleep(80);
                            }
                            break;
                        case 1:
                            step = (int) (Math.random() * 30);
                            for (int i = 0; i < step; i++) {
                                moveRight();
                                if (x >= 345)
                                    break;
                                if (x < 325)
                                    if (tankMap.location[x + 20][y] == 1) {
                                        tankMap.location[x + 20][y] = 0;
                                        break;
                                    }
                                Thread.sleep(80);
                            }
                            break;
                        case 2:
                            step = (int) (Math.random() * 30);
                            for (int i = 0; i < step; i++) {
                                moveDown();
                                if (y >= 400)
                                    break;
                                if (y < 380)
                                    if (tankMap.location[x][y + 20] == 1) {
                                        tankMap.location[x][y + 20] = 0;
                                        break;
                                    }
                                Thread.sleep(80);
                            }
                            break;
                        case 3:
                            step = (int) (Math.random() * 30);
                            for (int i = 0; i < step; i++) {
                                moveLeft();
                                if (x <= 0)
                                    break;
                                if (x >= 20)
                                    if (tankMap.location[x - 20][y] == 1) {
                                        tankMap.location[x - 20][y] = 0;
                                        break;
                                    }
                                Thread.sleep(80);
                            }
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                drect = (int) (Math.random() * 4);// 随机方向
                s = (int) (Math.random() * 10);
                if (s > 8) {
                    shot();     //随机发射子弹
                }
            }
        }
    }

}
