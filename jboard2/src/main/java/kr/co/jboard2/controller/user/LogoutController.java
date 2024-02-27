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

@WebServlet("/user/logout.do")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 14351362345L;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	UserService service = UserService.getInstance();
	@Override
	public void init() throws ServletException {

	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("logoutController - doGet");
		
		HttpSession session = req.getSession();
		//현재 사용자 객체 세션 제거
		session.removeAttribute("sessuser");
		// 현재 세션 해제
		session.invalidate();

		// 로그인으로 이동
		resp.sendRedirect("/jboard2/user/login.do");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("logoutController - doPost");

		}
}