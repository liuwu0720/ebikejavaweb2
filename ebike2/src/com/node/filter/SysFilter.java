package com.node.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.node.util.SingleOnline;
import com.node.util.SystemConstants;

/**
 * 系统过滤器
 * 
 * @author wen xiaoqiang
 * 
 */
public class SysFilter implements Filter {

	private Set<String> exclude = new HashSet<String>();

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String path = request.getContextPath();
		String uri = request.getRequestURI();
		if (request.getMethod().equalsIgnoreCase("get")) {
			this.encoding(request);
		}
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		session.setAttribute("contextPath", path);
		if (allow(path, uri, exclude)) {
			chain.doFilter(request, response);
			return;
		} else if (session.getAttribute(SystemConstants.SESSION_USER) != null) {
			String sessionUserName = (String) request.getSession().getAttribute(SystemConstants.SESSION_USER_NAME);
			boolean flag = SingleOnline.isValidUser(sessionUserName, request.getSession().getId());
			if(flag){
				chain.doFilter(request, response);
				return;
			}
		
		}
		PrintWriter out = response.getWriter();
		out.println("<script>window.parent.location.replace('"
				+ request.getContextPath() + "/userAction/index')</script>");
		out.flush();
		out.close();
		
	}

	/**
	 * GET提交方式中文编码过滤
	 * 
	 * @param request
	 */
	private void encoding(HttpServletRequest request) {
		Iterator<?> iter = request.getParameterMap().values().iterator();
		while (iter.hasNext()) {
			String[] parames = (String[]) iter.next();
			for (int i = 0; i < parames.length; i++) {
				try {
					parames[i] = new String(parames[i].getBytes("ISO-8859-1"),
							"UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * URL地址过滤
	 * 
	 * @param path
	 * @param uri
	 * @param urls
	 * @return boolean
	 */
	private boolean allow(String path, String uri, Set<String> urls) {
		for (String url : urls) {
			if (url.startsWith("*")) {
				if (uri.endsWith(url.substring(1))) {
					return true;
				}
			} else if (url.endsWith("*")) {
				if (uri.startsWith(path + url.substring(0, url.length() - 1))) {
					return true;
				}
			} else {
				if (uri.equals(path + url)) {
					return true;
				}
			}
		}
		return false;
	}

	public void init(FilterConfig config) throws ServletException {
		String values = config.getInitParameter("exclude");
		if (!"".equals(values) && values != null) {
			String[] params = values.split("\\|");
			for (String param : params) {
				exclude.add(param);
			}
		}
	}

}
