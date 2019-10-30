package com.paulniu.service.impl;

import com.paulniu.dao.CategoryDao;
import com.paulniu.dao.impl.ICategoryDaoImpl;
import com.paulniu.domain.Category;
import com.paulniu.service.CategoryService;
import com.paulniu.util.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 分类操作服务实现类
 */
public class ICategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new ICategoryDaoImpl();

    @Override
    public List<Category> findAll(boolean isFromRedis) {
        // 先从redis中查询数据
        Jedis jedis = JedisUtils.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category",0,-1);
        List<Category> cs = null;
        if (categorys == null || categorys.size() == 0 || !isFromRedis){
            System.out.println("从数据库中查询");
            cs = categoryDao.findAll();
            for (int i=0;i<cs.size();i++){
                jedis.zadd("category", cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            System.out.println("从redis中查询");
            cs = new ArrayList<Category>();
            for (Tuple tuple:categorys){
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
}
