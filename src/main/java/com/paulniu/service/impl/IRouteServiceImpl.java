package com.paulniu.service.impl;

import com.paulniu.dao.FavoriteDao;
import com.paulniu.dao.RouteDao;
import com.paulniu.dao.RouteImgDao;
import com.paulniu.dao.SellerDao;
import com.paulniu.dao.impl.IFavoriteDaoImpl;
import com.paulniu.dao.impl.IRouteDaoImpl;
import com.paulniu.dao.impl.IRouteImgDaoImpl;
import com.paulniu.dao.impl.ISellerDaoImpl;
import com.paulniu.domain.PageBean;
import com.paulniu.domain.Route;
import com.paulniu.domain.RouteImg;
import com.paulniu.domain.Seller;
import com.paulniu.service.RouteSevice;

import java.util.List;

public class IRouteServiceImpl implements RouteSevice {

    private RouteDao routeDao = new IRouteDaoImpl();

    private RouteImgDao routeImgDao = new IRouteImgDaoImpl();

    private SellerDao sellerDao = new ISellerDaoImpl();

    private FavoriteDao favoriteDao = new IFavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        // 封装PageBean
        PageBean<Route> pageBean = new PageBean<Route>();
        // 设置当前页码
        pageBean.setCurrentPage(currentPage);
        // 设置每页显示条数
        pageBean.setPageSize(pageSize);

        // 设置总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        pageBean.setTotalCount(totalCount);
        // 设置单签页显示数据集合
        int start = (currentPage - 1) * pageSize;// 开始记录数
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pageBean.setList(list);

        // 设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        // 根据id在route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        // 根据routeid查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        // 将集合设置到route对象中
        route.setRouteImgList(routeImgList);
        // 根据route的sid(商家id)查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        // 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
