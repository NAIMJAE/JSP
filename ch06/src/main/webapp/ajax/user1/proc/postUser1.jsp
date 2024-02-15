<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="ch06.User1DTO"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	// request Body JSON 문자열 스트림 처리
	
	BufferedReader reader = request.getReader();
	StringBuilder requestBody = new StringBuilder();
	String line;
	while ((line = reader.readLine()) != null) {
		
		requestBody.append(line);
		
	}
	
	reader.close();
	
	// JSON 파싱
	Gson gson = new Gson();
	User1DTO user1 = gson.fromJson(requestBody.toString(), User1DTO.class);
	
	System.out.println(user1);
	
	// 
	try{
		Context ctx = (Context) new InitialContext().lookup("java:comp/env");
		DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
		Connection conn = ds.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement("INSERT INTO `User1` VALUES (?,?,?,?,?)");
		psmt.setString(1, user1.getUid());
		psmt.setString(2, user1.getName());
		psmt.setString(3, user1.getBirth());
		psmt.setString(4, user1.getHp());
		psmt.setInt(5, user1.getAge());
		
		int result = psmt.executeUpdate(); // executeUpdate의 리턴 값 rowCount(성공 1, 실패 0)
		System.out.println(result);
		
		psmt.close();
		conn.close();
		
		// JSON 출력
		JsonObject json = new JsonObject(); // Gson 라이브러리의 JsonObject 클래스를 사용하여 새로운 JSON 객체를 생성
		json.addProperty("result", result);
		out.print(json.toString()); // JSON 형식으로 변환된 결과를 클라이언트에게 전송
		
	}catch (Exception e){
		e.printStackTrace();
	}
	
%>