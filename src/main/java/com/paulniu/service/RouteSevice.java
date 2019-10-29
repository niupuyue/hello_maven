package com.paulniu.service;

import com.paulniu.domain.PageBean;
import com.paulniu.domain.Route;

/**
 * 路线Service
 */
public interface RouteSevice {

    /**
     * 根据类别进行分页查询
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据id查询
     *
     * @param rid
     * @return
     */
    Route findOne(String rid);

}
