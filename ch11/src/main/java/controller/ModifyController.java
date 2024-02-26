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

@WebServlet("/member/modify.do")
public class ModifyController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// Logback 로거 생성
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ServiceMember service = ServiceMember.getInstance();
	
	@Override
	public void init() throws ServletException {
		// 자바 기본 로그 출력
		logger.info("ModifyController - init()...");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 로그 레벨 (요건 배포 단계에서 삭제)
		logger.info("ModifyContoller info - doGet()...");
		
		String uid = req.getParameter("uid");
		
		MemberDTO member = service.selectMember(uid);
		
		req.setAttribute("member", member);
		
		RequestDispatcher dispather = req.getRequestDispatcher("/member/modify.jsp");
		dispather.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그 레벨 (요건 배포 단계에서 삭제)
		logger.info("ModifyContoller info - doGet()...");
		
		String uid = req.getParameter("uid");
		String name = req.getParameter("name");
		String hp = req.getParameter("hp");
		String pos = req.getParameter("pos");
		String dep = req.getParameter("dep");
		String rdate = req.getParameter("rdate");
		
		MemberDTO member = new MemberDTO();
		member.setUid(uid);
		member.setName(name);
		member.setHp(hp);
		member.setPos(pos);
		member.setDep(dep);
		member.setRdate(rdate);
		
		service.updateMember(member);
		
		resp.sendRedirect("/ch11/member/list.do");
	}
	
}
