package com.paulniu.service;

/**
 * 收藏相关Service
 */
public interface FavoriteService {

    /**
     * 判断是否收藏
     *
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏
     *
     * @param rid
     * @param uid
     */
    void addFavorite(String rid, int uid);

}
