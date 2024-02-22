package sub1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeServlet extends HttpServlet{

	private static final long serialVersionUID = 2L;

	@Override
	public void init() throws ServletException {
		System.out.println("WelcomeServlet init()...");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// get 요청이 들어올 때
	System.out.println("WelcomeServlet doGet()...");
	
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
	writer.println("<a href='./greeting.do'>GreentingServlet</a>");
	writer.println("</body>");
	writer.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("WelcomeServlet doPost()...");
	}
	
	@Override
	public void destroy() {
		System.out.println("WelcomeServlet destroy()...");
	}
}
