package com.gdbc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {

	@RequestMapping("login")
	public String login(Model model,HttpServletRequest request) throws Exception
	{
		String exceptionClassName  = (String) request.getAttribute("shiroLoginFailure");
		String error = "";
		if(exceptionClassName!=null){
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				//throw new CustomException("账号不存在");
				error="账号不存在";
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				//throw new CustomException("用户名/密码错误");
				error="用户名/密码错误";
			}  else if("randomCodeError".equals(exceptionClassName)){
				//throw new CustomException("验证码出错");
				error="验证码出错";
			}else{
				//throw new Exception();//最终在异常处理器生成未知错误
				error="未知错误";
			}
		}
		model.addAttribute("error", error);
		return "login";
	}
}
