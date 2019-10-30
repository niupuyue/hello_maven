package com.paulniu.service.impl;

import com.paulniu.dao.UserDao;
import com.paulniu.dao.impl.IUserDaoImpl;
import com.paulniu.domain.PageBean;
import com.paulniu.domain.Route;
import com.paulniu.domain.User;
import com.paulniu.service.UserService;
import com.paulniu.util.UUIDUtils;

import java.util.List;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:44
 * version: 用户服务实现类
 * desc:
 **/
public class IUserServiceImpl implements UserService {

    private UserDao userDao = new IUserDaoImpl();

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        // 根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        if (u != null) {
            // 用户名已经存在，注册失败
            return false;
        }
        // 设置激活码
        user.setCode(UUIDUtils.getUuid());
        // 设置激活状态
        user.setStatus("Y");
        int count = userDao.save(user);
        if (count > 0) {
            // 发送激活邮件
            // TODO
            return true;
        } else {
            // 注册失败
            return false;
        }

    }

    /**
     * 激活用户
     *
     * @param code
     * @return
     */
    @Override
    public boolean activie(String code) {
        User user = userDao.findByCode(code);
        if (user != null) {
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public PageBean<Route> myfavorite(int uid, int currentPage, int pageSize) {
        // 封装PageBean
        PageBean<Route> pageBean = new PageBean<>();
        // 设置当前页码
        pageBean.setCurrentPage(currentPage);
        // 设置每页显示的条数
        pageBean.setPageSize(pageSize);

        // 设置总条数
        int totalCount = userDao.findFavoriteTotalCount(uid);
        pageBean.setTotalCount(totalCount);
        // 设置单页面显示数据集合
        int start = (currentPage - 1) * pageSize;// 开始记录数
        List<Route> list = userDao.myFavorite(uid,start,pageSize);
        pageBean.setList(list);
        return pageBean;
    }
}
