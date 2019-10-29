package com.paulniu.dao;

import com.paulniu.domain.Favorite;

/**
 * 收藏数据库操作接口
 */
public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     *
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid 查询收藏次数
     *
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     *
     * @param i
     * @param uid
     */
    void add(int i, int uid);

}
