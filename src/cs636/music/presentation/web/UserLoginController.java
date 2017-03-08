package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.User;
import cs636.music.service.data.*;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;

public class UserLoginController extends HttpServlet implements Controller {
	
	private String view;
	private UserServiceAPI userService;
	
	public UserLoginController(UserServiceAPI userService, String view){
		this.view = view;
		this.userService = userService;
	}
	
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String url = "";
		String error=null;
		
		try {
			
			HttpSession session = request.getSession();
			String requestURL = request.getServletPath();
			String email = request.getParameter("email");
			String nextUrl = request.getParameter("nextUrl");
			String proCode=request.getParameter("productCode");
			String trackNum=request.getParameter("trackNum");
			Boolean user=(session.getAttribute("user")!=null);
			if (nextUrl == null) {
				request.setAttribute("nextUrl", requestURL);
			} else {
				request.setAttribute("nextUrl", nextUrl);
			}
			request.setAttribute("productCode", proCode);
			request.setAttribute("trackNum", trackNum);
           UserData userData=null;
			
			if(email!=null){
				userData = userService.getUserInfo(email);
				 if(userData==null){
					 error="No registered user with this email ID";
				 }
			}
			if (userData != null) {
				session.setAttribute("user", userData);
				url = nextUrl;
			} else {
				url = view;				
			}
			request.setAttribute("error", error);

		} catch (ServiceException e) {
			System.out.println("UserLoginController: " + e);
			throw new ServletException(e);
		}

		return url;
	}

}
