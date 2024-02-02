<%@page import="ch06.User1DTO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 수정 데이터 조회
	String host = "jdbc:mysql://127.0.0.1:3306/studydb";
	String user = "naimjae";
	String pass = "abc1234";
	
	request.setCharacterEncoding("UTF-8");
	String uid = request.getParameter("uid");
	
	User1DTO dto = null;
	
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(host, user, pass);
		PreparedStatement psmt = conn.prepareStatement("DELETE FROM `User1` WHERE `uid`=?");
		psmt.setString(1, uid);
		
		psmt.executeUpdate();

		conn.close();		
		psmt.close();
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	response.sendRedirect("./list.jsp");
%>
