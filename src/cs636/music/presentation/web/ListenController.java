package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.service.data.*;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;

public class ListenController extends HttpServlet implements Controller {
	private UserServiceAPI userService;
	private String view;

	public ListenController(UserServiceAPI userService,String view) {
		// TODO Auto-generated constructor stub
		this.userService=userService;
		this.view=view;
	}

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String src="";
		HttpSession session=request.getSession();
		UserData user=(UserData) session.getAttribute("user");
		String proCode=request.getParameter("productCode");
		String trackNum=request.getParameter("trackNum");
		//System.out.println(trackNum);
		int log=(int) session.getAttribute("log");
		try {
		Product product=userService.getProduct(proCode);
		int	tracknum=0;
		if( user != null){
			if(trackNum!=null){
			tracknum = Integer.parseInt(trackNum);
				}
			Track track=product.findTrackByNumber(tracknum);
				userService.addDownload(user.getId(),track);
				if(log==0){
				src="/catalog/product.html?productCode="+proCode;
				}
				else{
					src="/sound/"+proCode+"/"+track.getSampleFilename();	
				}
				session.setAttribute("log",log+1);
				view=src;
		}else{
			view = "/WEB-INF/jsp/login.jsp";
		}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return view;
	}

}
