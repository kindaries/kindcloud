package top.aries.kind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-09-20 11:32
 * Created by IntelliJ IDEA.
 */
@SpringBootApplication
@EnableDiscoveryClient //服务注册与发现
@EnableFeignClients   //允许远程调用
@RestController
//@MapperScan("top.aries.kind.mapper")//将项目中对应的mapper类的路径加进来,如果mapper类有加注解则不需要
public class SysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysApplication.class, args);
	}

	@RequestMapping(value = "/", produces = {"application/json;charset=UTF-8"})
	public String hello() {
		return "hello,SysApplication!";
	}

}
