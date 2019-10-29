package com.paulniu.dao;

import com.paulniu.domain.Seller;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:43
 * version: 商家数据库操作接口
 * desc:
 **/
public interface SellerDao {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Seller findById(int id);

}

