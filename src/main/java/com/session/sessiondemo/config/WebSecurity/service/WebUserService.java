/*
 * All rights Reserved, Designed By CavanLiu
 * Copyright (c) 2017 by Energy Blockchain Labs.
 *
 * @ClassName:     WebUserService.java
 * @Description:   (用一句话描述该文件的功能)
 *
 * @author:        CavanLiu
 * @version:       V2.0.0
 * @Date:          17-11-27 下午6:05
 */

package com.session.sessiondemo.config.WebSecurity.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by CavanLiu on 2017/11/21 0021.
 */
@Component
public class WebUserService implements UserDetailsService
{
    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String strUsername) throws UsernameNotFoundException
    {
        if (strUsername.equals("") || strUsername == null)
            return null;

        // 校验用户是否存在
        /*boolean bRet = userInfoService.checkUserExist(strUsername);

        // TODO: 2017/11/21 0021 调整提示信息至resources/security/WebSecurity.xml
        if (!bRet) {
            throw new UsernameNotFoundException("用户不存在");
            return null;
        }

        // 获取数据库用户的密码
        String strPasswordDb = "111111";

        String strPasswordEncoder = passwordEncoder.encode(strPasswordDb);

        LoggerUtils.logger.info("用户:" + strUsername);
        LoggerUtils.logger.info("密码:" + strPasswordDb);
        LoggerUtils.logger.info("加密密码:" + strPasswordEncoder);*/

        return new User(strUsername, "111111",//strPasswordEncoder,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
