package cs636.music.presentation.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import cs636.music.domain.*;
import cs636.music.service.*;
import cs636.music.dao.*;

public class CartController extends HttpServlet  implements Controller{


	private UserServiceAPI userService;	
	private String view;

	public CartController(UserServiceAPI userService, String view) {
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
		String url = "/WEB-INF/jsp/cart.jsp";
		int quantitycount = 0;
		
		try {
			String quantity = request.getParameter("quantity");
			String productCode = request.getParameter("productCode");
			String removeButtonValue = request.getParameter("removeButton");
			CartBean cart = (CartBean) session.getAttribute("cart");
			
			if(cart == null)
			{
				cart = new CartBean();
				session.setAttribute("cart", cart);
			}			
			
			if(quantity!=null){
			quantitycount = Integer.parseInt(quantity);
			}
			
			if(removeButtonValue != null)
			{
				quantitycount = 0;
			}
			Product product=null;
			if(productCode!=null){			
				product = userService.getProduct(productCode);
				session.setAttribute("product", product);
			}
			
			if(product != null)
			{
				LineItem lineItem = new LineItem();
				lineItem.setProduct(product);
				lineItem.setQuantity(quantitycount);
				if(quantitycount > 0)
					cart.addItem(lineItem);
				else
					cart.removeItem(product);
			}
			session.setAttribute("cart", cart);	

		} catch (ServiceException e) {
			System.out.println("CartController: " + e);
			throw new ServletException(e);
		}

		return url;
	}
	
	
}