package com.paulniu.dao.impl;

import com.paulniu.dao.SellerDao;
import com.paulniu.domain.Seller;
import com.paulniu.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:44
 * version: 商家数据库操作实现类
 * desc:
 **/
public class ISellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findById(int id) {
        String sql = "select * from tab_seller where sid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),id);
    }
}
