package top.aries.kind.model;

/**
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-30 9:32
 * Created by IntelliJ IDEA.
 */
public class User {

    private String name;
    private String ip;

    public User(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
