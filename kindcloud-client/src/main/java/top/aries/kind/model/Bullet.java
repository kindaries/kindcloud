package top.aries.kind.model;

/**
 * 子弹类
 * 因为多个子弹同时运动所以需要做线程
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-31 16:25
 * Created by IntelliJ IDEA.
 */
public class Bullet extends Thread {
    private int x;
    private int y;
    private int speed;                  //子弹移动速度
    private int drect;                  //子弹方向
    public boolean islive = true;

    public Bullet(int x, int y, int speed, int drect) {
        super();
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.drect = drect;
        this.start();
    }

    public Bullet() {
        super();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void run() {
        while (islive) {

            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (drect) {//判断方向坐标移动
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            if (x < 0 || y < 0 || x > 345 || y > 400 || !islive) {
                islive = false;
                break;
            }
        }
    }

}
