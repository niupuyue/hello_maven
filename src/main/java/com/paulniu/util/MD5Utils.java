package com.paulniu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法，运行结果与Mysql的md5()函数相同
 * 将明文密码转化成MD5密码
 * 123456->e10adc3949ba59abbe56e057f20f883e
 */
public class MD5Utils {

    private static String[] hex = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    public MD5Utils() {
    }

    /**
     * 将明文密码转换成MD5密码
     */
    public static String encodeByMd5(String password){
        // Java中MessageDigest类封装了MD5算法和SHA算法
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 调用MD5算法，返回16byte类型的数值
            byte[] byteArray = md5.digest(password.getBytes());
            // 将byte数组转换成String
            return byteArrayToHexString(byteArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将byte数组转换成字符串(16进制)
     */
    private static String byteArrayToHexString(byte[] byteArray){
        StringBuffer sb = new StringBuffer();
        for (byte b:byteArray){
            // 取出一个byte类型，转换成String类型
            String hex = byteToHexString(b);
            // 将转换之后的数据放在StringBuffer中
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将byte转成16进制字符串
     */
    private static String byteToHexString(byte b){
        //将byte类型赋给int类型
        int n = b;
        //如果n是负数
        if(n < 0){
            //转正数
            //-31的16进制数，等价于求225的16进制数
            n = 256 + n;
        }
        //商(14)，数组的下标
        int d1 = n / 16;
        //余(1)，数组的下标
        int d2 = n % 16;
        //通过下标取值
        return hex[d1] + hex[d2];
    }

}
