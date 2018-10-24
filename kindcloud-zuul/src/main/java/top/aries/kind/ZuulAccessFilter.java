package top.aries.kind;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;


public class ZuulAccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(ZuulAccessFilter.class);

    //URL 表达式
    private final PathMatcher pathMatcher = new AntPathMatcher();

    //忽略的参数
    private static Set<String> ingoreurlPatterns = new HashSet<String>() {{
        add("/**/script/**");
        add("/**/html/**");
        add("/**/*.ico");
        add("/**/*.js");
    }};

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //BEGIN: add by wxb for [控制是否需要进行鉴权拦截]  on 2018-07-16
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI();
        for (String pattern : ingoreurlPatterns) {
            if (pathMatcher.match(pattern, url)) {//如果匹配到则不执行这个方法
                return false;
            }
        }
        //end
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURI()));

        ctx.set("logFlag", 1);
        ctx.set("startTime", System.currentTimeMillis());// 请求开始时间

        log.info("access token ok");
        return null;
    }

    /**
     * @return void    返回类型
     * @throws IOException **
     * @throws
     * @Title: writeResponse
     * @Description: 写入返回数据
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