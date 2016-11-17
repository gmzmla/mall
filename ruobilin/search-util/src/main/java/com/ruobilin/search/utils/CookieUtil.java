package com.ruobilin.search.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	 /**
     * Cookie 追加
     * @return
     * @throws Exception
     */
    public static void addCookie(String name,String value, int timeLong,HttpServletResponse response){
		try {
			Cookie cookie = new Cookie(name, URLEncoder.encode(value,"utf-8"));
			cookie.setMaxAge(timeLong);	//有效 期限
	    	cookie.setPath("/");//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
	        response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    /**
     * Cookie 取得
     * @return
     * @throws Exception
     */
    public static String getCookie(String name,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals(name))
                {
                    try {
						return URLDecoder.decode(cookie.getValue(),"utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
                }
            }
        }
        return null;
    }
    
    /**
     * 清空Cookie
     * @param response
     */
    public static void delCookie(String key,HttpServletResponse response){
    	Cookie cookie = new Cookie(key, null);
    	cookie.setMaxAge(0);
    	cookie.setPath("/");
    	response.addCookie(cookie);
    }
}
