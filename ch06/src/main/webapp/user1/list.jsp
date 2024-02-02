<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="ch06.User1DTO"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%

	String host = "jdbc:mysql://127.0.0.1:3306/studydb";
	String user = "naimjae";
	String pass = "abc1234";
	
	List<User1DTO> users = new ArrayList<>(); 
	//
	try {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(host, user, pass);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `User1`");
		
		while(rs.next()) {
			
			User1DTO dto = new User1DTO();
			dto.setUid(rs.getString(1));
			dto.setName(rs.getString(2));
			dto.setBirth(rs.getString(3));
			dto.setHp(rs.getString(4));
			dto.setAge(rs.getInt(5));
			
			users.add(dto);
		}
		
		rs.close();
		conn.close();
		stmt.close();
		
	}catch(Exception e) {
		e.printStackTrace();
	}

%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>user1::list</title>
		<style>
		* {
			font: normal 16px '고딕';
		}
		table {
		}
		table tr {
		}
		table tr > th {
			width: 100px;
		}
		table tr > th:nth-child(4) {
			width: 200px;
		} 
		</style>
	</head>
	<body>
		<h3>user1 목록</h3>
		
		<a href="../1.Jdbc.jsp">처음으로</a>
		<a href="./register.jsp">등록하기</a>
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>생년월일</th>
				<th>휴대폰</th>
				<th>나이</th>
				<th>관리</th>
			</tr>
			<% for(User1DTO dto : users) { %>
			<tr>
				<th><%= dto.getUid() %></th>
				<th><%= dto.getName() %></th>
				<th><%= dto.getBirth() %></th>
				<th><%= dto.getHp() %></th>
				<th><%= dto.getAge() %></th>
				<th>
					<a href="./Modify.jsp?uid=<%= dto.getUid()%>">수정</a> <!-- 파라미터로 정보 넘기기 -->
					<a href="./Delete.jsp?uid=<%= dto.getUid()%>">삭제</a>
				</th>
			</tr>
			<%} %>
		</table>
		
	</body>
</html>