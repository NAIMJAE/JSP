package kr.co.jboard2.controller.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
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

import kr.co.jboard2.DTO.UserDTO;
import kr.co.jboard2.service.UserService;

@WebServlet("/user/loginCheck.do")
public class LoginCheckController extends HttpServlet {
	private static final long serialVersionUID = 143514565L;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	UserService service = UserService.getInstance();
	@Override
	public void init() throws ServletException {

	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("loginController - doGet");

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("loginCheckController - doPost");
		
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
		Properties props =  gson.fromJson(requestBody.toString(), Properties.class);
		logger.debug("props : "+ props);
		
		// 인증코드 일치 여부 확인
		String uid = props.getProperty("uid");
		String pass = props.getProperty("pass");
		UserDTO result = service.selectUserForLogin(uid, pass);
		logger.info(""+result);
		int check = 0;
		if(result.getName() != null) {
			check = 1;
		}else {
			check = 0;
		}
		logger.info(""+check);
		// JSON 출력
		JsonObject json = new JsonObject();
		json.addProperty("check", check);
		PrintWriter writer = resp.getWriter();
		writer.print(json);
	}
}
