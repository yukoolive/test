package com.yesx.ssm.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置页面变量和切换主题的拦截器
 */
public class PageContextInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
		addPageContext(request);
		return true;
	}
	
	/**
	 * 设置页面变量
	 */
	private void addPageContext(HttpServletRequest request) {
		// 设置上下文路径
		String path = request.getContextPath();
		request.setAttribute("ctx", path);
		//System.out.println("---------------------------ctx:"+request.getAttribute("ctx"));

	}

}
