package top.aries.kind.uitl;

/**
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-30 11:35
 * Created by IntelliJ IDEA.
 */
public class StringUtil {

    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
