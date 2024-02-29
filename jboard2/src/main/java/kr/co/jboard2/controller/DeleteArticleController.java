package kr.co.jboard2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DTO.FileDTO;
import kr.co.jboard2.service.ArticleService;
import kr.co.jboard2.service.FileService;

@WebServlet("/deleteArticle.do")
public class DeleteArticleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ArticleService articleService = ArticleService.getInstance();
	private FileService fileService = FileService.getInstance();
	
	@Override
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("deleteArticleController - doGet");
		
		String no = req.getParameter("no");
		String parent = req.getParameter("parent");
		// 게시글 번호 no -> 게시글, file테이블, upload 폴더 삭제
		
		// file 조회
		List<FileDTO> fileDTO = fileService.selectFiles(no);
		
		// upload 폴더내 파일 삭제 (sName 필요)
		ServletContext ctx = getServletContext();
		for (FileDTO file : fileDTO) {
			logger.info(file.getsName());
		    fileService.deleteUploadFile(ctx, file.getsName());
		}
		// 파일 삭제 (순서!!!!!)
		fileService.deleteFileAll(no);
		
		// 댓글 삭제
		articleService.deleteComment(no, parent);
		
		// 게시글 삭제
		articleService.deleteArticle(no);
		
		resp.sendRedirect("/jboard2/list.do");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
