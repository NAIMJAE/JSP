package controller.user1;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.User1DTO;
import service.User1Service;

@WebServlet("/user1/register.do")
public class registerController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	// controller는 service를 멤버로 가짐
	private User1Service service = User1Service.getInstance();
	
	@Override
	public void init() throws ServletException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/user1/register.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uid = req.getParameter("uid");
		String name = req.getParameter("name");
		String birth = req.getParameter("birth");
		String hp = req.getParameter("hp");
		String age = req.getParameter("age");
		
		User1DTO user = new User1DTO();
		user.setUid(uid);
		user.setName(name);
		user.setBirth(birth);
		user.setHp(hp);
		user.setAge(age);
		
		service.insertUser1(user);
		
		// redirect - 클라이언트가 다시 요청하는 주소
		resp.sendRedirect("/ch10/user1/list.do");
	}
}
