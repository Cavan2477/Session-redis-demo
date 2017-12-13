/*
 * All rights Reserved, Designed By CavanLiu
 * Copyright (c) 2017 by Energy Blockchain Labs.
 *
 * @ClassName:     UserDao.java
 * @Description:   (用一句话描述该文件的功能)
 *
 * @author:        CavanLiu
 * @version:       V2.0.0
 * @Date:          17-12-12 下午6:00
 */

package com.session.sessiondemo.controller;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User Dao 接口
 * Created by zggdczfr on 2017/2/28.
 */
@Mapper
public interface UserDao
{
    void delete(String uuid);

    int update(@Param("ruser") User user);

    User findByUuid(String uuid);

    int save(@Param("ruser") User user) throws Exception;
}
