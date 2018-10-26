package top.aries.kind;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录拦截器
 * <p>
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-24 14:46
 * Created by IntelliJ IDEA.
 */
public class ZuulLoginFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //URL 表达式
    private final PathMatcher pathMatcher = new AntPathMatcher();

    //忽略的参数
    private static Set<String> ingoreurlPatterns = new HashSet<String>() {{
        add("/**/script/**");
        add("/**/html/**");
        add("/**/*.ico");
        add("/**/*.js");
    }};

    //放行的URL
    private static Set<String> passActionSet = new HashSet<String>() {{
        add("/**/register.do");       //注册
        add("/**/retrieve.do");       //找回
        add("/**/login.do");          //登录
        add("/sys/**");
        add("/tasks/**");
    }};

    @Override
    public String filterType() {    //过滤器的类型
        return "pre";               //pre前置过滤、route在路由请求时候被调用
    }                               //error请求发生错误时调用、post在route和error之后被调用

    @Override
    public int filterOrder() {
        return 0;           //优先级为0,数字越大,优先级越低
    }

    @Override
    public boolean shouldFilter() {
        logger.info("触发登录拦截......");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI();
        for (String pattern : ingoreurlPatterns) {
            if (pathMatcher.match(pattern, url)) {                  //匹配URL
                return false;
            }
        }
        return true;        //是否执行该过滤器,此处为true,说明需要过滤
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI();
        logger.info(String.format("%s request to %s", request.getMethod(), url) + "触发拦截机制......");
        for (String pattern : passActionSet) {
            if (pathMatcher.match(pattern, url)) {                  //匹配URL
                //TODO 此处可以返回日志标志 控制是否需要记录日志
                ctx.set("logFlag", 1);
                ctx.set("startTime", System.currentTimeMillis());// 请求开始时间
                logger.info("通过权限认证");
                return null;
            }
        }
        try {
            logger.info("权限不足吖");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            writeResponse(ctx.getResponse(),
                    "{" +
                            "\"success\": false," +
                            "\"msgCode\": 10002," +
                            "\"message\": \"权限不足吖\"" +
                            "}");
        } catch (IOException e) {
            logger.error(e.getMessage(), "返回发生异常:" + e);
        }
        return null;
    }

    /**
     * 写入返回数据
     *
     * @param servletResponse
     * @param str
     * @throws IOException
     */
    private void writeResponse(HttpServletResponse servletResponse, String str) throws IOException {
        //设置编码
        servletResponse.setContentType("application/json; charset=utf-8");
        OutputStream outStream = null;
        try {
            outStream = servletResponse.getOutputStream();
            outStream.write(str.getBytes(servletResponse.getCharacterEncoding()));
            outStream.flush();
        } finally {
            if (null != outStream) {
                outStream.flush();
            }
        }
    }

}