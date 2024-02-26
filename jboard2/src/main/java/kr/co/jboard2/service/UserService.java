package kr.co.jboard2.service;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DAO.UserDAO;
import kr.co.jboard2.DTO.UserDTO;

public class UserService {
	private static UserService instance = new UserService();
	public static UserService getInstance() {
		return instance;
	}
	private UserService() {}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private UserDAO dao = UserDAO.getInstance();
	
// 사용자 정의 CRUD 메서드
	// 로그인 확인 메서드	
	public void insertUser(UserDTO userDTO) {
		dao.insertUser(userDTO);
	}
	public UserDTO selectUser(String uid) {
		return dao.selectUser(uid);
	}
	public List<UserDTO> selectUsers() {
		return dao.selectUsers();
	}
	public void updateUser(UserDTO userDTO) {
		dao.updateUser(userDTO);
	}
	public void deleteUser(String uid) {
		dao.deleteUser(uid);
	}
	
// 사용자 정의 CRUD 메서드
	// 회원가입 중복 검사 메서드
	public int selectCountUser(String type, String value) {
		return dao.selectCountUser(type, value);
	}
	// 로그인 확인 메서드
	public UserDTO selectUserForLogin(String uid, String pass) {
		return dao.selectUserForLogin(uid, pass);
	}
	
	// 이메일 전송 메서드
	public void sendEmailCode(HttpSession session, String receiver) {
		// 인증 코드 생성
		int code = ThreadLocalRandom.current().nextInt(100000,1000000);
		
		// 인증 코드 세션에 기록
		session.setAttribute("code", String.valueOf(code));
		
		// 기본 정보
		String sender = "skdlawo5985@gmail.com";
		String password = "fvcc crac dsyc okix";// 앱 비밀번호
		String title = "jboard2 인증코드 입니다.";
		String content = "<h1>인증코드는 " + code + "입니다.</h1>";
		
		// Gmail SMTP 서버 설정
		Properties props = new Properties(); // 문자열 구조체?
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		// Gmail SMTP 세션 설정
		try {
			Session gmailSession = Session.getInstance(props, new Authenticator(){
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(sender, password);
				}
			});
	
			// 메일 객체 생성 및 설정
			Message message = new MimeMessage(gmailSession);
			
			message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			message.setSubject(title);
			message.setContent(content, "text/html;charset=UTF-8");
			
			// 세션에 인증 코드 저장
			
			// 메일 발송
			Transport.send(message);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 이메일 수신 메서드
	public int cunfirmEmailCode(HttpSession session, String code) {
		
		String sessCode = (String)session.getAttribute("code");
		
		if(sessCode.equals(code)) {
			// 성공
			return 1;
		}else {
			return 0;
		}
	}
}
