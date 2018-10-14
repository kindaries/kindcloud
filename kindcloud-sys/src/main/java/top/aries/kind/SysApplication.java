package top.aries.kind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-09-20 11:32
 * Created by IntelliJ IDEA.
 */
@SpringBootApplication
@EnableDiscoveryClient //服务注册与发现
@EnableFeignClients   //允许远程调用
@MapperScan("top.aries.kind.mapper")//将项目中对应的mapper类的路径加进来就可以了
public class SysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysApplication.class, args);
	}

}
