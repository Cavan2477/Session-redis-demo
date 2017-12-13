/*
 * All rights Reserved, Designed By CavanLiu
 * Copyright (c) 2017 by Energy Blockchain Labs.
 *
 * @ClassName:     RedisSessionConfig.java
 * @Description:   (用一句话描述该文件的功能)
 *
 * @author:        CavanLiu
 * @version:       V2.0.0
 * @Date:          17-11-27 下午6:05
 */

package com.session.sessiondemo.config.RedisSession;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by CavanLiu on 2017/11/21 0021.
 */
@Configuration
//@ConfigurationProperties(prefix="spring.redis")
@EnableCaching                                                  // 启用缓存
public class RedisConfig extends CachingConfigurerSupport
{
    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    /**
     * redis连接配置
     *
     * @return
     */
    /*@Bean
    public static ConfigureRedisAction configureRedisAction()
    {
        return ConfigureRedisAction.NO_OP;
    }*/

    @Bean
    public KeyGenerator keyGenerator()
    {
        return new KeyGenerator()
        {
            @Override
            public Object generate(Object object, Method method, Object... objects)
            {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(object.getClass().getName());
                stringBuilder.append(method.getName());

                for (Object obj : objects)
                    stringBuilder.append(obj.toString());

                return stringBuilder.toString();
            }
        };
    }

    /**
     * 默认连接端口:6379
     * 默认连接地址:localhost
     * 需要修改则可以使用JedisConnectionFactory的setPort()方法和setHostName()方法来修改默认的端口和连接地址(废弃)
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory()
    {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

        // 自行添加配置
        // 1.可使用application.properties中属性配置（推荐）
        // 2.硬编码配置

        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setTimeout(timeout);

        return jedisConnectionFactory;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate)
    {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        cacheManager.setDefaultExpiration(600);  //设置 key-value 超时时间

        return cacheManager;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);

        setSerializer(template);    //设置序列化工具，就不必实现Serializable接口
        template.afterPropertiesSet();

        return template;
    }

    private void setSerializer(StringRedisTemplate template)
    {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
