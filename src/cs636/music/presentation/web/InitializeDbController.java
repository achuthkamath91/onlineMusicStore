package cs636.music.presentation.web;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.data.*;
import cs636.music.service.*;
import cs636.music.service.ServiceException;
import cs636.music.service.AdminServiceAPI;

public class InitializeDbController extends HttpServlet implements Controller {

	private String view;
	private AdminServiceAPI adminService;
	
	public InitializeDbController(AdminServiceAPI adminService,String view) {
		// TODO Auto-generated constructor stub
		this.view=view;
		this.adminService=adminService;
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			adminService.initializeDB();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return view;
	}

}
