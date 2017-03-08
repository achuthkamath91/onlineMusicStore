package cs636.music.presentation.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.*;
import cs636.music.service.*;
import cs636.music.service.data.*;

public class CatalogController extends HttpServlet implements Controller {
    
	private UserServiceAPI userService;
	private String view;
	
	public CatalogController(UserServiceAPI userService, String view) {
		this.view = view;
		this.userService = userService;
	}
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		try{
			HttpSession session = request.getSession();
			
			// if list of products doesn't exist, initialize it,
			// and store it for the remainder of the session
			if(session.getAttribute("products") == null){
				Set<Product> products = userService.getProductList();
				session.setAttribute("products",  products);
			}
		}catch(ServiceException e){
			System.out.println("CatalogController: " + e);
			throw new ServletException(e);
		}		
		
		return view;
	}
	
    @Override
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String url;
        if (requestURI.endsWith("/listen")) {
            url = listen(request, response);
        } else {
            url = showProduct(request, response);
        }
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String url = "/catalog.html";
        if (requestURI.endsWith("/register.html")) {
            url = registerUser(request, response);
        }
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    private String showProduct(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {
		String productCode = request.getPathInfo();
    	try{
        // This should never be null. But just to be safe.
	        if (productCode != null) {
	            productCode = productCode.substring(1);
	            Product product = userService.getProduct(productCode);
	            HttpSession session = request.getSession();
	            session.setAttribute("product", product);
	        }    		
    	}catch(ServiceException e){
			System.out.println("CatalogController: " + e);
			throw new ServletException(e);    		
    	}
                
        return "/catalog/" + productCode + "/index.jsp";
    }
    
    private String listen(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException{
        
        HttpSession session = request.getSession();        
        UserData user = (UserData) session.getAttribute("user");
        String returnURL = "";
        try{
        	 // if the User object doesn't exist, check for the email cookie
            if (user == null) {
                Cookie[] cookies = request.getCookies();
                String emailAddress =
                        CookieUtil.getCookieValue(cookies, "emailCookie");
                // if the email cookie doesn't exist, go to the registration page
                if (emailAddress == null || emailAddress.equals("")) {
                    return "/register.jsp";
                } else {
                    user = userService.getUserInfo(emailAddress);
                    // if a user for that email isn't in the database, go to the registration page
                    if (user == null) {
                        return "/catalog/register.jsp";
                    }
                    session.setAttribute("user", user);
                }
            }
            // eoneil: why not get code from URL here?
            // This can return null if session timed out
            Product product = (Product) session.getAttribute("product");

          /*   Download download = new Download();
            download.setUser(user);
            download.setTrack(product.getTracks().iterator().next());        
            userService.addDownload(user.getId(),product.getTracks());*/
            
            returnURL = "/catalog/" + product.getCode() + "/sound.jsp";
        	
        }catch(ServiceException e){
			System.out.println("CatlogController: " + e);
			throw new ServletException(e);        	
        }
      
        return returnURL;
    }  
    
    private String registerUser(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException{

        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String url = "";
        try{
        	User user = new User();
            UserData userData = new UserData();
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setEmailAddress(email);
            if (userService.getUserInfo(email) != null) {
                //userDAO.update(user);
            } else {
                userService.registerUser(firstName,lastName,email);
            }
            if(firstName!=null && lastName!=null && email!=null){
            	userData = userService.getUserInfo(email);
    			if(userData==null){
    			userService.registerUser(firstName, lastName, email);
    			userData = userService.getUserInfo(email);
    			}
    			 if(userData==null){
    				// error="No registered user with this email";
    			 }
    		}

            session.setAttribute("user", user);

            Cookie emailCookie = new Cookie("emailCookie", email);
            emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
            emailCookie.setPath("/");
            response.addCookie(emailCookie);
            
            Product product = (Product) session.getAttribute("product");
            url = "/catalog/" + product.getCode() + "/sound.jsp";        	
        }catch(ServiceException e){
			System.out.println("CatalogController: " + e);
			throw new ServletException(e);        	
        }
        
        return url;
    }    
}