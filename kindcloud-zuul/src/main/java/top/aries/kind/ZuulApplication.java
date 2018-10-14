package top.aries.kind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-09-20 11:32
 * Created by IntelliJ IDEA.
 */
@SpringBootApplication
@EnableDiscoveryClient  //服务注册与发现
@EnableFeignClients     //允许远程调用
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
