package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.AdminService;
import cs636.music.service.AdminServiceAPI;

public class AdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static AdminServiceAPI adminService;
	
	private static Controller adminController;
	private static Controller initializeDbController;
	private static Controller downloadsController;
	private static Controller invoicesController;
	private static Controller invoiceProcessController;
	private static Controller logoutController;
	
	
	
	private static final String ADMIN_LOGIN_URL = "/login";
	private static final String ADMIN_LOGIN_VIEW = "/WEB-INF/admin/login.jsp";
	private static final String ADMIN_WELCOME_URL = "/welcome";
	private static final String ADMIN_WELCOME_VIEW = "/WEB-INF/admin/index.jsp";
	private static final String DOWNLOADS_URL = "/downloads";
	private static final String DOWNLOADS_VIEW = "/WEB-INF/admin/downloads.jsp";
	private static final String INVOICES_URL = "/invoices";
	private static final String INVOICES_VIEW = "/WEB-INF/admin/invoices.jsp";
	private static final String PROCESS_URL = "/process";
	private static final String PROCESS_VIEW = "/WEB-INF/admin/unprocessed.jsp";
	private static final String LOGOUT_URL = "/logout";
	private static final String LOGOUT_VIEW = "/WEB-INF/admin/logout.jsp";
	
	@Override
	public void init() throws ServletException {
		
		System.out.println("Starting dispatcher servlet initialization for admin");
		try {
				MusicSystemConfig.configureServices();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println(MusicSystemConfig.exceptionReport(e));
				System.exit(1);
			}
		
		adminService = MusicSystemConfig.getAdminService();
		
		adminController = new AdminController(adminService,ADMIN_LOGIN_VIEW);
		initializeDbController = new InitializeDbController(adminService,ADMIN_WELCOME_VIEW);
		downloadsController = new DownloadsController(adminService,DOWNLOADS_VIEW);
		invoicesController = new InvoicesController(adminService,INVOICES_VIEW);
		invoiceProcessController = new InvoiceProcessController(adminService,PROCESS_VIEW);
		logoutController = new LogoutController(LOGOUT_VIEW);
		
	}
	
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
		String requestURL = request.getRequestURI();
		//String requestURL = request.getServletPath();
		
		boolean hasAdmin = (request.getSession().getAttribute("admin")
				!= null);
		String forwardURL = null;
		
		if (requestURL.endsWith(ADMIN_LOGIN_URL) || !hasAdmin){
		System.out.println("DispatcherServlet: requestURLIn = " + requestURL);
			forwardURL = adminController.handleRequest(request, response); // no controller needed
	        // test for bean, and if not there, send admin to admin welcome page
			if(!forwardURL.equals(ADMIN_LOGIN_VIEW)){
				requestURL=forwardURL;
			}
		}else if (requestURL.endsWith(ADMIN_WELCOME_URL)){
			forwardURL =  ADMIN_WELCOME_VIEW;// no controller needed
        // test for bean, and if not there, send user to student welcome page
			
		}else if (requestURL.endsWith("/initializedb")){
			forwardURL = initializeDbController.handleRequest(request, response); // no controller needed
	        // test for bean, and if not there, send user to student welcome page
				
		}else if (requestURL.endsWith(DOWNLOADS_URL)){
			forwardURL = downloadsController.handleRequest(request, response); // no controller needed
	        // test for bean, and if not there, send user to student welcome page
				
		}else if (requestURL.endsWith(INVOICES_URL)){
			forwardURL = invoicesController.handleRequest(request, response); // no controller needed
        // test for bean, and if not there, send user to student welcome page
			
		}else if (requestURL.endsWith(PROCESS_URL)){
			forwardURL = invoiceProcessController.handleRequest(request, response); // no controller needed
        // test for bean, and if not there, send user to student welcome page
		}else if (requestURL.endsWith(PROCESS_URL)){
			forwardURL = invoiceProcessController.handleRequest(request, response); // no controller needed
        // test for bean, and if not there, send user to student welcome page
		}else if (requestURL.endsWith(LOGOUT_URL)){
			forwardURL = logoutController.handleRequest(request, response); // no controller needed
	        // test for bean, and if not there, send user to student welcome page
				
		}else{ 
			throw new ServletException("DispatcherServlet: Unknown servlet path: "+ requestURL);
			
		}
		// Here with good forwardURL to forward to
		System.out.println("DispatcherServlet: forwarding to "+ forwardURL);
		RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(forwardURL);
		dispatcher.forward(request, response);		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
}