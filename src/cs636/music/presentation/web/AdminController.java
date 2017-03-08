package cs636.music.presentation.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.UserData;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.ServiceException;
import javax.servlet.http.HttpSession;

public class AdminController extends HttpServlet implements Controller { 
	
	private AdminServiceAPI adminService;
	private String view;
	
	
	public AdminController(AdminServiceAPI adminService,String view) {
		// TODO Auto-generated constructor stub
		this.view=view;
		this.adminService=adminService;
	}
	
	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
			
			String url = "";
			String error=null;
			String adminURL = "/WEB-INF/admin/index.jsp";
			try {
				HttpSession session = request.getSession();
				String requestURL = request.getServletPath();
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				/*String productCode=request.getParameter("productCode");
				String trackNum=request.getParameter("trackNum");	*/			
				
				Boolean admin=(session.getAttribute("admin")!=null);
				//// if list of products doesn't exist, initialize it,
				// and store it for the remainder of the session
				if(username!=null&password!=null){
					 admin = adminService.checkLogin(username, password);
					 if(admin==null){
						 error="Invalid Credentials";
						 
					 }
				}
				if (admin==true) {
					session.setAttribute("admin", admin);
					url = adminURL;
				} else {
					url = view;
					
					
				}
				request.setAttribute("error",error);

			} catch (ServiceException e) {
				System.out.println("UserLoginController: " + e);
				throw new ServletException(e);
			}

			return url;
			
			
			
			
			}
	
	/*
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String requestURI = request.getRequestURI();
		System.out.println("AdminServlet: requestURL = " + requestURI);
		String url = null;
		if (requestURI.contains("initializeDB.html")) {
			url = initializeDB(request, response);
		} else {
			doGet(request, response);
			return;
		}

		getServletContext().getRequestDispatcher(url)
				.forward(request, response);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String requestURI = request.getRequestURI();
		System.out.println("in doGet of AdminServlet, requestURI = "
				+ requestURI);
		String url = null;
		if (requestURI.contains("adminWelcome.html")) {
			url = ADMIN_JSP_DIR + "adminWelcome.jsp";
		} else if (requestURI.contains("/invoice")) {
			url = processInvoice(request, response);
		} else if (requestURI.contains("/product")) {
			url = processInvoice(request, response); 
		} else if (requestURI.contains("listVariables.html")) {
			url =  ADMIN_JSP_DIR + "listVariables.jsp";
		} else if (requestURI.contains("logout.html")) {
			request.getSession().invalidate();  // drop session
			url =  ADMIN_JSP_DIR + "logout.jsp"; 
		} else {
			System.out.println("no match for requestURI = " + requestURI);
		}

		getServletContext().getRequestDispatcher(url)
				.forward(request, response);
	}
	
	private String initializeDB(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String info = null;
		try {
			adminService.initializeDB();
			info = "success";
		} catch (ServiceException e) {
			info = "failed: " + MusicSystemConfig.exceptionReport(e);
		}
		request.setAttribute("info", info);
		String url = ADMIN_JSP_DIR + "initializeDB.jsp";
		return url;
	}
	
	private String processInvoice(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
				return null;
			}
	
	*/
}