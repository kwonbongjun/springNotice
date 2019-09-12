package com.java.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	NoticeService ns;
	
	private static String at="";
	private static String nm="";
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
		int no;
		if(request.getParameter("boardNum")!=null) {
			System.out.println(request.getParameter("boardNum"));
			no=Integer.parseInt(request.getParameter("boardNum"));

			Bean detail=ns.detailRead(no);
			List<FileBean> fb=ns.readFile(no);
			System.out.println("no:"+no);
			if(detail!=null)
			request.setAttribute("detail", detail);
			request.setAttribute("file", fb);
			return "detail";
		}
		try {

			int pageNum=1;
			if(request.getParameter("pageNum")!=null){
				pageNum=Integer.parseInt(request.getParameter("pageNum"));
			}
			List<Bean> list=null;
			
			if(request.getParameter("search")!=null) {
				String title=request.getParameter("search");
				int total=ns.contentReadSearchAll(title);
				request.setAttribute("total", total);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("pageNum", pageNum);
				map.put("title", title);
				list=ns.contentReadSearch(map);
//				finalno = ns.readSearchFinalNo(title);
//				if(finalno==0) {
//					finalno=1;
//				}
//				System.out.println("finalno"+finalno);
			}else {
			int total=ns.contentReadAll();
			
			request.setAttribute("total", total);
			System.out.println(total);
			list=ns.contentRead(pageNum);
			}
			int page=0;
			int finalno = ns.readfinalno();
			if(finalno==0) {
				finalno=1;
			}
			System.out.println("listsize"+list.size());
			if(list.size()>0) {
			request.setAttribute("list", list);
			request.setAttribute("finalno", finalno);	
			}
			
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
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		System.out.println("no:"+no);
		String title=request.getParameter("title");
		String val=request.getParameter("val");
		String writer=nm;
		String originalFileName="";
		String fileName="";
		String ext="";
		if(!"".equals(files[0].getOriginalFilename())) {
		for(int i=0;i<files.length;i++) {
			MultipartFile file = files[i];
			originalFileName=file.getOriginalFilename();
			ext=originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
			fileName=UUID.randomUUID().toString();
			try {
				byte[] data=file.getBytes();
				String path="C:\\Resources\\";//D:\\workspace\\resources\\";
				File f = new File(path);
				if(!f.isDirectory()) {
					f.mkdirs();
				}
				OutputStream os = new FileOutputStream(new File(path+fileName+ext));
				os.write(data);
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileBean fb=new FileBean(no,originalFileName,fileName,ext);
			ns.createFile(fb);
		}
		}
		System.out.println(title+","+val+","+writer);
		Bean bean=new Bean(title,val,writer);
		ns.createContent(bean);
		return "redirect:/";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestParam("file") MultipartFile[] files,HttpServletRequest request, HttpServletResponse response) {
		int no=0;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(request.getParameter("no"));
		if(request.getParameter("no")!=null) {
		no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		String title=request.getParameter("title");


//		bean.update(no, title, val);
		System.out.println(no+","+val);
		
		String originalFileName="";
		String fileName="";
		String ext="";
		ns.deleteFile(no);
		if(!"".equals(files[0].getOriginalFilename())) {
		for(int i=0;i<files.length;i++) {
			MultipartFile file = files[i];
			originalFileName=file.getOriginalFilename();
			System.out.println(originalFileName);
			ext=originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
			fileName=UUID.randomUUID().toString();
			try {
				byte[] data=file.getBytes();
				String path="C:\\Resources\\";//"D:\\workspace\\resources\\";
				File f = new File(path);
				if(!f.isDirectory()) {
					f.mkdirs();
				}
				OutputStream os = new FileOutputStream(new File(path+fileName+ext));
				os.write(data);
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileBean fb=new FileBean(no,originalFileName,fileName,ext);
			ns.createFile(fb);;
		}
		
		}
		Bean bean=new Bean(no,title,val);
		ns.updateDetail(bean);
		}
		return "redirect:/?boardNum="+no;
	}
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		//String no="";
		int no;
//		if(request.getParameter("boardNum")!=null) {
//			no=Integer.parseInt(request.getParameter("boardNum"));
//			Bean detail=ns.detailRead(no);
//			if(detail!=null)
//			request.setAttribute("detail", detail);
//			return "detail";
//		}
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		no=Integer.parseInt(request.getParameter("no"));
		ns.deleteDetail(no);
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
//			String temp=(String) jtoken.get("properties");

			JSONObject tmp=JSONObject.fromObject(jtoken.get("properties"));

			nm=(String) tmp.get("nickname");
			System.out.println(nm);
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
		System.out.println("at"+at);
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
//				String id=(String) jtoken.get("id");
				System.out.println(result);
//				System.out.println(id);
				
				HttpSession session=request.getSession();
				session.invalidate();
//				RequestDispatcher rd=request.getRequestDispatcher("https://developers.kakao.com/logout");
//				rd.forward(request, response);
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
		return "redirect:https://developers.kakao.com/logout";
	}
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response){	
		 	int no=Integer.parseInt(request.getParameter("boardnum"));
		 	String filename=request.getParameter("filename");
			Bean detail=ns.detailRead(no);
			FileBean fb=new FileBean(no, filename, "", "");
			FileBean file=ns.readFile(fb);
			String path="C:\\Resources\\";//"D:\\workspace\\resources\\"; 
			String originalFilename=file.getFilename();
			System.out.println(originalFilename);
			String fileName=file.getFileurl();
			String ext=file.getExt();
			try {
				InputStream input = new FileInputStream(path+fileName+ext);
				OutputStream output = response.getOutputStream();
				IOUtils.copy(input, output);
				response.setHeader("content-Disposition", "attachment;filename=\""+(originalFilename)+"\"");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
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
	
	@RequestMapping("/create2")
	public String create2(HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		String writer=request.getParameter("writer");
		
		System.out.println(no+","+val+","+writer);
//		Bean bean=new Bean(no, "title",val,writer);
//		ns.createContent(val);
		return "redirect:/";
	}
	
	@RequestMapping("/update2")
	public String update2(HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		Bean bean=new Bean("title",val,"writer");
		System.out.println(no+","+val);
		ns.updateContent(bean);
		return "redirect:/";
	}
	@RequestMapping("/delete2")
	public String delete2(HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		Bean bean=new Bean("title",val,"writer");
		System.out.println(no+","+val);
		ns.deleteContent(bean);
		return "redirect:/";
	}
	
	
}
