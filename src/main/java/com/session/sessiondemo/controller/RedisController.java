/*
 * All rights Reserved, Designed By CavanLiu
 * Copyright (c) 2017 by Energy Blockchain Labs.
 *
 * @ClassName:     RedisController.java
 * @Description:   (用一句话描述该文件的功能)
 *
 * @author:        CavanLiu
 * @version:       V2.0.0
 * @Date:          17-12-12 下午5:56
 */

package com.session.sessiondemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zggdczfr on 2017/2/28.
 */
@RestController
public class RedisController
{
    @Autowired
    private UserService userService;

    @Value("${server.port}")
    String port;

    private String SESSION_KEY_USER = "user";

    @PostMapping("/user")
    public Object login(@RequestBody User user, HttpSession httpSession)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("SessionId", httpSession.getId());
        map.put("ServerPort", "服务端口号为 " + port);
        map.put(SESSION_KEY_USER, user.getName());

        if (httpSession.isNew()) {
            System.out.println("new session id:" + map.get("SessionId"));
        } else {
            System.out.println("current session id:" + map.get("SessionId"));
            System.out.println("user:" + httpSession.getAttribute(SESSION_KEY_USER));
        }

        httpSession.setAttribute(SESSION_KEY_USER, user.getName());

        return map;
    }

    @GetMapping(value = "/user_invalid")
    public String logout(@RequestParam("name") String user, HttpSession httpSession)
    {
        httpSession.removeAttribute(SESSION_KEY_USER);
        httpSession.invalidate();

        return "success";
    }

    @GetMapping("/session")
    public Object getSession(HttpSession httpSession/*HttpServletRequest request*/)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("SessionId", httpSession.getId());
        map.put("ServerPort", "服务端口号为 " + port);

        if (httpSession.isNew()) {
            System.out.println("new session id:" + map.get("SessionId"));

            httpSession.setAttribute("user", "user1");
        } else {
            System.out.println("current session id:" + map.get("SessionId"));
            System.out.println("user:" + httpSession.getAttribute("user"));
        }

        return map;
    }

    @GetMapping(value = "/session_invalid")
    public String sessionInvalid(HttpServletRequest request)
    {
        request.getSession().removeAttribute(SESSION_KEY_USER);

        request.getSession().invalidate();

        return "success";
    }

    @GetMapping(value = "/redis")
    public String redisTest()
    {
        System.out.println("====== 进行 Redis 缓存试验 ======");

        User user = new User();

        //生成第一个用户的唯一标识符 UUID
        String u1_uuid = UUID.randomUUID().toString();

        //去掉 UUID 的 - 符号
        String uuid1 = u1_uuid.substring(0, 8) + u1_uuid.substring(9, 13) + u1_uuid.substring(14, 18) + u1_uuid.substring(19, 23) + u1_uuid.substring(24);

        user.setUuid(uuid1);
        user.setAge(20);
        user.setName("张三");

        try {
            userService.save(user);
        } catch (Exception e) {
            System.out.println("保存用户出现异常");
        }

        //第一次查询
        System.out.println(userService.findByUuid(user.getUuid()));

        //通过缓存查询
        System.out.println(userService.findByUuid(user.getUuid()));

        System.out.println("====== 修改 Redis 缓存数据 ======");
        //修改用户数据
        user.setAge(18);
        user.setName("李四");
        System.out.println(userService.update(user));

        System.out.println(userService.findByUuid(user.getUuid()));

        return "success";
    }
}
