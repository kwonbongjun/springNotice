package com.java.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String home(){
		return "home";
	}
	@RequestMapping("/loginpage")
	public String loginpage(){
		return "login";
	}
	@RequestMapping("/join")
	public String join(){
		return "join";
	}
}
