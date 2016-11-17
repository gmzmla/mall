package com.ruobilin.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.controller.model.ControllerModel;
import com.ruobilin.mall.entity.MailingAddress;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.service.MailingAddressService;
 
/**
 * 收货地址Controller
 */
@Controller
@RequestMapping("address")
public class MailingAddressController extends ControllerModel {
	
	@Autowired
	private MailingAddressService mailingAddressService;
	
	/**
	 * 查询地址列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryList")
	public String queryList(Model model){
		User user=this.getUserInfo();
		List<MailingAddress> listAddress=mailingAddressService.queryMailingAddress(user.getId().toString());
		model.addAttribute("listAddress", listAddress);
		return "address/list";
	}
	
	/**
	 * 保存用户配送地址
	 * @param mailingAddress
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveMailingAddress")
	public String saveMailingAddress(MailingAddress mailingAddress,String saveType,Model model){
		User user=this.getUserInfo();
		mailingAddress.setUserId(user.getId().toString());
		mailingAddress.setMark("0");
		if(mailingAddress.getId()==null||"".equals(mailingAddress.getId())||"null".equals(mailingAddress.getId())){
			mailingAddressService.saveMailingAddress(mailingAddress);
		}else{
			mailingAddressService.updateMailingAddress(mailingAddress);
		}
		
		if("0".equals(saveType)){	//订单中的保存
			return "redirect:/o/settle";
		}
		return "redirect:/address/queryList";	//个人中心中的保存
	}
	
	/**
	 * 删除收货地址
	 * @param id
	 * @param model
	 */
	@RequestMapping("/delMailingAddress")
	@ResponseBody
	public void delMailingAddress(String id,Model model){
		mailingAddressService.deleteMailingAddress(id);
	}
	
	/**
	 * 设置默认地址
	 * @param id
	 */
	@RequestMapping("/updateMark")
	@ResponseBody
	public void updateMark(MailingAddress mailingAddress){
		mailingAddress.setUserId(this.getUserInfo().getId().toString());
		mailingAddressService.updateMark(mailingAddress);
	}
	
	
}
