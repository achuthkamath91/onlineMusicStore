package cs636.music.presentation.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import cs636.music.domain.*;
import cs636.music.service.*;
import cs636.music.dao.*;

public class UserController extends HttpServlet  implements Controller{


	private UserServiceAPI userService;	
	private String view;

	public UserController(UserServiceAPI userService, String view) {
		this.view = view;
		this.userService = userService;
	}
	
	
	public String handleRequest (
			HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		//System.out.println("in UserController");
		HttpSession session = request.getSession();
		Integer cartid = null;
		// take id parameter over session var in UsertBean--
		String paramCartString = (String)request.getParameter("cartid");
		if (paramCartString != null) {
			try {				
			cartid = Integer.parseInt(paramCartString);
			System.out.println("Got cart ID from param = "+ cartid);
			} catch (NumberFormatException e) {
			// if get here, it's a bug: user can't directly enter user id.
			System.out.println("music4: userWelcome: bad number format in cartid");
			throw new ServletException("Bad cartid param in UserController");
			}
		} 		
		return view;
	}
	
	
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
		String requestURL = request.getServletPath();
        String url = "";
        if (requestURI.endsWith("/deleteCookies")) {
            url = deleteCookies(request, response);
        } else if (requestURI.endsWith("/logout")) {
            url = logout(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/subscribeToEmail")) {
            url = subscribeToEmail(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String deleteCookies(HttpServletRequest request,
            HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);  //delete the cookie
            cookie.setPath("/");  //entire application
            response.addCookie(cookie);
        }
        return "/delete_cookies.jsp";
    }
    
    // added by eoneil
    private String logout(HttpServletRequest request,
            HttpServletResponse response) {
        request.getSession().invalidate();  // drop session
        return "/logout.jsp";
    }


    private String subscribeToEmail(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User user = new User();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setEmailAddress(email);

        request.setAttribute("user", user);

        String url;
        String message;
        //check that email address doesn't already exist
        try{
        	 if (userService.getUserInfo(email) != null ) {
                 message = "This email address already exists. <br>"
                         + "Please enter another email address.";
                 request.setAttribute("message", message);
                 url = "/index.jsp";
             } else {
             	userService.registerUser(firstName,lastName,email);
                 message = "";
                 request.setAttribute("message", message);
                 url = "/thanks.jsp";
             }
        }catch(ServiceException e){
			System.out.println("UserController: " + e);
			throw new ServletException(e); 
        	}
       
        return url;
    }
}