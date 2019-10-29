package com.paulniu.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:45
 * version: 字符编码filter过滤器
 * desc:
 **/
@WebFilter("/*")
public class EncodingFilter implements Filter {
    private String encoding;
    private HashMap<String, String> params = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        for (Enumeration<?> e = filterConfig.getInitParameterNames(); e.hasMoreElements(); ) {
            String name = (String) e.nextElement();
            String value = filterConfig.getInitParameter(name);
            params.put(name, value);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.err.println("--------------------------------------------------------");
        System.out.println("paulniu:编码过滤");
        System.out.println("需要转换的编码格式 encoding = " + encoding);
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("paulniu:编码过滤");
        System.err.println("--------------------------------------------------------");
    }

    @Override
    public void destroy() {
        this.encoding = null;
        this.params = null;
    }
}
