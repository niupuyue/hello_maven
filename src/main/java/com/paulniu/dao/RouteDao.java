package com.paulniu.dao;

import com.paulniu.domain.Route;

import java.util.List;

/**
 * 旅游路线数据库操作接口
 */
public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    int findTotalCount(int cid,String rname);

    /**
     * 根据cid，start，pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    List<Route> findByPage(int cid,int start,int pageSize,String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    Route findOne(int rid);

}
