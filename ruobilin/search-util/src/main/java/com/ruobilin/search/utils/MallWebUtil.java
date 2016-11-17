package com.ruobilin.search.utils;

import java.text.SimpleDateFormat;
import java.util.UUID;

/***
 * 得到中文首字母
 */
public class MallWebUtil {

	/**
	 * 生成ID
	 * @return
	 */
	public static String getID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 生成订单编号
	 * 用户ID+yyddmmhhssMMms+两位随机数,用户ID长度不够5位补0
	 * @return
	 */
	public static String getTaskSerialNo(String userId){
		String str = new SimpleDateFormat("yyddmmhhssMMms").format(new java.util.Date());
		if(userId!=null){
			for(int i=10;i>userId.length();i--){
				if(userId.length()<5){
					userId=userId+"0";
				}else{
					break;
				}
			}
		}
		int random=(int)(Math.random()*90+10); //两位随机数
		return userId+str+random;
	}
	
	public static void main(String[] args) {
		System.out.println(getTaskSerialNo("10"));
	}
}