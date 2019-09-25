package com.java.web.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Login;
import com.java.web.service.NoticeService;
import com.java.web.service.NoticeServiceInterface;

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
	@Autowired
	NoticeServiceInterface nsi;
	@RequestMapping("/submitjoin")
	public String submitjoin(HttpServletRequest req, HttpServletResponse res){
		String id=req.getParameter("id");
		String pw=req.getParameter("pw");
		String nickname=req.getParameter("nickname");
		Login checklogin = nsi.checkLogin(id);
		if(checklogin!=null) {
			req.setAttribute("dup", true);
			return "join";
		}
		Login login = new Login(id,pw,nickname);
		nsi.insertLogin(login);
		return "home";
	}
	@RequestMapping("/submitlogin")
	public String submitlogin(HttpServletRequest req, HttpServletResponse res){
		String id=req.getParameter("no");
		String pw=req.getParameter("val");
		Login login = nsi.checkLogin(id);
		HttpSession s=req.getSession();
		if(login!=null) {
			s.setAttribute("login", new Login(id,login.getNickname()));
		}else {
			s.invalidate();
		}
		return "home";
	}
	@RequestMapping("/submitlogout")
	public String submitlogout(HttpServletRequest req, HttpServletResponse res){
		HttpSession s=req.getSession();
		s.invalidate();
		return "redirect:/";
	}
}
