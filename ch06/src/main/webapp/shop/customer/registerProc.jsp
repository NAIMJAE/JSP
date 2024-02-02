<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	request.setCharacterEncoding("UTF-8");
	
	String custId =  request.getParameter("custId");
	String name =  request.getParameter("name");
	String hp =  request.getParameter("hp");
	String addr =  request.getParameter("addr");
	
	// DBCP를 이용한 데이터베이스 작업
	try {
		
		//1 단계	- JNDI 서비스 객체 생성
		Context initCtx = new InitialContext();
		Context ctx = (Context) initCtx.lookup("java:comp/env"); // JNDI 기본 환경 이름
		
		//2 단계 - 커넥션 가져오기
		DataSource ds = (DataSource) ctx.lookup("jdbc/shop");
		Connection conn = ds.getConnection();
		
		//3 단계 - sql 실행 객체 생성
		String sql = "INSERT INTO `Customer` VALUES (?, ?, ?, ?, NOW())";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, custId);
		psmt.setString(2, name);
		psmt.setString(3, hp);
		psmt.setString(4, addr);
		
		
		//4 단계 - sql 실행
		psmt.executeUpdate();
		
		//5 단계 - 결과처리(select문)
		
		//6 단계 - 데이터베이스 종료(커넥션 반납)
		psmt.close();
		conn.close();
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	response.sendRedirect("./list.jsp");
%>