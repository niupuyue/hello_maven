package com.paulniu.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulniu.domain.PageBean;
import com.paulniu.domain.ResultInfo;
import com.paulniu.domain.Route;
import com.paulniu.domain.User;
import com.paulniu.service.UserService;
import com.paulniu.service.impl.IUserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 所有的用户操作都在这里实现
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    // 声明UserService对象
    private UserService service = new IUserServiceImpl();

    /**
     * 注册功能
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 验证验证码
        String check = request.getParameter("check");
        // 从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            // 验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            // 将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        // 获取数据
        Map<String, String[]> map = request.getParameterMap();
        // 封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 调用service完成注册
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        // 将info对象序列化成json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将json数据写回客户端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }

    /**
     * 登录功能
     */
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 获取用户名和密码
        Map<String,String[]> map = request.getParameterMap();
        // 封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 使用service完成查询工作
        User u = service.login(user);
        ResultInfo info = new ResultInfo();

        if (u == null){
            info.setFlag(false);
            info.setErrorMsg("用户密码或密码错误");
        }
        //5.判断用户是否激活
        if(u != null && !"Y".equals(u.getStatus())){
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活");
        }
        //6.判断登录成功
        if(u != null && "Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);//登录成功标记

            //登录成功
            info.setFlag(true);
        }

        //响应数据
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);

    }

    /**
     * 查询单个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将user写回客户端

       /* ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();

        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if(code != null){
            //2.调用service完成激活
            //UserService service = new UserServiceImpl();
            boolean flag = service.activie(code);

            //3.判断标记
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else{
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    public void myfavorite(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user != null){
            uid = user.getUid();
        }

        // 当前页码，如果不传，默认第一页
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        // 每页显示条数，如果不传递默认每页显示10条记录
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 10;
        }
        PageBean<Route> myfavorite = service.myfavorite(user.getUid(),currentPage,pageSize);
        writeValue(myfavorite,response);
    }

}
