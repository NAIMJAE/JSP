package kr.co.jboard2.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DTO.UserDTO;
import kr.co.jboard2.service.UserService;

@WebServlet("/user/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 14351362345L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private UserService service = UserService.getInstance();
	@Override
	public void init() throws ServletException {

	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("loginController - doGet");
		
		String success = req.getParameter("success");
		
		req.setAttribute("success", success);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/user/login.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("loginController - doPost");
		
		String uid = req.getParameter("uid");
		String pass = req.getParameter("pass");
		logger.info("log"+uid + pass);
		UserDTO user = service.selectUserForLogin(uid, pass);
		logger.info("log"+user);
		
		if(user.getUid() == null) {
			resp.sendRedirect("/jboard2/user/login.do");
		}else {
			HttpSession session = req.getSession();
			session.setAttribute("sessUser", user);
			
			resp.sendRedirect("/jboard2/list.do");
		}
	}
}