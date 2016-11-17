package com.ruobilin.mall.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.service.UserService;

public class UserAuthorizingRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	
	@Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {  
        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();  
        if(loginName != null) {
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();  
            info.addRole("user");
            //info.addStringPermission("add");
            return info;
        }
        return null;
    }  
  
    /** 
     * 登录认证; 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(  
            AuthenticationToken authenticationToken) throws AuthenticationException {  
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        String pwd = new String(token.getPassword());
        User u = userService.login(token.getUsername(), pwd);
        if (u != null && u.getPassword().equals(pwd)) {
        	return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), String.valueOf(u.getId()));
        }  
        return null;  
    }  
}
