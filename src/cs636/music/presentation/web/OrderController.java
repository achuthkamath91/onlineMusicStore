package cs636.music.presentation.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.*;
import cs636.music.service.*;
import cs636.music.service.data.*;

public class OrderController extends HttpServlet {
    private static final String defaultURL = "/cart.jsp";
    private AdminServiceAPI adminService;
	private UserServiceAPI userService;
    
    @Override
    public void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/addItem")) {
            url = addItem(request, response);
        } else if (requestURI.endsWith("/updateItem")) {
            url = updateItem(request, response);
        } else if (requestURI.endsWith("/removeItem")) {
            url = removeItem(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        } else if (requestURI.endsWith("/processUser")) {
            url = processUser(request, response);
        } else if (requestURI.endsWith("/displayInvoice")) {
            url = displayInvoice(request, response);
        } else if (requestURI.endsWith("/displayUser")) {
            url = "/user.jsp";
        } else if (requestURI.endsWith("/completeOrder")) {
            url = completeOrder(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = defaultURL;
        if (requestURI.endsWith("/showCart")) {
            showCart(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    private String showCart(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null ) {
            request.setAttribute("emptyCart", "Your cart is empty");
        } else {
            request.getSession().setAttribute("cart", cart);
        }
        return defaultURL;
    }
    
    private String addItem(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        try{
        	if (cart == null)
                cart = new Cart();
            String productCode = request.getParameter("productCode");
            Product product = userService.getProduct(productCode);
            if (product != null) {
                LineItem lineItem = new LineItem();
                lineItem.setProduct(product);
                cart.addItem(lineItem);
            }
            session.setAttribute("cart", cart);
        }catch(ServiceException e){
			System.out.println("OrderController: " + e);
			throw new ServletException(e); 
        	}
        
        return defaultURL;
    }
    
    private String updateItem(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String quantityString = request.getParameter("quantity");
        String productCode = request.getParameter("productCode");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
            if (quantity < 0)
                quantity = 1;
        } catch (NumberFormatException ex) {
            quantity = 1;
        }
        try{
        	Product product = userService.getProduct(productCode);
            if (product != null && cart != null) {
                LineItem lineItem = new LineItem();
                lineItem.setProduct(product);
                lineItem.setQuantity(quantity);
                if (quantity > 0)
                    cart.addItem(lineItem);
                else
                    cart.removeItem(product);
            }
        }catch(ServiceException e){
			System.out.println("OrderController: " + e);
			throw new ServletException(e); 
        	}
        
        return defaultURL;
    }
    
    private String removeItem(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String productCode = request.getParameter("productCode");
        try{
            Product product = userService.getProduct(productCode);
        	if (product != null && cart != null) {
                LineItem lineItem = new LineItem();
                lineItem.setProduct(product);
                cart.removeItem(product);
            }
        }catch(ServiceException e){
			System.out.println("OrderController: " + e);
			throw new ServletException(e); 
        	}
        
        
        return defaultURL;
    }
    
    private String checkUser(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        UserData userData = new UserData();
        // if the User object exists with address1, skip User page
        String url = "/user.jsp";
        try{
        	if (user != null ) {
	            url = "/order/displayInvoice";
	        } else {  // otherwise, check the email cookie
	            Cookie[] cookies = request.getCookies();
	            String email
	                    = CookieUtil.getCookieValue(cookies, "emailCookie");
	            if (email.equals("")) {
	                user = new User();
	                url = "/user.jsp";
	            } else {
	            	userData = userService.getUserInfo(email);
	                if (userData != null) {
	                    url = "/order/displayInvoice";
	                }
	            }
	        }
	        session.setAttribute("user", user);
        	
        }catch(ServiceException e){
			System.out.println("OrderController: " + e);
			throw new ServletException(e); 
        	}
        
        
        return url;
    }

    private String processUser(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException  {
        
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
       // String companyName = request.getParameter("companyName");
        String email = request.getParameter("email");
        //String address1 = request.getParameter("address1");
       // String address2 = request.getParameter("address2");
       // String city = request.getParameter("city");
       // String state = request.getParameter("state");
       // String zip = request.getParameter("zip");
       // String country = request.getParameter("country");

        HttpSession session = request.getSession();
        
        try{
        	User user = (User) session.getAttribute("user");
            if (user == null) {
                user = new User();
            }

            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setEmailAddress(email);
            //user.setCompanyName(companyName);
            //user.setAddress1(address1);
            //user.setAddress2(address2);
            //user.setCity(city);
            //user.setState(state);
            //user.setZip(zip);
            //user.setCountry(country);
            
            if (userService.getUserInfo(email) != null) {
               // userService.update(user);
            } else {
                userService.registerUser(firstName,lastName,email);
            }        

            session.setAttribute("user", user);

            return "/order/displayInvoice";
        	
        }catch(ServiceException e){
			System.out.println("OrderController: " + e);
			throw new ServletException(e); 
        	}
        
        
    }

    private String displayInvoice(HttpServletRequest request,
            HttpServletResponse response) throws IOException  {
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");       
        Cart cart = (Cart) session.getAttribute("cart");
        

        	java.util.Date today = new java.util.Date();

            Invoice invoice = new Invoice();
            invoice.setUser(user);
            invoice.setInvoiceDate(today);
            invoice.setLineItems(cart.getItems());
            
            session.setAttribute("invoice", invoice);
        
        
        return "/invoice.jsp";
    }
    
    private String completeOrder(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Invoice invoice = (Invoice)session.getAttribute("invoice");
        Cart cart = (Cart)session.getAttribute("cart");
        
        try{
        	/*  String creditCardType = 
            request.getParameter("creditCardType");
    		String creditCardNumber = 
            request.getParameter("creditCardNumber");
    		String creditCardExpMonth = 
            request.getParameter("creditCardExpirationMonth");
    		String creditCardExpYear = 
            request.getParameter("creditCardExpirationYear");

		    user.setCreditCardType(creditCardType);
		    user.setCreditCardNumber(creditCardNumber);
		    user.setCreditCardExpirationDate(creditCardExpMonth
		            + "/" + creditCardExpYear);*/
		    
		    // if a record for the User object exists, update it
		    // This is not enough to allow the persistence of Invoice
		    // to succeed, since the user object is still detached
		    // after this--need to do someing in the insert(invoice)
		    // or use cascading persistence
		    // 
		    if (userService.getUserInfo(user.getEmailAddress()) != null ) {
		       // userDB.update(user);
		    } else { // otherwise, write a new record for the user            
		    	 userService.registerUser(user.getFirstname(),user.getLastname(),user.getEmailAddress());
		    }
		    
		    // write a new invoice record
		    userService.checkout(cart,user.getId());
		    
		    // set the emailCookie in the user's browser.
		    Cookie emailCookie = new Cookie("emailCookie",
		            user.getEmailAddress());
		    emailCookie.setMaxAge(60*24*365*2*60);
		    emailCookie.setPath("/");
		    response.addCookie(emailCookie);
		
		    // remove all items from the user's cart
		    session.setAttribute("cart", null);
		           
		  /*  // send an email to the user to confirm the order.
		    String to = user.getEmailAddress();
		    String from = "test@cs.umb.edu";
		    String subject = "Order Confirmation";
		    String body = "Dear " + user.getFirstname() + ",\n\n" +
		        "Thanks for ordering from us. " +
		        "You should receive your order in 3-5 business days. " + 
		        "Please contact us if you have any questions.\n" +
		        "Have a great day and thanks again!\n\n" +
		        "Joe King\n" +
		        "Fresh Corn Records";
		    boolean isBodyHTML = false;
		    try {
		        MailUtil.sendMail(to, from, subject, body, isBodyHTML);
		    }
		    catch(MessagingException e) {
		        this.log(
		            "Unable to send email. \n" +
		            "You may need to configure your system as " +
		            "described in chapter 15. \n" +
		            "Here is the email you tried to send: \n" +
		            "=====================================\n" +
		            "TO: " + to + "\n" +
		            "FROM: " + from + "\n" +
		            "SUBJECT: " + subject + "\n" +
		            "\n" +
		            body + "\n\n");
		    }*/
        	
        }catch(ServiceException e){
			System.out.println("OrderController: " + e);
			throw new ServletException(e); 
        	}

        
        
        return "/complete.jsp";
    }    
}