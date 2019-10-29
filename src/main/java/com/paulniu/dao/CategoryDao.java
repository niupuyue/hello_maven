package com.paulniu.dao;


import com.paulniu.domain.Category;

import java.util.List;

/**
 * 分类数据库操作接口
 */
public interface CategoryDao {

    List<Category> findAll();

}
