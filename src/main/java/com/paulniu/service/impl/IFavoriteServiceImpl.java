package com.paulniu.service.impl;

import com.paulniu.dao.FavoriteDao;
import com.paulniu.dao.impl.IFavoriteDaoImpl;
import com.paulniu.domain.Favorite;
import com.paulniu.service.FavoriteService;

/**
 * 收藏操作服务实现类
 */
public class IFavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new IFavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;
    }

    @Override
    public void addFavorite(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }
}
