package top.aries.kind.model;

/**
 * 地图坐标标记
 * 防止敌方坦克重叠
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-31 16:27
 * Created by IntelliJ IDEA.
 */
public class TankMap {

    public int[][] location = new int[345][400];

    public TankMap() {
        for (int i = 0; i < 345; i++) {
            for (int j = 0; j < 400; j++) {
                location[i][j] = 0;
            }
        }
    }

}
