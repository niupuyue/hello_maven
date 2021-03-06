package com.paulniu.dao.impl;

import com.paulniu.dao.RouteDao;
import com.paulniu.domain.Route;
import com.paulniu.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅游路线数据库操作实现类
 */
public class IRouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            String[] tags = rname.split("/");
            if (tags.length > 0) {
                for (int i = 0; i < tags.length; i++) {
                    if (i == 0) {
                        sb.append(" and rname like ? ");
                    } else {
                        sb.append(" or rname like ? ");
                    }
                    params.add("%" + tags[i] + "%");
                }
            } else {
                sb.append(" and rname like ? ");
                params.add("%" + rname + "%");
            }
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        // String sql = "select * from tab_route where cid = ? and rname like ? limit ? , ?";
        String sql = " select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            String[] tags = rname.split("/");
            if (tags.length > 0) {
                for (int i = 0; i < tags.length; i++) {
                    if (i == 0) {
                        sb.append(" and rname like ? ");
                    } else {
                        sb.append(" or rname like ? ");
                    }
                    params.add("%" + tags[i] + "%");
                }
            } else {
                sb.append(" and rname like ? ");
                params.add("%" + rname + "%");
            }
        }
        // 分页条件
        sb.append(" limit ? , ?");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
