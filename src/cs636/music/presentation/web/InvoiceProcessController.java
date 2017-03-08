package cs636.music.presentation.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Invoice;
import cs636.music.service.data.*;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.ServiceException;

public class InvoiceProcessController extends HttpServlet implements Controller {

	private String view;
	private AdminServiceAPI adminService;
		public InvoiceProcessController(AdminServiceAPI adminService,String view) {
			// TODO Auto-generated constructor stub
			this.view=view;
			this.adminService=adminService;
		}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session=request.getSession();
		String invoiceId=request.getParameter("invoiceId");
		if(invoiceId!=null){
			Long i=(long) Integer.parseInt(invoiceId);
			try {
				adminService.processInvoice(i);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Set<InvoiceData> invoices=null;
		try {
			invoices=adminService.getListofUnprocessedInvoices();
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("invoices",invoices);
		return view;
	}

}
