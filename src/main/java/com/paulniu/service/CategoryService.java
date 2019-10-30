package com.paulniu.service;

import com.paulniu.domain.Category;

import java.util.List;

/**
 * 分类相关Service
 */
public interface CategoryService {

    List<Category> findAll(boolean isFromRedis);

}
