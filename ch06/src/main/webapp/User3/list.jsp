<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ch06.User3DTO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	String host = "jdbc:mysql://127.0.0.1:3306/studydb";
	String user = "naimjae";
	String pass = "abc1234";

	List<User3DTO> users3 = new ArrayList<>();
	
	try {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(host, user, pass);
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `User3`");
				
		
		while(rs.next()) {
			User3DTO dto = new User3DTO();
			dto.setUid(rs.getString(1));
			dto.setName(rs.getString(2));
			dto.setBirth(rs.getString(3));
			dto.setHp(rs.getString(4));
			dto.setAddr(rs.getString(5));
			
			users3.add(dto);
		}
		
		conn.close();
		rs.close();
		stmt.close();
		
	}catch (Exception e) {
		e.printStackTrace();
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User3::list</title>
	</head>
	<body>
		<h3>User3 list</h3>
		<a href="../1.Jdbc.jsp">처음으로</a>
		<a href="./register.jsp">등록하기</a>
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>생일</th>
				<th>전화번호</th>
				<th>주소</th>
				<th>관리</th>
			</tr>
			<% for(User3DTO dto : users3) { %>
			<tr>
				<th><%=dto.getUid() %></th>
				<th><%=dto.getName() %></th>
				<th><%=dto.getBirth() %></th>
				<th><%=dto.getHp() %></th>
				<th><%=dto.getAddr() %></th>
				<th>
					<a href="#" >수정</a>
					<a href="#" >삭제</a>
				</th>
			</tr>
			<%} %>
		</table>
	</body>
</html>