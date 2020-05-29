package top.aries.kind;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.aries.util.HttpClientUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-25 11:27
 * Created by IntelliJ IDEA.
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Scheduled(fixedRate = 1000 * 1)
    public void reportCurrentTime() throws IOException {
        System.out.println("百度搜索定时任务: The time is now " + dateFormat().format(new Date()));
        String url = "https://www.baidu.com/s?ie=UTF-8&wd=kindaries%E7%AC%94%E8%AE%B0";
        String start = "data-tools='{\"title\":\"kindaries笔记\",\"url\":\"";
        String end = "\"}'><a class=\"c-tip-icon\">";
        String result = HttpClientUtil.sendGet(url);
        int star = result.indexOf(start) + 42;
        String kindUrl = result.substring(star, result.indexOf(end, star));
        System.out.println(kindUrl);
        HttpClientUtil.sendGet(kindUrl);

    }

//    //每1分钟执行一次
//    @Scheduled(cron = "0 */1 *  * * * ")
//    public void reportCurrentByCron() throws IOException {
//        //System.out.println("谷歌定时任务搜索: The time is now " + dateFormat().format(new Date()));
//    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

}
