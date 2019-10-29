package com.paulniu.service.impl;

import com.paulniu.dao.UserDao;
import com.paulniu.dao.impl.IUserDaoImpl;
import com.paulniu.domain.User;
import com.paulniu.service.UserService;
import com.paulniu.util.UUIDUtils;

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
        user.setStatus("N");
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
}
