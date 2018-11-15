package top.aries.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * HTTP连接工具类
 * Created by IntelliJ IDEA.
 *
 * @author：zhangshuai Date: 2018-05-31
 * Time: 9:21
 */
public class HttpClientUtil {

    /**
     * 以get形式向服务器请求数据
     *
     * @param url 请求链接
     * @return
     * @throws IOException
     */
    public static String sendGet(String url) throws IOException {
        String result = "";
        URL realURL = new URL(url);
        URLConnection conn = realURL.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
        conn.connect();
        Map<String, List<String>> map = conn.getHeaderFields();
        for (String s : map.keySet()) {
            //System.out.println(s + "-->" + map.get(s));
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += "\n" + line;
        }
        return result;
    }

    /**
     * 以post形式向服务器请求数据
     *
     * @param url   请求地址
     * @param param 请求数据
     * @return
     * @throws IOException
     */
    public static String sendPost(String url, String param) throws IOException {
        String result = "";
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
        //post设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(param);
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += "\n" + line;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String cityUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=113.110.192.6";
        String cityJSON = sendGet(cityUrl);
        System.out.println(cityJSON);
    }
}