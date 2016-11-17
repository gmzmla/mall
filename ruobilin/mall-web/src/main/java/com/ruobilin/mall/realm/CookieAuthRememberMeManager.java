package com.ruobilin.mall.realm;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieAuthRememberMeManager extends CookieRememberMeManager {
    public CookieAuthRememberMeManager(String domain, int timeout, String key) {
    	super();

        if (domain != null && !domain.equals("")) {
        	getCookie().setDomain(domain);
        }
        if (key != null && !key.equals("")) {
        	setCipherKey(Base64.decode(key));
        }
    }
}
