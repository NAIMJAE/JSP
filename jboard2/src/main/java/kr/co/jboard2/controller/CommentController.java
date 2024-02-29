package kr.co.jboard2.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import kr.co.jboard2.DTO.ArticleDTO;
import kr.co.jboard2.service.ArticleService;

@WebServlet("/comment.do")
public class CommentController extends HttpServlet{
	
	private static final long serialVersionUID = 1125412545235L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ArticleService articleService = ArticleService.getInstance();
	
	@Override
	public void init() throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 삭제 처리
		String no = req.getParameter("no");
		String type = req.getParameter("type");
		String parent = req.getParameter("parent");
	
		int result = 0;
		if(type.equals("remove")) {
			result = articleService.deleteComment(no, parent);
		}
		// JSON 출력
		JsonObject json = new JsonObject();
		json.addProperty("result", result);
		PrintWriter writer = resp.getWriter();
		writer.print(json);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("CommentController - doPost");
		
		// AJAX POST 데이터 스트림 수신처리
		BufferedReader reader = req.getReader();
		StringBuilder requestBody = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			requestBody.append(line);
		}
		reader.close();

		// JSON 파싱
		Gson gson = new Gson();
		ArticleDTO articleDTO =  gson.fromJson(requestBody.toString(), ArticleDTO.class);
		String regip = req.getRemoteAddr();
		articleDTO.setRegip(regip);
		logger.debug("articleDTO : "+ articleDTO);
		
		int pk = articleService.insertComment(articleDTO);
		int parent = articleDTO.getParent();
		
		// JSON 출력
		JsonObject json = new JsonObject();
		json.addProperty("pk", pk);
		json.addProperty("parent", parent);
		PrintWriter writer = resp.getWriter();
		writer.print(json);
	}
}
