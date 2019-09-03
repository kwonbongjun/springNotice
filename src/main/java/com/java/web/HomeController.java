package com.java.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		try {
//			String driver="org.mariadb.jdbc.Driver";
//			String url="jdbc:mariadb://192.168.3.31:3306/DBTEST";
			//String url="jdbc:mariadb://172.30.1.11:3306/DBTEST";
//			String userName="root";
//			String password="1234";
			Connection con=DriverManager.getConnection(url,userName,password);
			Statement st = con.createStatement();

			
			
			String sql="select no,val from content";
			
			//List<Bean> list=(List<Bean>) st.executeQuery(sql);
			ResultSet rs=st.executeQuery(sql);
			List<Bean> list=new ArrayList<Bean>();
			while(rs.next()) {
				list.add(new Bean(rs.getInt("no"),rs.getString("val")));
			}
			
			//System.out.println(st.executeQuery(sql).getObject(1));
			request.setAttribute("list", list);
			con.close();
			st.close();
			
			//response.sendRedirect("/servletNotice/test.jsp");
			
			RequestDispatcher rd=request.getRequestDispatcher("/test.jsp");
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return "home";
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
