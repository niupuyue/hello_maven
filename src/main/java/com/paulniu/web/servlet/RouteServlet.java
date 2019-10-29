package com.paulniu.web.servlet;

import com.paulniu.domain.PageBean;
import com.paulniu.domain.Route;
import com.paulniu.domain.User;
import com.paulniu.service.FavoriteService;
import com.paulniu.service.RouteSevice;
import com.paulniu.service.impl.IFavoriteServiceImpl;
import com.paulniu.service.impl.IRouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteSevice routeSevice = new IRouteServiceImpl();
    private FavoriteService favoriteService = new IFavoriteServiceImpl();

    /**
     * 分页查询路线
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        // 接收rname 路线名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        // 类别id
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equalsIgnoreCase(cidStr)) {
            cid = Integer.parseInt(cidStr);
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

        // 调用service查询
        PageBean<Route> pageBean = routeSevice.pageQuery(cid, currentPage, pageSize, rname);
        writeValue(pageBean, response);
    }

    /**
     * 根据id查询一个旅游路线信息
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        Route route = routeSevice.findOne(rid);
        writeValue(route, response);
    }

    /**
     * 判断用户是否收藏过该路线
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        // 调用service判断是否已经加入收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag, response);
    }

    /**
     * 将路线加入到收藏
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        favoriteService.addFavorite(rid, uid);
    }


}
