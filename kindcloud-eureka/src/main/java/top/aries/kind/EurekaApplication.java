package top.aries.kind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-09-20 11:32
 * Created by IntelliJ IDEA.
 */
@SpringBootApplication
@EnableEurekaServer     //开启Eureka支持
public class EurekaApplication {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(EurekaApplication.class);

    public static void main(String[] args) {
        logger.info("EurekaApplication启动中......");
        SpringApplication.run(EurekaApplication.class, args);
    }

}
