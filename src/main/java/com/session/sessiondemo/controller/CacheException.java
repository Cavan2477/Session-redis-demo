/*
 * All rights Reserved, Designed By CavanLiu
 * Copyright (c) 2017 by Energy Blockchain Labs.
 *
 * @ClassName:     CacheException.java
 * @Description:   (用一句话描述该文件的功能)
 *
 * @author:        CavanLiu
 * @version:       V2.0.0
 * @Date:          17-12-12 下午6:00
 */

package com.session.sessiondemo.controller;

/**
 * Created by zggdczfr on 2017/3/1.
 */
public class CacheException extends RuntimeException {

    public CacheException(String message){ super(message);}

    public CacheException(String message, Throwable cause){ super(message, cause);}
}