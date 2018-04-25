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
//		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
//		if (basePath.endsWith("/")) {
//			basePath = basePath.substring(0, basePath.length()-1);
//		}
		request.setAttribute("ctx", path+"/test");
		System.out.println("---------------------------ctx:"+request.getAttribute("ctx"));
		//request.setAttribute("themePath", path+"/static/css/skin/southnet");
		
//		String theme = getTheme(request);
//		// 设置主题名称
//		request.setAttribute("theme", theme);
//		// 设置主题路径
//		request.setAttribute("themePath", path + "/theme/2015/" + theme);
//		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//		if(shiroUser!=null&&StringUtils.isNotEmpty(shiroUser.getHomePage())){//为电网做了一个登录页面
//			request.setAttribute("themePath", path + "/theme/" + theme);
//		}
	}
	
	/**
	 * 获取主题名称
	 */
//	private String getTheme(HttpServletRequest request) {
//		String theme = "default";
//		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//		if (shiroUser != null) {
//			theme = shiroUser.getTheme();
//			//如果是超级管理员，则默认主题为default
//			if(shiroUser.getType()==0 || theme == null)
//				theme="default";
//		}
//		// 测试用
//		if (request.getParameter("theme") != null) {
//			theme = request.getParameter("theme");
//		}
//		return theme;
//	}

}
