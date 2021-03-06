package top.aries.kind.model;

/**
 * 爆炸类
 * 考虑到同时爆炸定义个类
 * <p>
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-31 16:24
 * Created by IntelliJ IDEA.
 */
public class Bomb {

    private int x;
    private int y;//坐标
    public boolean islive = true;
    private int time = 6; //炸弹生命

    public Bomb() {
        super();
    }

    public Bomb(int x, int y) {
        super();
        this.x = x;
        this.y = y;
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

    public int getTime() {
        return time;
    }

    //生命递减
    public void livedown() {
        if (time > 0) {
            time--;
        } else {
            islive = false;
        }
    }

}
