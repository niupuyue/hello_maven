package com.paulniu.util;

import java.util.UUID;

/**
 * 产生UUI随机字符串工具类
 */
public final class UUIDUtils {

    public UUIDUtils() {
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
