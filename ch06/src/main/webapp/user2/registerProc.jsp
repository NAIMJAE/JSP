<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 인코딩
	request.setCharacterEncoding("UTF-8");
	
	// 값 받아오기
	String uid = request.getParameter("uid");
	String name = request.getParameter("name");
	String birth = request.getParameter("birth");
	String addr = request.getParameter("addr");

	// 데이터베이스 작업
	String host = "jdbc:mysql://127.0.0.1:3306/studydb";
	String user = "naimjae";
	String pass = "abc1234";
	
	try {
		// 1단계 - jdbc 드라이버 로드
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// 2단계 - 데이터베이스 접속
		Connection conn =  DriverManager.getConnection(host, user, pass);
		
		// 3단계 - sql 실행객체 생성
		String sql = "INSERT INTO `User2` VALUES (?,?,?,?)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		
		psmt.setString(1, uid);
		psmt.setString(2, name);
		psmt.setString(3, birth);
		psmt.setString(4, addr);
		
		// 4단계 - sql 실행
		psmt.execute();		
		
		// 5단계 - 결과 처리 (select)
		
		// 6단계 - 데이터베이스 종료
		psmt.close();
		conn.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	// 목록 이동
	response.sendRedirect("./list.jsp");
%>