package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.UserServiceAPI;

// like Spring MVC DispatcherServlet and its config file, but simpler.
// This servlet is handling the User pages of the music project,
// calling on various controllers, roughly one for each user page or form.
// Note that all the jsp filenames (for views) are in this file, not
// in the controllers themselves.  Each controller is set up
// here and given its forward-to URLs in its constructor.

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 3971217231726348088L;
	private static UserServiceAPI userService;

	// The controllers, roughly one per student page or form
	private static Controller catalogController;
	private static Controller OrderController;
	private static Controller userController;
	private static Controller productController;
	private static Controller registerController;
	private static Controller cartController;
	private static Controller checkOutController;
	private static Controller listenController;
	private static Controller userLoginController;
	private static Controller logoutController;
		
	private static final String SITE_WELCOME_URL = "/welcome.html";
	private static final String SITE_WELCOME_VIEW = "/welcome.jsp";
	private static final String USER_WELCOME_URL = "/home.html";
	private static final String USER_WELCOME_VIEW = "/WEB-INF/jsp/home.jsp";
	private static final String CATALOG_URL = "/catalog.html";
	private static final String CATALOG_VIEW = "/WEB-INF/jsp/catalog.jsp";
	private static final String PRODUCT_URL = "/catalog/product.html";
	private static final String PRODUCT_VIEW = "/WEB-INF/jsp/product.jsp";
	private static final String CART_URL = "/cart.html";
	private static final String CART_VIEW = "/WEB-INF/jsp/cart.jsp";
	private static final String REGISTER_URL = "/register.html";
	private static final String REGISTER_VIEW = "/WEB-INF/jsp/register.jsp";	
	private static final String USER_LOGIN_URL = "/login.html";
	private static final String USER_LOGIN_VIEW = "/WEB-INF/jsp/login.jsp";	
	private static final String CHECKOUT_URL = "/checkout.html";
	private static final String LISTEN_URL = "/listen.html";
	private static final String LOGOUT_URL = "/logout.html";
	private static final String LOGOUT_VIEW = "/WEB-INF/jsp/logout.jsp";
	
	
	// Initialization of servlet: runs before any request is
	// handled in the web app. It does PizzaSystemConfig initialization
	// then sets up all the controllers
	@Override
	public void init() throws ServletException {
		System.out.println("Starting dispatcher servlet initialization");
		try {
			MusicSystemConfig.configureServices();
		} catch (Exception e) {
			System.out.println(MusicSystemConfig.exceptionReport(e));			
			throw new ServletException(e); // fatal error
			//System.exit(1);
		}
		userService = MusicSystemConfig.getUserService();
		if (userService==null)
			throw new ServletException(	"DispatcherServlet: bad initialization");
		
		// create all the controllers and their forward URLs
		userController = new UserController(userService,USER_WELCOME_VIEW);
		catalogController = new CatalogController(userService,CATALOG_VIEW);
		productController = new ProductController(userService,PRODUCT_VIEW);
		registerController = new RegisterController(userService,REGISTER_VIEW);
		cartController = new CartController(userService,CART_VIEW);
		checkOutController=new CheckOutController(userService,"/WEB-INF/jsp/orderconfirmation.jsp");
		listenController=new ListenController(userService,USER_WELCOME_VIEW);		
		userLoginController=new UserLoginController(userService,USER_LOGIN_VIEW);
		logoutController=new LogoutController(LOGOUT_VIEW);
		
			
	}
	
	// Called when app server is shutting this servlet down
	// because it is shutting the app down.
	// Since this servlet is in charge of this app, it is
	// the one to respond by shutting down the BL+DAO
	// (the SysTestServlet ignores the shutdown)
	@Override
	public void destroy() {
		System.out.println("DispatcherServlet: shutting down");
		MusicSystemConfig.shutdownServices();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get webapp-specific part of the URL, the part after
		// the context path that identifies the webapp
		//String requestURL = request.getServletPath();
		String requestURL = request.getServletPath();
		//String requestURL = request.getRequestURI();
		System.out.println("DispatcherServlet: requestURL = " + requestURL);
		// At studentWelcome, the user gets a StudentBean.
		// If it's not there for subsequent pages, hand the request to
		// studentWelcome. Having the bean is like being "logged in".
		boolean haslog = (request.getSession().getAttribute("log")
				!= null);
		if(haslog==false){
		request.getSession().setAttribute("log",0);
		}
		System.out.println("DispatcherServlet: requestURL = " + requestURL);
		// At studentWelcome, the user gets a StudentBean.
		// If it's not there for subsequent pages, hand the request to
		// studentWelcome. Having the bean is like being "logged in".
		boolean hasBean = (request.getSession().getAttribute("cart")
				!= null);
		if(hasBean==false)
		{
			CartBean cart = new CartBean();
			request.getSession().setAttribute("cart", cart);
		}
		boolean hasUser = (request.getSession().getAttribute("user")
				!= null);

		// Dispatch to the appropriate Controller, which will determine
		// the next URL to use as well as do its own actions.
		// The URL returned by handleRequest will be a context-relative URL, 
		// like /WEB-INF/jsp/foo.jsp (a view) 
		// or /something.html (for a controller).
		// Note that although resources below /WEB-INF are inaccessible
		// to direct HTTP requests, they are accessible to forwards
		String forwardURL = null; 		
		System.out.println("DispatcherServlet: requestURL = " + requestURL);
		if (requestURL.equals(SITE_WELCOME_URL))
			forwardURL = SITE_WELCOME_VIEW; // no controller needed
        // test for bean, and if not there, send user to student welcome page
		else if (requestURL.equals(USER_WELCOME_URL))
			forwardURL = userController.handleRequest(request, response);
		else if (requestURL.equals(CATALOG_URL))
			forwardURL = catalogController.handleRequest(request, response);
		else if (requestURL.equals(PRODUCT_URL))
			forwardURL = productController.handleRequest(request, response);
		else if (requestURL.equals(CART_URL))
			forwardURL = cartController.handleRequest(request, response);			
		else if (requestURL.equals(REGISTER_URL))
			forwardURL = registerController.handleRequest(request, response);
		else if (requestURL.equals(CHECKOUT_URL)){
			forwardURL = checkOutController.handleRequest(request, response); // no controller needed
	        // test for bean, and if not there, send user to student welcome page
		}else if (requestURL.equals(USER_LOGIN_URL)){
			forwardURL = userLoginController.handleRequest(request, response); // no controller needed
	        // test for bean, and if not there, send user to student welcome page
			if(!forwardURL.equals(USER_LOGIN_VIEW)){
				requestURL=forwardURL;
			}
		}else if (requestURL.equals(LISTEN_URL)){
			forwardURL = listenController.handleRequest(request, response);
			// no controller needed
	        // test for bean, and if not there, send user to student welcome page
			requestURL=forwardURL;
		}else if (requestURL.equals(LOGOUT_URL)){
			forwardURL = logoutController.handleRequest(request, response);
			// no controller needed
	        // test for bean, and if not there, send user to student welcome page
			requestURL=forwardURL;
		}else 
			throw new ServletException("DispatcherServlet: Unknown servlet path: "
					+ requestURL);
		// Here with good forwardURL to forward to
		System.out.println("DispatcherServlet: forwarding to "+ forwardURL);
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(forwardURL);
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
