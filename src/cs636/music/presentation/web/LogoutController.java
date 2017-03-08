package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import cs636.music.service.UserServiceAPI;
//import cs636.music.service.AdminServiceAPI;

public class LogoutController extends HttpServlet implements Controller {

	//private UserServiceAPI userService;	
	private String view;

	public LogoutController(/*UserServiceAPI userService,*/ String view) {
		this.view = view;
		/*this.userService = userService;*/
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String url = "/WEB-INF/jsp/logout.jsp";
		
		request.getSession().invalidate();
		return url;
	}

}