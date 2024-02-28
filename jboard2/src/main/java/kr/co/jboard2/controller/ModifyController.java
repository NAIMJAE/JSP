package kr.co.jboard2.controller;

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

import kr.co.jboard2.DTO.ArticleDTO;
import kr.co.jboard2.DTO.FileDTO;
import kr.co.jboard2.service.ArticleService;
import kr.co.jboard2.service.FileService;

@WebServlet("/modify.do")
public class ModifyController extends HttpServlet{
	private static final long serialVersionUID = 14632235L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ArticleService articleService = ArticleService.getInstance();
	private FileService fileService = FileService.getInstance();
	
	@Override
	public void init() throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("modifyController - doGet");
		
		String no = req.getParameter("no");
		
		ArticleDTO article = articleService.selectArticle(no);
		
		logger.info(""+article.getFileDTOs());
		
		req.setAttribute("article", article);
		
		RequestDispatcher dispather = req.getRequestDispatcher("/modify.jsp");
		dispather.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("modifyController - doPost");
		
		String regip = req.getRemoteAddr();
		int no = Integer.parseInt(req.getParameter("no"));
		logger.info("no : " + req.getParameter("no"));
		
		// 새로 등록한 파일 업로드 (Files)
		ArticleDTO articleDTO = articleService.fileUpload(req);
		articleDTO.setRegip(regip);
		articleDTO.setNo(no);
		
		// 현재 게시글의 file 개수 조회
		int file = articleService.selectArticleFile(no);
		articleDTO.setFile(file);
		
		logger.info("articleDTO : " + articleDTO);
		
		// 글 수정 (updateAritcle)
		int ano = articleService.updateArticle(articleDTO);
		
		logger.info("AfterInsert : "+articleDTO);
		
		// 새로 등록한 파일 등록 (insertFile)
		List<FileDTO> files = articleDTO.getFileDTOs();
		
		for(FileDTO fileDTO : files) {
			fileDTO.setAno(no);
			fileService.insertFile(fileDTO );
		}
		resp.sendRedirect("/jboard2/view.do?no="+no);
	}
}
