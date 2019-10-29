package com.paulniu.dao;

import com.paulniu.domain.User;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:43
 * version: 用户数据库操作接口
 * desc:
 **/
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 用户保存
     */
    int save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username,String password);

}
