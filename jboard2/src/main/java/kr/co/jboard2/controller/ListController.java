package kr.co.jboard2.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DTO.ArticleDTO;
import kr.co.jboard2.service.ArticleService;

@WebServlet("/list.do")
public class ListController extends HttpServlet{
	private static final long serialVersionUID = 1165136432L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ArticleService service = ArticleService.getInstance();
	
	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("ListController - doGet");
		
		// 현재 페이지 번호
		String pg = req.getParameter("pg");
		// 페이지 버튼 누를때 매개변수로 전달
		int currentPg = 1;
		if(pg != null) {
			currentPg = Integer.parseInt(pg);
		}
		
		// 전체 게시글 수 조회 / 10 올림 = 총 페이지 번호
		int total = service.selectCountTotal();
		int lastPageNum = (int) Math.ceil((double)total/10);
		
		// limit ?, 10
		// 0 - 0~10 / 1 - 10~20 / 2 - 20~30 ...
		int start = (currentPg-1) * 10;
		
		// 현재 페이지 그룹 번호 구하기
		int pageGroupCurrent = (int) Math.ceil(currentPg / 10.0);
		int pageGroupStart = (pageGroupCurrent-1) * 10 + 1;
		int pageGroupEnd = pageGroupCurrent * 10;
		
		if(pageGroupEnd > lastPageNum){
			pageGroupEnd = lastPageNum;
		}
		
		// 페이지 시작번호 계산
		int pageStartNum = total - start;
		
		// 전달해야하는 값
		// pageGroupStart, pageGroupEnd, lastPageNum, currentPg, pageStartNum
		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("pageGroupStart", pageGroupStart);
		pageInfo.put("pageGroupEnd", pageGroupEnd);
		pageInfo.put("lastPageNum", lastPageNum);
		pageInfo.put("currentPg", currentPg);
		pageInfo.put("pageStartNum", pageStartNum);

		req.setAttribute("pageInfo", pageInfo);
		
		// 게시글 조회 10개만
		List<ArticleDTO> articles = service.selectArticles(start);
		
		req.setAttribute("articles", articles);
		
		RequestDispatcher dispather = req.getRequestDispatcher("/list.jsp");
		dispather.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
}
