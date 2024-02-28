package kr.co.jboard2.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import kr.co.jboard2.DTO.FileDTO;
import kr.co.jboard2.service.FileService;
@WebServlet("/fileCheck.do")
public class FileCheckController extends HttpServlet{

	private static final long serialVersionUID = 125164353451L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private FileService service = FileService.getInstance();
	
	@Override
	public void init() throws ServletException {

	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("FileCheck - doGet");
		
		String fno = req.getParameter("no");
		int result = 0;
		
		FileDTO file = service.selectFile(fno);
		if(file != null) {
			result = 1;
		}
		// JSON 출력
		JsonObject json = new JsonObject();
		json.addProperty("result", result);
		PrintWriter writer = resp.getWriter();
		writer.print(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("FileCheck - doPost");
		
	}
}
