package com.yigu.shop.commom.util;

/**
 * Created by brain on 2016/6/16.
 */
public interface RequestExceptionCallback2 {
    void error(Integer code, String message);
    void error(String code, String message);
}
