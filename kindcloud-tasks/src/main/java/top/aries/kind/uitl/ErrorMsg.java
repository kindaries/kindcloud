package top.aries.kind.uitl;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-09-10 10:52
 * Created by IntelliJ IDEA.
 */
public enum ErrorMsg {

    SUCCESS("200", "成功"),
    ERROR("500", "失败"),
    LOGINERROR("001", "未登录"),
    ADMINERROR("002", "权限不足"),
    USERERROR("003", "用户错误"),
    EMAILERROR("004", "邮箱错误"),
    INVITEERROR("005", "校验码错误"),
    PASSWORDERROR("006", "密码错误"),
    CODEERROR("007", "验证码错误"),
    SYSTEMERROR("400", "系统错误");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
