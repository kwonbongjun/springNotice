package com.java.web.controller;

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

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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

import com.java.web.bean.Bean;
import com.java.web.bean.FileBean;
import com.java.web.bean.Login;
import com.java.web.service.NoticeService;
import com.java.web.service.NoticeServiceInterface;

import net.sf.json.JSONObject;

@Controller
public class BoardController { //인터페이스
	@Autowired
	NoticeService ns;
	@Autowired
	NoticeServiceInterface nsi;
	
	private static String at="";
	private static String nm="";

	//루트주소 진입
	@RequestMapping("/board")
	public String read(HttpServletRequest request, HttpServletResponse response) {
		int no;
		//게시글 입력 or 게시판글클릭
		if(request.getParameter("boardNum")!=null) {
			no=Integer.parseInt(request.getParameter("boardNum"));
			Bean detail=ns.detailRead(no);
			List<FileBean> fb=ns.readFile(no);
			System.out.println("no:"+no);
			//if(detail!=null)
			request.setAttribute("detail", detail);
			request.setAttribute("file", fb);
			request.setAttribute("writer", nm);
			
			for(int i =0;i<fb.size();i++) {
			String ext= fb.get(i).getExt();	
				if(ext.equals(".jpg ") || ext.equals(".jpeg") || ext.equals(".png")) {
					System.out.println("ext"+ext);
					request.setAttribute("img", "true");
					break;
				}
			}
			return "detail";
		}
		try {
			int pageNum=1;
			//페이지 받아오기
			if(request.getParameter("pageNum")!=null){
				pageNum=Integer.parseInt(request.getParameter("pageNum"));
			}
			List<Bean> list=null;
			int total;
			//검색여부 확인
			if(request.getParameter("search")!=null) {
				String title=request.getParameter("search");
				String sep = request.getParameter("searchseperate");
				System.out.println(sep);
				if(sep!=null) {
					if("".equals(sep) || "title".equals(sep)) {
						
					}else if("val".equals(sep)) {
						
					}
				}
				total=ns.contentReadSearchAll(title);
				list=ns.contentReadSearch(pageNum, title);
			}else {
				total=ns.contentReadAll();
				list=ns.contentRead(pageNum);
			}
			
			//게시글 마지막 no 확인
			int finalno = 0;
			if(finalno==0) {
				finalno=1;
			}
			System.out.println("listsize"+list.size());
			
			if(list.size()>0) {
			request.setAttribute("list", list);
			finalno = ns.readfinalno();
			}
			request.setAttribute("total", total);
			request.setAttribute("finalno", finalno);	
			request.setAttribute("user", nm);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "board";
	}
	
	@RequestMapping("/login")
	public String Login(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("no");
		String pw=request.getParameter("val");
		Login login = ns.loginRead(new Login(id,pw,""));
		HttpSession s=request.getSession();
//		s.setAttribute("login", false);
		if(login!=null) {
			s.setAttribute("login", true);
			request.setAttribute("user", login);
			nm=id;
		}else {
			s.invalidate();
		}

		return "redirect:/";
	}
	
	@RequestMapping(value="/board/create", method = RequestMethod.POST)
	public String create(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
		int no=Integer.parseInt(request.getParameter("no"));
		System.out.println("no:"+no);
		String title=request.getParameter("title");
		String val=request.getParameter("val");
		String writer=request.getParameter("writer");
		
		ns.uploadFile(files, no);
		Bean bean=new Bean(no,title,val,writer);
		ns.createContent(bean);
		return "redirect:/board";
	}

	@RequestMapping(value = "/board/update", method = RequestMethod.POST)
	public String update(@RequestParam("file") MultipartFile[] files,HttpServletRequest request, HttpServletResponse response) {
		int no;//=0;
		System.out.println(request.getParameter("no"));
//		if(request.getParameter("no")!=null) {
		no=Integer.parseInt(request.getParameter("no"));
		String val=request.getParameter("val");
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");

		ns.deleteFile(no);
		ns.uploadFile(files, no);
		Bean bean=new Bean(no,title,val,writer);
		ns.updateDetail(bean);
//		}
		return "redirect:/board/?boardNum="+no;
	}
	
	@RequestMapping("/board/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int no;
//		try {
//			request.setCharacterEncoding("utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		no=Integer.parseInt(request.getParameter("no"));
		ns.deleteDetail(no);
		return "redirect:/board";
	}
	
	@RequestMapping("/kakao")
	public void kakao(HttpServletRequest request, HttpServletResponse response) {
				try {
					//http://gdj16.gudi.kr:20003/KakaoBack  http://localhost:8080/KakaoBack
					String url="https://kauth.kakao.com/oauth/authorize?client_id=ed94698d2dd2bbca37dbb1ad2cd5ae87"
							+ "&redirect_uri="+URLEncoder.encode("http://gdj16.gudi.kr:20003/KakaoBack","UTF-8")
							+ "&response_type=code";
					System.out.println(request.getParameter("code"));
					
					String url2 = "https://accounts.kakao.com/login?continue=";
					url2+=URLEncoder.encode(url, "UTF-8");
					response.sendRedirect(url2);
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	@RequestMapping("/KakaoBack")
	public String kakaoback(HttpServletRequest request, HttpServletResponse response){
	//http://gdj16.gudi.kr:20003/KakaoBack http://localhost:8080/KakaoBack
		try {
			String url="https://kauth.kakao.com/oauth/token"
					 +"?grant_type=authorization_code"
					 +"&client_id=ed94698d2dd2bbca37dbb1ad2cd5ae87"
					 +"&redirect_uri="+URLEncoder.encode("http://gdj16.gudi.kr:20003/KakaoBack","UTF-8")
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
			String tmpid =jtoken.get("id").toString();
			JSONObject tmp=JSONObject.fromObject(jtoken.get("properties"));
			tmp.put("id", tmpid);
			nm=(String) tmp.get("nickname");

			System.out.println(nm);
			
			
			HttpSession session=request.getSession();
			Login login=new Login(tmp.get("id").toString(),(String)tmp.get("nickname"));
			session.setAttribute("login", login);
			
			Login login2 = nsi.loginRead(new Login(tmp.get("id").toString(),"1",""));
			if(login2==null) {
				request.setAttribute("klogin", login);
				return "join";
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/";
//		return "redirect:/Kakaoinfo";
	}
	
//	@RequestMapping("/Kakaoinfo")
//	public String kakaoinfo(HttpServletRequest request, HttpServletResponse response){
//		//http://gdj16.gudi.kr:20003/KakaoBack http://localhost:8080/KakaoBack
//		try {
//			String url="https://kapi.kakao.com/v2/user/me?Authorization=Bearer"+at;
//			
//			URL uri = new URL(url);
//			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
//			
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//			conn.setRequestProperty("Authorization", "Bearer "+at);
//			//conn.setRequestProperty("property_keys", "["+"kakao_account.birthday"+"]");
//			System.out.println(conn);
//			InputStream input = conn.getInputStream();
//			InputStreamReader inputReader = new InputStreamReader(input);
//			BufferedReader br = new BufferedReader(inputReader);
//			
//			String line="";
//			String result="";
//			while((line=br.readLine())!=null) {
//				result+=line;
//			}
//			System.out.println(result);
//			
//			JSONObject jtoken = JSONObject.fromObject(result);
//
//			String id=(String) jtoken.get("id");
//
//
//			
//			
//			jtoken = JSONObject.fromObject(result);
//			System.out.println(result);
////			String temp=(String) jtoken.get("properties");
//			String tmpid =jtoken.get("id").toString();
//			JSONObject tmp=JSONObject.fromObject(jtoken.get("properties"));
//			tmp.put("id", tmpid);
//			nm=(String) tmp.get("nickname");
//
//			System.out.println(nm);
//			HttpSession session=request.getSession();
//			Login login=new Login(tmp.get("id").toString(),(String)tmp.get("nickname"));
//			session.setAttribute("login", login);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//
//		return "redirect:/";
//	}
	
	
	@RequestMapping("/kakaologout")
	public String kakaoLogout(HttpServletRequest request, HttpServletResponse response){
		//HttpSession hs
		System.out.println("at"+at);
		if(!"".equals(at)) {
			String url="https://kapi.kakao.com/v1/user/logout" +  
					"?access_token=" + at;
			URL uri;
			try {
				uri = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
				conn.setRequestMethod("POST");
//				conn.setRequestProperty("Authorization", "Bearer " + hs.getAttribute("access_token"));
				
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
		return "redirect:/kakao";
	}
	@RequestMapping("/board/download")
	public void download(HttpServletRequest request, HttpServletResponse response){	
		 	int no=Integer.parseInt(request.getParameter("boardnum"));
		 	String filename=request.getParameter("filename");
			Bean detail=ns.detailRead(no);
			FileBean fb=new FileBean(no, filename, "", "");
			FileBean file=ns.readFile(fb);
			String path="C:\\Resources\\";//"D:\\workspace\\resources\\"; "C:\\Resources\\"
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
	
	 
	 
	
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//	
//	
//	/**
//	 * Simply selects the home view to render by returning its name.
//	 */
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "home";
//	}
	
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
	
}
