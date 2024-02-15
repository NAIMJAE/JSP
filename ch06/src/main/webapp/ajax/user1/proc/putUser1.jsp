<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@ page contentType="application/json;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String uid = request.getParameter("uid");
	String name = request.getParameter("name");
	String birth = request.getParameter("birth");
	String hp = request.getParameter("hp");
	String age = request.getParameter("age");

	System.out.println(uid);
	int result = 0;
	try{
		Context ctx = (Context) new InitialContext().lookup("java:comp/env");
		
		DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
		Connection conn = ds.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement("UPDATE `User1` SET `name`=?, `birth`=?, `hp`=?, `age`=? WHERE `uid`=?");
		psmt.setString(1, name);
		psmt.setString(2, birth);
		psmt.setString(3, hp);
		psmt.setString(4, age);
		psmt.setString(5, uid);
		
		result = psmt.executeUpdate();
		
		conn.close();
		psmt.close();
		
	}catch (Exception e){
		e.printStackTrace();
	}

	// JSON 출력
	JsonObject json = new JsonObject();
	json.addProperty("result", result);
	out.print(json.toString());
%>