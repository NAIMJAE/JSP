<%@page import="com.google.gson.Gson"%>
<%@page import="ch06.User1DTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page contentType="application/json;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<User1DTO> user1s = new ArrayList<>();

	try{
		Context ctx = (Context) new InitialContext().lookup("java:comp/env");
		
		DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
		Connection conn = ds.getConnection();
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `User1`");
		
		while(rs.next()) {
			User1DTO user1 = new User1DTO();
			user1.setUid(rs.getString(1));
			user1.setName(rs.getString(2));
			user1.setBirth(rs.getString(3));
			user1.setHp(rs.getString(4));
			user1.setAge(rs.getInt(5));
			user1s.add(user1);
		}
		
		conn.close();
		stmt.close();
		rs.close();
		
	}catch (Exception e){
		e.printStackTrace();
	}

	// JSON 출력
	Gson gson = new Gson(); // Gson 객체 생성 (직렬화 : java객체->JSON문자열 / 역질렬화 : JSON문자열->java객체)
	String strJson = gson.toJson(user1s); // toJson 메서드를 이용해 JSON문자열로 변환
	out.print(strJson); // 출력 스트림 out으로 클라이언트에 전송 (JSON 형식으로)
	
	
%>