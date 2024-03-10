package com.sleep.config;

import com.sleep.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author SleepWalker
 * @version 1.0
 * @date 2023/1/31 22:50
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
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
                .antMatchers("/user/login").anonymous()
//                .antMatchers("/github/upload").anonymous()
                .antMatchers("/common/admin/captcha").anonymous()
                .antMatchers("/swagger/**", "/swagger-ui.html", "/swagger-resources/**", "/doc.html", "/v2/**", "/webjars/**", "/v2/api-docs-ext/**").anonymous()
//                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/**").anonymous()
                //注销接口需要认证才能访问
//                .antMatchers("/logout").authenticated()
                //查询所有标签接口必须登录后才能访问
//                .antMatchers("/content/tag/list").authenticated()
                //查询管理员用户信息必须登录后才能访问
//                .antMatchers("/getInfo").authenticated()
                //查询路由信息必须登录后才能访问
//                .antMatchers("/getRouters").authenticated()
                // 除上面外的所有请求全部需要认证访问
                .anyRequest().authenticated();


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
