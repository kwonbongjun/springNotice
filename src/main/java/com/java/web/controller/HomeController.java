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
		String gender = req.getParameter("gender");
		String temp=req.getParameter("age");
		System.out.println(gender);
		
		//int age = Integer.parseInt(req.getParameter("age"));
		int age= 2019-Integer.parseInt(temp.substring(0,4))+1;
		Login checklogin = nsi.checkLogin(id);
		if(checklogin!=null ) {
			req.setAttribute("dup", true);
			return "join";
		}
		Login checkNickname = nsi.checkNickname(nickname);
		if(checkNickname!=null ) {
			req.setAttribute("nickname", true);
			return "join";
		}
		if(!gender.equals("m") && !gender.equals("f")) {
			req.setAttribute("gen", true);
			return "join";
		}
		Login login = new Login(id,pw,nickname,gender,age);
		nsi.insertLogin(login);
		return "home";
	}
	@RequestMapping("/submitlogin")
	public String submitlogin(HttpServletRequest req, HttpServletResponse res){
		String id=req.getParameter("no");
		String pw=req.getParameter("val");
		Login login = nsi.loginRead(new Login(id,pw,""));
		HttpSession s=req.getSession();
		if(login!=null) {
			s.setAttribute("login", new Login(id,login.getNickname()));
		}else {
			req.setAttribute("login", "false");
			s.invalidate();
			return "login";
		}
		return "home";
	}
	@RequestMapping("/submitlogout")
	public String submitlogout(HttpServletRequest req, HttpServletResponse res){
		HttpSession s=req.getSession();
		s.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value="/mypage")
	public String mypage(HttpServletRequest req, HttpServletResponse res) {
		//정보보기, 회원탈퇴, 
		return "mypage";
	}
}
