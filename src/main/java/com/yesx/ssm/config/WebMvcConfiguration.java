package com.yesx.ssm.config;

import com.yesx.ssm.interceptors.PageContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SPRING MVC相关配置
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter /*implements EnvironmentAware*/ {
//    @Override
//    public void setEnvironment(Environment environment) {
//
//    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
//        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
//		registry.addInterceptor(new JsonErrorMsgInterceptor()).addPathPatterns("*.json");
        registry.addInterceptor(new PageContextInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
