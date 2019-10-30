package com.paulniu.dao.impl;

import com.paulniu.dao.UserDao;
import com.paulniu.domain.Route;
import com.paulniu.domain.User;
import com.paulniu.util.JDBCUtils;
import com.paulniu.util.MailUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:43
 * version: 用户数据库操作实现类
 * desc:
 **/
public class IUserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public int save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        int count = template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
        return count;
    }

    public static void main(String[] args) throws Exception { // 做测试用
//        String sql = "select tab_route.* from tab_route,tab_favorite where 1 = 1 and tab_route.`rid` = tab_favorite.`rid`";
//        StringBuilder sb = new StringBuilder(sql);
//        List params = new ArrayList();
//        sb.append(" and tab_favorite.`uid` = ? ");
//        params.add(11);
//        // 分页条件
//        sb.append(" limit ? , ? ");
//        params.add(0);
//        params.add(10);
//        sql = sb.toString();
//        List<Route> routes = new JdbcTemplate(JDBCUtils.getDataSource()).query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
//        System.out.println(routes);
    }

    /**
     * 根据激活码查询用户对象
     *
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    /**
     * 更改指定用户的激活状态
     *
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql, user.getUid());
    }

    /**
     * 根据用户名和密码查询方法
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public int findFavoriteTotalCount(int uid) {
        String sql = "select count(*) from tab_favorite where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (uid != 0) {
            sb.append(" and uid = ? ");
            params.add(uid);
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> myFavorite(int uid, int start, int pageSize) {
        String sql = "select tab_route.* from tab_route,tab_favorite where 1 = 1 and tab_route.`rid` = tab_favorite.`rid`";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (uid != 0) {
            sb.append(" and tab_favorite.`uid` = ? ");
            params.add(uid);
        }
        // 分页条件
        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }
}
