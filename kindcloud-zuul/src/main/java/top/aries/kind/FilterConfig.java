package top.aries.kind;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 拦截器配置进Spring
 * <p>
 * Author: kindaries
 * Company: shenzhen
 * Date: Created in 2018-10-24 14:46
 * Created by IntelliJ IDEA.
 */
@Configuration
public class FilterConfig {

    /***
     * URL权限拦截
     * @return
     */
    @Bean
    public ZuulLoginFilter accessLogonFilter() {
        return new ZuulLoginFilter();
    }
}
