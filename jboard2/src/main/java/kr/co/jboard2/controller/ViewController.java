package kr.co.jboard2.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DTO.ArticleDTO;
import kr.co.jboard2.DTO.FileDTO;
import kr.co.jboard2.service.ArticleService;
import kr.co.jboard2.service.FileService;

@WebServlet("/view.do")
public class ViewController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ArticleService articleService = ArticleService.getInstance();
	private FileService fileService = FileService.getInstance();
	
	@Override
	public void init() throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("ViewController - doGet");
		// 게시글 조회
		int no = Integer.parseInt(req.getParameter("no"));
		ArticleDTO article = articleService.selectArticle(no);
	
		req.setAttribute("article", article);
		// 파일 조회
		if(article.getFile() > 0) {
			FileDTO file = fileService.selectFile(no);
			req.setAttribute("file", file);
		}
		RequestDispatcher dispather = req.getRequestDispatcher("/view.jsp");
		dispather.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
