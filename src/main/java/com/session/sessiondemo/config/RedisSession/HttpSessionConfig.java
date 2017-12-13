/*
 * All rights Reserved, Designed By CavanLiu
 * Copyright (c) 2017 by Energy Blockchain Labs.
 *
 * @ClassName:     HttpSessionConfig.java
 * @Description:   (用一句话描述该文件的功能)
 *
 * @author:        CavanLiu
 * @version:       V2.0.0
 * @Date:          17-12-12 下午5:52
 */

package com.session.sessiondemo.config.RedisSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)    // 设置Session最大超时时间为30分钟
public class HttpSessionConfig
{
}
