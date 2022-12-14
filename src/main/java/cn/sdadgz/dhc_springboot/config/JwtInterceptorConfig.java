package cn.sdadgz.dhc_springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Resource
    JwtAuthenticationInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**").order(-1) // 似乎是小的先走
                .excludePathPatterns(
                        "/user/login", // 用户登录
                        "/user/register", // 注册
                        "/essay/text", // 显示的html
                        "/essay/page", // 获取分页
                        "/carousel/page", // 获取轮播图分页
                        "/carousel", // 获取全部轮播图
                        "/friendLink/page", // 友情连接分页
                        "/headItem/**", // 头
                        "/static/**");
    }

}
