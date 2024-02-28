package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.MemberDTO;
import service.ServiceMember;

@WebServlet("/member/list.do")
public class ListController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// 자바 기본 로그
	// private Logger logger =  Logger.getGlobal();
	
	// Logback 로거 생성
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ServiceMember service = ServiceMember.getInstance();
	
	@Override
	public void init() throws ServletException {
		
		// 자바 기본 로그 출력
		logger.info("ListController - init()...");
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 로그 레벨 (요건 배포 단계에서 삭제)
		// logger.error("ListContoller error - doGet()...");
		// logger.warn("ListContoller warn - doGet()...");
		logger.info("ListContoller info - doGet()...");
		// logger.debug("ListContoller debug - doGet()...");
		// logger.trace("ListContoller trace - doGet()...");
		
		List<MemberDTO> members = service.selectMembers();

		req.setAttribute("members", members);
		
		RequestDispatcher dispather = req.getRequestDispatcher("/member/list.jsp");
		dispather.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
}
