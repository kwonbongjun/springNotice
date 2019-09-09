package com.java.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
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

import net.sf.json.JSONObject;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	NoticeService ns;
	
	private static String at="";
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
			System.out.println("list"+list);
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
		//System.out.println(loginList.size());
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
	
	@RequestMapping("/kakao")
	public void kakao(HttpServletRequest request, HttpServletResponse response) {
				try {
					//http://gdj16.gudi.kr:20003/KakaoBack 
					String url="https://kauth.kakao.com/oauth/authorize?client_id=ed94698d2dd2bbca37dbb1ad2cd5ae87"
							+ "&redirect_uri="+URLEncoder.encode("http://localhost:8080/KakaoBack","UTF-8")
							+ "&response_type=code";
					System.out.println(request.getParameter("code"));
					response.sendRedirect(url);
//					String url = "https://kauth.kakao.com/oauth/authorize";
//					url +="?client_id=ed94698d2dd2bbca37dbb1ad2cd5ae87&redirect_uri="; //rest api
//					url +=URLEncoder.encode("http://gdj16.gudi.kr:20003/KakaoBack","UTF-8"); //uri
//					url +="&response_type=code"; //token 
//					System.out.println(url);
//					res.sendRedirect(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	@RequestMapping("/KakaoBack")
	public String kakaoback(HttpServletRequest request, HttpServletResponse response){
	//http://gdj16.gudi.kr:20003/KakaoBack
		try {
			String url="https://kauth.kakao.com/oauth/token"
					 +"?grant_type=authorization_code"
					 +"&client_id=ed94698d2dd2bbca37dbb1ad2cd5ae87"
					 +"&redirect_uri="+URLEncoder.encode("http://localhost:8080/KakaoBack","UTF-8")
					 +"&code="+request.getParameter("code");
			URL uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("POST");
			
			InputStream input = conn.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(inputReader);
			
			String line="";
			String result="";
			while((line=br.readLine())!=null) {
				result+=line;
			}
			
			JSONObject jtoken = JSONObject.fromObject(result);
			String accesstoken=(String) jtoken.get("access_token");
			at=accesstoken;
			System.out.println(result);
			
			url="https://kapi.kakao.com/v2/user/me" +  
					"?access_token=" + accesstoken;
//					"?Content-type= application/x-www-form-urlencoded;charset=utf-8";
			uri = new URL(url);
			conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("POST");
			
			input = conn.getInputStream();
			inputReader = new InputStreamReader(input);
			br = new BufferedReader(inputReader);
			
			line="";
			result="";
			while((line=br.readLine())!=null) {
				result+=line;
			}
			
			jtoken = JSONObject.fromObject(result);
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HttpSession session=request.getSession();
		session.setAttribute("login", true);
		return "redirect:/";
	}
	
	@RequestMapping("/kakaologout")
	public String kakaoLogout(HttpServletRequest request, HttpServletResponse response){
		if(!"".equals(at)) {
			String url="https://kapi.kakao.com/v1/user/logout" +  
					"?access_token=" + at;
			URL uri;
			try {
				uri = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
				conn.setRequestMethod("POST");
				
				InputStream input = conn.getInputStream();
				InputStreamReader inputReader = new InputStreamReader(input);
				BufferedReader br = new BufferedReader(inputReader);
				
				String line="";
				String result="";
				while((line=br.readLine())!=null) {
					result+=line;
				}
				
				JSONObject jtoken = JSONObject.fromObject(result);
				String id=(String) jtoken.get("id");
				System.out.println(id);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		return "redirect:/login";
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
