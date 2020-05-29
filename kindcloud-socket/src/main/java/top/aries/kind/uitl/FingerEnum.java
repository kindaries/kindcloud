package top.aries.kind.uitl;

/**
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-29 15:36
 * Created by IntelliJ IDEA.
 */
public enum FingerEnum {


    STONE("01", "石头"),
    SCISSORS("02", "剪刀"),
    CLOTH("03", "布");

    private String fingerId;
    private String fingerName;

    public String getFingerId() {
        return fingerId;
    }


    public String getFingerName() {
        return fingerName;
    }

    FingerEnum(String fingerId, String fingerName) {
        this.fingerId = fingerId;
        this.fingerName = fingerName;
    }

    public String toString() {
        return this.fingerName;
    }

}
