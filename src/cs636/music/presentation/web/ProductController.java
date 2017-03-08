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

public class ProductController extends HttpServlet implements Controller
{
	private UserServiceAPI userService;
	private String view;
	
	public ProductController(UserServiceAPI userService, String view)
	{
		this.userService = userService;
		this.view = view;
	}
	
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		try{
			String productCode = request.getParameter("productCode");
			
			Product product = userService.getProduct(productCode);
			HttpSession session = request.getSession();
			session.setAttribute("product", product);
			System.out.println("productCode: " + productCode);
			
		}catch(ServiceException e){
			System.out.println("ProductController: " + e);
			throw new ServletException(e);
		}
		return view;
	}
}