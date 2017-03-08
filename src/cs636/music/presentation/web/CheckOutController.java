package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Cart;
import cs636.music.domain.Invoice;
import cs636.music.domain.User;
import cs636.music.service.data.*;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;

public class CheckOutController extends HttpServlet implements Controller {

	
	private String view;
	private UserServiceAPI userService;
	
	public CheckOutController(UserServiceAPI userService, String view){
		this.view = view;
		this.userService = userService;
	}
	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		CartBean cartbean=(CartBean) session.getAttribute("cart");
		UserData user=(UserData) session.getAttribute("user");
		Cart cart=new Cart();
		
		InvoiceData invoice=null;
		if(cart.getItems().size()!=0){
		try {
		invoice=userService.checkout(cart, user.getId());
		
			
		} catch (ServiceException e) {
			System.out.println("CheckOutController: " + e);
			throw new ServletException(e);
		}
		}
		request.setAttribute("invoice",invoice);
		return view;
	}

}
