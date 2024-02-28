package kr.co.jboard2.controller.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import kr.co.jboard2.DTO.FileDTO;
import kr.co.jboard2.service.ArticleService;
import kr.co.jboard2.service.FileService;
import kr.co.jboard2.service.UserService;

@WebServlet("/fileDelete.do")
public class FileDeleteController extends HttpServlet{

	private static final long serialVersionUID = 1241365123024L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private FileService fileService = FileService.getInstance();
	private ArticleService articleService = ArticleService.getInstance();
	
	@Override
	public void init() throws ServletException {

	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("FileDelete - doGet");
		
		String fno = req.getParameter("no");
		logger.info("fno : " + fno);
		int success = 0;
		
		// file 조회
		FileDTO file = fileService.selectFile(fno);
		
		if (file != null) {
			// file 테이블의 데이터 삭제 (fno 필요)
			// 게시글의 file 갯수 -1 (ano 필요)
			success = fileService.deleteFile(fno, file.getAno());
			
			logger.info("getsName() : " + file.getsName());
			
			// uploads에 업로드된 file 삭제 (sName 필요)
			ServletContext ctx = getServletContext();
			fileService.deleteUploadFile(ctx, file.getsName());
		}else {
			return;
		}
		
		logger.info("success : " + success);
		
		// JSON 출력
		JsonObject json = new JsonObject();
		json.addProperty("success", success);
		PrintWriter writer = resp.getWriter();
		writer.print(json);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("FileDelete - doPost");
		
	}
}
