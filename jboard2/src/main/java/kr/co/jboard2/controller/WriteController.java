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
import kr.co.jboard2.service.UserService;

@WebServlet("/write.do")
public class WriteController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ArticleService articleService = ArticleService.getInstance();
	private FileService fileService = FileService.getInstance();
	
	@Override
	public void init() throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
	
		RequestDispatcher dispather = req.getRequestDispatcher("/write.jsp");
		dispather.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("WriteController - doPost");
		
		String regip = req.getRemoteAddr();
		
		// 파일 업로드 (Files)
		ArticleDTO articleDTO = articleService.fileUpload(req);
		articleDTO.setRegip(regip);
		
		// 글 등록 (insertAritcle)
		int ano = articleService.insertArticle(articleDTO);
		
		logger.info("AfterInsert"+articleDTO);
		logger.info("ano"+ano);
		
		// 파일 등록 (insertFile)
		List<FileDTO> files = articleDTO.getFileDTOs();
		
		for(FileDTO fileDTO : files) {
			fileDTO.setAno(ano);
			fileService.insertFile(fileDTO );
		}
		
		
		resp.sendRedirect("/jboard2/view.do?no="+ano);
	}
}
