package com.paulniu.service;

import com.paulniu.domain.User;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:44
 * version: 用户操作Service服务
 * desc:
 **/
public interface UserService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    boolean activie(String code);

    User login(User user);
}
