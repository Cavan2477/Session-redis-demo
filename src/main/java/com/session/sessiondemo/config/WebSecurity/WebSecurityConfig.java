/*
 * All rights Reserved, Designed By CavanLiu
 * Copyright (c) 2017 by Energy Blockchain Labs.
 *
 * @ClassName:     WebSecurityConfig.java
 * @Description:   (用一句话描述该文件的功能)
 *
 * @author:        CavanLiu
 * @version:       V2.0.0
 * @Date:          17-11-28 下午4:16
 */

package com.session.sessiondemo.config.WebSecurity;

import com.session.sessiondemo.config.WebSecurity.service.WebUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by CavanLiu on 2017/11/21 0021.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${server.session.maxmum}")
    private Integer sessionMaxmum;

    @Bean
    public WebUserService customUserService()
    {
        return new WebUserService();
    }

    /**
     * 自定义授权配置
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        super.configure(auth);

        // 添加自定义认证UserDetailsService,设置加密算法
        auth.userDetailsService(customUserService());

        // 不删除用户凭证以记住用户
        auth.eraseCredentials(false);
    }

    /**
     * 自定义http安全配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //String strAdminUrl      = EnumUserRoleType.USER_ROLE_TYPE_ADMIN.getUrl();
        //String strAdminRole     = EnumUserRoleType.USER_ROLE_TYPE_ADMIN.getDescEn();
        //String strOwnerUrl      = EnumUserRoleType.USER_ROLE_TYPE_OWNER.getUrl();
        //String strOwnerRole     = EnumUserRoleType.USER_ROLE_TYPE_OWNER.getDescEn();
        //String strStamperUrl    = EnumUserRoleType.USER_ROLE_TYPE_STAMPER.getUrl();
        //String strStamperRole   = EnumUserRoleType.USER_ROLE_TYPE_STAMPER.getDescEn();

        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();

        //
        //http.addFilterAt(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.authorizeRequests().anyRequest().fullyAuthenticated();

        //自定义过滤器
        //MyFilterSecurityInterceptor filterSecurityInterceptor = new MyFilterSecurityInterceptor(securityMetadataSource,accessDecisionManager(),authenticationManagerBean());

        //在适当的地方加入
        //http.addFilterAt(filterSecurityInterceptor, FilterSecurityInterceptor.class);

        //http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").and().exceptionHandling().accessDeniedPage("/accessDenied");

        // TODO: 2017/11/21 0021 此处未添加其他用户，待修改为从数据库读取角色类型
        /*http.authorizeRequests()
                // 部署测试页面可以任意访问
                .antMatchers(this.DEPLOY_TEST)
                .permitAll()

                // 所有请求需要登录方可访问
                .anyRequest()
                .authenticated()

                // TODO: 2017/11/21 0021 开启后REST接口无法接收参数，原因未知
                // 访问路径授权
                //.antMatchers(strAdminUrl).hasRole(strAdminRole)
                //.antMatchers(strAdminUrl, strOwnerUrl, strStamperUrl).hasAnyRole(strAdminRole, strOwnerRole, strStamperRole)

                // cookie设定
                //.and()
                //.rememberMe()
                //    .tokenValiditySeconds(cookieValiditySeconds)    // cookie保存一星期
                    //.key("")                                        // cookie私钥配置

                // 定制登录行为
                .and()
                .formLogin()
                    //.loginPage(this.USER_LOGIN)                       // 用户未登录时拦截至登录页面
                    //.loginProcessingUrl(this.USER_LOGIN)
                    //.defaultSuccessUrl(this.INDEX)                    // 用户登录成功后跳转的页面
                    //.failureForwardUrl(this.USER_LOGIN_ERR)           // 用户登录失败后跳转的页面
                    .permitAll()                                        // 登录页面可以任意访问

                // 定制登出行为
                .and()
                .logout()
                    .permitAll()                                        // 注销请求可以任意访问
                    .logoutSuccessUrl(this.USER_LOGIN);                 // 注销成功后跳转的页面*/

        // 禁用csrf跨站请求
        http.csrf().disable();

        //-----------------------------------------session管理-----------------------------------------
        // session失效后跳转, 只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        //http.sessionManagement().invalidSessionUrl(USER_LOGIN);
        //http.sessionManagement().maximumSessions(sessionMaxmum).expiredUrl(USER_LOGIN);
    }

    /**
     * 自定义web安全配置
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        super.configure(web);

        // 忽略相关页面
        web.ignoring().antMatchers("/assets*");
        web.ignoring().antMatchers("/components*");
        web.ignoring().antMatchers("/css*");
        web.ignoring().antMatchers("/images*");
        web.ignoring().antMatchers("/js*");
        web.ignoring().antMatchers("/mustache*");
        web.ignoring().antMatchers("/favicon.ico");
        //web.ignoring().antMatchers("/login");
    }

    /**
     * 添加httpSession监听
     * @return
     */
    /*@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher()
    {
        return new HttpSessionEventPublisher();
    }*/

    /*UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception
    {
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();

        usernamePasswordAuthenticationFilter.setPostOnly(true);
        usernamePasswordAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        usernamePasswordAuthenticationFilter.setUsernameParameter("name_key");
        usernamePasswordAuthenticationFilter.setPasswordParameter("pwd_key");
        usernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/checkLogin", "POST"));
        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());

        return usernamePasswordAuthenticationFilter;
    }*/

    /**
     * 登录成功后的跳转
     * 如果需要根据不同的角色做不同的跳转处理,那么继承AuthenticationSuccessHandler重写方法
     * @return
     */
    /*private SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler()
    {
        return new SimpleUrlAuthenticationSuccessHandler(INDEX);
    }*/
}
