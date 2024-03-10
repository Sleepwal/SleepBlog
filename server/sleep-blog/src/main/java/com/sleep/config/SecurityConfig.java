package com.sleep.config;

import com.sleep.filter.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * SpringSecurity配置
 *
 * @author manson
 * @date 2023/05/14
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    final private AuthenticationEntryPoint authenticationEntryPoint;
    final private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/auth/login").anonymous()
                //注销接口需要认证才能访问
                .antMatchers("/auth/logout").authenticated()
                //个人信息接口必须登录后才能访问
                .antMatchers("/user/userInfo").authenticated()
                //添加留言
                .antMatchers("/message/addMessage").authenticated()
                //添加评论
                .antMatchers("/comment/addComment").authenticated()
                //聊天室
                .antMatchers("/chat/getChatList", "/chat/addChatMessage").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();


        //添加jwtAuthenticationTokenFilter过滤器到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //添加认证授权失败处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //关闭默认的注销功能
        http.logout().disable();

        //允许跨域
        http.cors();
    }

    //暴露AuthenticationManager，注入到容器中
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
