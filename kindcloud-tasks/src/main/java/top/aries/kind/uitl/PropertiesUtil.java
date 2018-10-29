package top.aries.kind.uitl;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.*;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-08-06 9:35
 * Created by IntelliJ IDEA.
 */
public class PropertiesUtil {

    /**
     * 根据filePath读取配置文件
     *
     * @param filePath
     * @return Properties
     * @Title: getProperties
     * @Description: 第一种方式：根据文件名使用spring中的工具类进行解析
     * filePath是相对路劲，文件需在classpath目录下
     * 比如：config.properties在包com.test.config下，
     * 路径就是com/test/config/config.properties
     */
    public static Properties getProperties(String filePath) {
        Properties prop = null;
        try {
            // 通过Spring中的PropertiesLoaderUtils工具类进行获取
            prop = PropertiesLoaderUtils.loadAllProperties(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 根据filePath读取配置文件
     *
     * @param filePath
     * @return Properties
     * @throws
     * @Title: getProperties_2
     * @Description: 第二种方式：使用缓冲输入流读取配置文件，然后将其加载，再按需操作
     * 绝对路径或相对路径， 如果是相对路径，则从当前项目下的目录开始计算，
     * 如：当前项目路径/config/config.properties,
     * 相对路径就是config/config.properties
     */
    public static Properties getProperties_2(String filePath) {
        Properties prop = new Properties();
        try {
            // 通过输入缓冲流进行读取配置文件
            InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            // 加载输入流
            prop.load(InputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 根据filePath读取配置文件
     *
     * @param filePath
     * @return Properties
     * @Title: getProperties_3
     * @Description: 第三种方式：
     * 相对路径， properties文件需在classpath目录下，
     * 比如：config.properties在包com.test.config下，
     * 路径就是/com/test/config/config.properties
     */
    public static Properties getProperties_3(String filePath) {
        Properties prop = new Properties();
        try {
            InputStream inputStream = PropertiesUtil.class.getResourceAsStream(filePath);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 获取所有配置信息
     *
     * @param props
     * @return Map
     * @Title: getAllProperties
     * @Description: 获取所有配置信息
     */
    private static Map getAllProperties(Properties props) {
        Map rnMap = new HashMap();
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String value = props.getProperty(key);
            rnMap.put(key, value);
        }
        return rnMap;
    }

    /**
     * 动态修改配置文件属性
     *
     * @param filePath
     * @param inMap
     * @return Map
     */
    public static Map setProperties(String filePath, Map inMap) {
        Map rnMap = new HashMap();
        FileOutputStream fileOut = null;
        Properties prop = null;
        try {
            // 通过Spring中的PropertiesLoaderUtils工具类进行获取
            prop = PropertiesLoaderUtils.loadAllProperties(filePath);
            String pathUrl = PropertiesUtil.class.getResource("/" + filePath).getPath();
            //true表示追加打开,false表示修改而不是追加
            fileOut = new FileOutputStream(pathUrl, false);
            Set<String> sets = inMap.keySet();
            for (String key : sets) {
                prop.setProperty(key, (String) inMap.get(key));
            }
            prop.store(fileOut, "Update '" + sets + "' value");
            fileOut.close();
            rnMap.put("error", ErrorMsg.SUCCESS.getCode());
            rnMap.put("msg", "修改成功！");
        } catch (Exception e) {
            rnMap.put("error", ErrorMsg.ERROR.getCode());
            rnMap.put("msg", "修改失败！");
            e.printStackTrace();
        }
        return rnMap;
    }

    /**
     * 根据filePath读取配置
     *
     * @param filePath
     * @param key
     * @return
     */
    public static String getPropertie(String filePath, String key) {
        Properties prop = getProperties(filePath);
        String value = prop.getProperty(key);
        return value;
    }

    /**
     * 根据key获取默认配置
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        Properties pro = getProperties("config.properties");
        String value = pro.getProperty(key);
        return value;
    }

    public static void main(String[] args) {
        Properties properties_1 = getProperties("jdbc.properties");
        String str = properties_1.getProperty("jdbc.driverClassName");
        System.out.println("**********" + str + "**********");
        Properties properties_2 = getProperties_2("E:\\work\\kindaries\\src\\main\\resources\\jdbc.properties");
        String url = properties_2.getProperty("jdbc.url");
        System.out.println("**********" + url + "**********");
        Properties properties_3 = getProperties_3("/jdbc.properties");
        String username = properties_3.getProperty("jdbc.username");
        System.out.println("**********" + username + "**********");
        ResourceBundle resource = ResourceBundle.getBundle("jdbc");
        String password = resource.getString("jdbc.password");
        System.out.println("**********" + password + "**********");
        Map map = getAllProperties(properties_3);
        System.out.println("**********" + map.toString() + "**********");
        System.out.println("**********key1=" + getPropertie("mail.properties", "key1") + "**********");
        Map inMap = new HashMap();
        inMap.put("key1", "value5");
        inMap.put("key3", "value5");
        Map rnMap = setProperties("mail.properties", inMap);
        System.out.println("**********" + rnMap.toString() + "**********");
        System.out.println("**********key1=" + getPropertie("mail.properties", "key1") + "**********");
    }
}
