package com.paulniu.dao;

import com.paulniu.domain.RouteImg;

import java.util.List;

/**
 * 旅游图片数据库操作接口
 */
public interface RouteImgDao {

    /**
     * 根据route的id查询图片
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);

}
