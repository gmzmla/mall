package com.ruobilin.mall.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/list")
	public String list(String name, @RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "id")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			Model model) {
		
		PageList<User> users = userService.findAll(name, page, limit, sort, dir);
		model.addAttribute("users", users);
		model.addAttribute("page", page);
		
		return "user/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		if (id != null) {
			User user = userService.getById(id);
			model.addAttribute("user", user);
		}
		return "user/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(User user, Model model) {
		if (user.getId() == null) {
			String pwd = new Sha256Hash(user.getPassword(), user.getEmail(), 1024).toBase64();
			user.setPassword(pwd);
		}
		if (userService.save(user))
			return "redirect:/user/list";
		
		model.addAttribute("user", user);
		model.addAttribute("message", "失败。");
		return "user/edit";
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Long id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String email, String password, boolean remember, RedirectAttributes redirectAttributes) {
		try {
			String pwd = new Sha256Hash(password, email, 1024).toBase64();
            SecurityUtils.getSubject().login(new UsernamePasswordToken(email, pwd, remember));
            return "redirect:/";
        } catch (AuthenticationException e) {
        	e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/user/login";
        }
	}
	
	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		SecurityUtils.getSubject().logout();
		return "redirect:/user/login";
	}
}
