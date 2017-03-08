package cs636.music.presentation.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import cs636.music.domain.*;
import cs636.music.service.*;
import cs636.music.dao.*;
import cs636.music.service.data.*;

public class RegisterController extends HttpServlet  implements Controller{


	private UserServiceAPI userService;	
	private String view;

	public RegisterController(UserServiceAPI userService, String view) {
		this.view = view;
		this.userService = userService;
	}
	
	
	public String handleRequest (
			HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		HttpSession session = request.getSession();
		String requestURL = request.getServletPath();
		String validate = null;
		String url = "/register.html";
		
		try {
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			String nextUrl=request.getParameter("nextUrl");
			String productCode=request.getParameter("productCode");
			String trackNum=request.getParameter("trackNum");
			
			request.setAttribute("productCode",productCode);
			request.setAttribute("trackNum",trackNum);
			//User user = null;
			UserData user = null;
			if( firstname!=null && lastname!=null && email!=null ){
				user = userService.getUserInfo(email);
				if(user==null){
				userService.registerUser(firstname, lastname, email);
				user = userService.getUserInfo(email);
				}else{
					 validate="user with this email exist!!!";
				}
			}
			if (user != null) {
				session.setAttribute("user", user);
				url = "/catalog.html";
			} else {
				url = view;
			}
			request.setAttribute("error", validate);
			
		} catch (ServiceException e) {
			System.out.println("RegisterController: " + e);
			throw new ServletException(e);
		}

		return url;
	}
	
	
}