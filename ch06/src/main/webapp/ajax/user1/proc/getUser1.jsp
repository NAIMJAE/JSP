<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="ch06.User1DTO"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%

	request.setCharacterEncoding("UTF-8");

	String uid = request.getParameter("uid");

	Context ctx = (Context) new InitialContext().lookup("java:comp/env");
	DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
	Connection conn = ds.getConnection();
	
	PreparedStatement psmt =  conn.prepareStatement("SELECT * FROM `User1` WHERE `uid`=?");
	psmt.setString(1, uid);
	
	ResultSet rs = psmt.executeQuery();
	
	User1DTO user1 = null;
	
	if(rs.next()) {
		user1 = new User1DTO();
		user1.setUid(rs.getString(1));
		user1.setName(rs.getString(2));
		user1.setBirth(rs.getString(3));
		user1.setHp(rs.getString(4));
		user1.setAge(rs.getInt(5));
	}
	rs.close();
	conn.close();
	psmt.close();
	
	// JSON 출력
	Gson gson = new Gson();
	String strJson = gson.toJson(user1);
	out.print(strJson);
%>
