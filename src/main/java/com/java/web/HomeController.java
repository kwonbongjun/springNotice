package com.java.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	NoticeService ns;
//	@RequestMapping("/create")
//	public String create(HttpServletRequest request, HttpServletResponse response) {
//		String no=request.getParameter("no");
//		String val=request.getParameter("val");
//		System.out.println(no+","+val);
//		try {
//			String driver="org.mariadb.jdbc.Driver";
//			Class.forName(driver);
//			String url="jdbc:mariadb://192.168.3.31:3306/DBTEST";
//			//String url="jdbc:mariadb://172.30.1.11:3306/DBTEST";
//			String userName="root";
//			String password="1234";
//			Connection con=DriverManager.getConnection(url,userName,password);
//			Statement st = con.createStatement();
//			String sql="insert into content(val) values(\""+val+"\")";
//			st.execute(sql);
//			con.close();
//			st.close();
////			RequestDispatcher rd=request.getRequestDispatcher("/test.jsp");
////			rd.forward(request, response);
//			response.sendRedirect("/servletNotice/test");
//		}catch(Exception e) {
//			e.printStackTrace();
//			
//		}
//			return "home";
//	}
	
	@RequestMapping("/")
	public String read(HttpServletRequest request, HttpServletResponse response) {
		if(request.getParameter("boardNum")!=null) {
			return "detail";
		}
		try {
			List<Bean> list=ns.contentRead();
			request.setAttribute("list", list);
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return "home";
	}
	@RequestMapping("/login")
	public String Login(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("no");
		String pw=request.getParameter("val");
		Login login=new Login(id, pw);
		System.out.println(login.getId()+login.getPw());
		
		List<Login> loginList = ns.loginRead(login);
		HttpSession s=request.getSession();
//		s.setAttribute("login", false);
		System.out.println(loginList.size());
		if(loginList.size()>0) {
			s.setAttribute("login", true);
			request.setAttribute("user", login);
		}else {
			s.invalidate();
		}
		return "redirect:/";
	}
	
	@RequestMapping("/create")
	public String create(HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		String writer=request.getParameter("writer");
		
		System.out.println(no+","+val+","+writer);
		Bean bean=new Bean(no, "title",val,writer);
		ns.createContent(val);
		return "redirect:/";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		Bean bean=new Bean(no, "title",val,"writer");
		System.out.println(no+","+val);
		ns.updateContent(bean);
		return "redirect:/";
	}
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		Bean bean=new Bean(no, "title",val,"writer");
		System.out.println(no+","+val);
		ns.deleteContent(bean);
		return "redirect:/";
	}
	
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
}
