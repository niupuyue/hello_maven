package com.paulniu.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulniu.domain.ResultInfo;
import com.paulniu.domain.User;
import com.paulniu.service.UserService;
import com.paulniu.service.impl.IUserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户名和密码
        Map<String,String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService userService = new IUserServiceImpl();
        User u = userService.login(user);
        ResultInfo info = new ResultInfo();

        if (u == null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if (u != null && "N".equalsIgnoreCase(u.getStatus())){
            // 用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活账户，请激活");
        }
        if (u != null && "Y".equalsIgnoreCase(u.getStatus())){
            request.getSession().setAttribute("user",u);
            // 登陆成功
            info.setFlag(true);
        }
        // 设置响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
