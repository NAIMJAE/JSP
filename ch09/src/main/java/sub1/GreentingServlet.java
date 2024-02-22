package sub1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/greeting.do")
public class GreentingServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		// 서블릿이 최초 실행될 때
		System.out.println("greetingServlet init()...");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get 요청이 들어올 때
		System.out.println("greetingServlet doGet()...");
		
		// HTML 출력
		resp.setContentType("text/html;charset=utf-8");
		
		PrintWriter writer = resp.getWriter();
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<meta charset='UTF=8'>");
		writer.println("<title>HelloServlet</title>");
		writer.println("</head>");
		writer.println("<h3>HelloServlet</h3>");
		writer.println("<a href='./1.ServletTest.jsp'>Servlet 메인</a>");
		writer.println("<a href='./hello.do'>HelloServlet</a>");
		writer.println("<a href='./welcome.do'>WelcomeServlet</a>");
		writer.println("<a href='./greeting.do'>greentingServlet</a>");
		writer.println("</body>");
		writer.println("</html>");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("greetingServlet doPost()...");
	}
	
	@Override
	public void destroy() {
		// 서블릿이 종료될 때 (was가 중지될 때)
		System.out.println("greetingServlet destory()");
	}
	
}
