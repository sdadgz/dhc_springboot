package cn.sdadgz.dhc_springboot.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Resource
    JwtAuthenticationInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**").order(-1) // 似乎是小的先走
                .excludePathPatterns(
                        "/user/login", // 用户登录
                        "/user", // 用户注册
                        "/blog/*/blogs", // 博客s
                        "/blog/*/blog/*", // 博客
                        "/img/*/banner", // 主页banner
                        "/img/*/background", // 背景图片
                        "/static/**");
    }

}
