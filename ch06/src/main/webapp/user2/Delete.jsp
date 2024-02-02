<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="ch06.User2DTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// db 정보 가져오기
	String host = "jdbc:mysql://127.0.0.1:3306/studydb";
	String user = "naimjae";
	String pass = "abc1234";
	
	// 정보 넘겨 받기
	request.setCharacterEncoding("UTF-8");
	String uid = request.getParameter("uid");
	
	User2DTO dto = null;
	
	// 
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(host, user, pass);
		PreparedStatement psmt = conn.prepareStatement("DELETE FROM `User2` WHERE `uid`=?");
		psmt.setString(1, uid);
		
		psmt.executeUpdate();
		
		conn.close();
		psmt.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	response.sendRedirect("./list.jsp");
%>