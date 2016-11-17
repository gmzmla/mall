package com.ruobilin.mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ruobilin.mall.entity.Contact;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.service.OrderService;
import com.ruobilin.mall.service.UserService;
import com.ruobilin.mall.web.utils.JsonResult;
import com.ruobilin.search.utils.CookieUtil;

@Controller
@RequestMapping("u")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String email, String password, boolean remember, RedirectAttributes redirectAttributes,HttpServletRequest request
			,HttpServletResponse response,Model model) {
		try {
			String pwd = new Sha256Hash(password, email, 1024).toBase64();
            SecurityUtils.getSubject().login(new UsernamePasswordToken(email, pwd, remember));
            
            //查询用户信息存入session
            Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("email", email);
			User userInfo=userService.getUserInfo(params);
			if(userInfo!=null){
				session.setAttribute("userInfo", userInfo);
			}
			//用户登陆成功，保存Cookie中的商品信息
			String str=CookieUtil.getCookie("carProduct", request);
			if(str!=null&&!"".equals(str)&&str.split("&").length>0&&str.split("&")[0].split(":").length>1){
				orderService.saveHandleCart(str, String.valueOf(userInfo.getId()));
			}
			CookieUtil.delCookie("carProduct", response);	//用户已经登陆清空cookie中的商品信息
			
			//跳转到登陆之前的页面
			SavedRequest sr=(SavedRequest) session.getAttribute("shiroSavedRequest");
			if(sr!=null){
				//sr.getRequestUrl() 带参数连接，sr.getRequestURI()不带参数
				String webName=request.getContextPath();
				String url=sr.getRequestUrl();
				if(url.indexOf(webName)>-1){
					url=url.replace(webName, "");
				}
				return "redirect:"+url;
			}
			
            return "redirect:/";
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("message","用户名或密码错误");  
            return "redirect:/u/login";
        }  
	}
	
	@RequestMapping(value = "/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "user/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(User u, Model model) {
		try {
			String pwd = new Sha256Hash(u.getPassword(), u.getEmail(), 1024).toBase64();
			u.setPassword(pwd);
			userService.register(u, false);
			
			if (u.getId() != null) {
				SecurityUtils.getSubject().login(new UsernamePasswordToken(u.getEmail(), pwd));
				return "redirect:/";
			}
		}
		catch (Exception e) {
			model.addAttribute("message", "系统异常");
		}
		model.addAttribute("user", u);
		return "user/register";
	}
	
	/**
	 * 用户基本信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(Model model) {
		String id = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
		User u = userService.getById(Long.valueOf(id));
		
		model.addAttribute("user", u);
		return "user/info";
	}
	
	@RequestMapping(value = "/activate")
	public String activate() {
		return "user/activate";
	}
	
	@RequestMapping(value = "/addContact")
	@ResponseBody
	public JsonResult addContact(Contact c, Model model) {
		String uid = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
		c.setUserId(Long.valueOf(uid));
		c.setStatus(0);

		if (userService.saveContact(c)) {
			return new JsonResult(true, null, c.getId());
		}
		else {
			return new JsonResult(false, "失败。", null);
		}
	}
	
	@RequestMapping(value = "/getContact")
	@ResponseBody
	public Contact getContact(Long id, Model model) {
		String uid = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
		Contact c = userService.getContactById(id);
		if (c == null || !c.getUserId().toString().equals(uid))
			return null;

		return c;
	}
	
	/**
	 * 用户中心中修改个人信息
	 * @param user
	 */
	@RequestMapping(value="/updateUserInfo", method = RequestMethod.POST)
	public String updateUserInfo(User user,Model model){
		String uid = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
		user.setId(Long.valueOf(uid));
		userService.save(user);	//id不为空就是修改
		return "redirect:/u/info";
	}
}
