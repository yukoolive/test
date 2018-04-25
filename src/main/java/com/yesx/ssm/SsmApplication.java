package com.yesx.ssm;

import com.yesx.ssm.interceptors.PageContextInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement//如果mybatis中service实现类中加入事务注解，需要此处添加该注解
//@MapperScan("com.yesx.ssm.mapper")//扫描的是mapper.xml中namespace指向值的包位置
public class SsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsmApplication.class, args);
	}

	//mvc控制器
	//@Configuration
	static class WebMvcConfigurer extends WebMvcConfigurerAdapter {
		//增加拦截器
		public void addInterceptors(InterceptorRegistry registry){
			registry.addInterceptor(new PageContextInterceptor())    //指定拦截器类
					.addPathPatterns("/*");        //指定该类拦截的url
		}
	}
}
