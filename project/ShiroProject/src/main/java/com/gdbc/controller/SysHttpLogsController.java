package com.gdbc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@RequestMapping("/syshttp") 
public class SysHttpLogsController {

	@RequestMapping("/index")
	public String Index(Model model,HttpServletRequest  httpServletRequest)
	{
		
		return "index";
	}
	
}
