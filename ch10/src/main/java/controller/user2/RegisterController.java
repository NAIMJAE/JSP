package controller.user2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.User2DTO;
import service.User2Service;

@WebServlet("/user2/register.do")
public class RegisterController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private User2Service service = User2Service.getInstance();
	
	@Override
	public void init() throws ServletException {

	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/user2/register.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uid = req.getParameter("uid");
		String name = req.getParameter("name");
		String birth = req.getParameter("birth");
		String addr = req.getParameter("addr");
		
		User2DTO user = new User2DTO();
		user.setUid(uid);
		user.setName(name);
		user.setBirth(birth);
		user.setAddr(addr);
		
		service.insertUser2(user);
		
		resp.sendRedirect("/ch10/user2/list.do");
	}
}
