<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="ch06.User2DTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// db 정보 가져오기
	String host = "jdbc:mysql://127.0.0.1:3306/studydb";
	String user = "naimjae";
	String pass = "abc1234";
	
	// db 정보 출력할 list 만들기
	List<User2DTO> users2 = new ArrayList<>();
	
	// 
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(host, user, pass);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `User2`");
		
		while(rs.next()) {
			
			User2DTO dto = new User2DTO();
			dto.setUid(rs.getString(1));
			dto.setName(rs.getString(2));
			dto.setBirth(rs.getString(3));
			dto.setAddr(rs.getString(4));
			
			users2.add(dto);
		}
		rs.close();
		conn.close();
		stmt.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User2::list</title>
	</head>
	<body>
		<h3>User2 목록</h3>
		
		<a href="../1.Jdbc.jsp">처음으로</a>
		<a href="./register.jsp">등록하기</a>
		
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>생년월일</th>
				<th>주소</th>
				<th>관리</th>
			</tr>
			<% for(User2DTO dto : users2) { %>
			<tr>
				<th><%=dto.getUid() %></th>
				<th><%=dto.getName() %></th>
				<th><%=dto.getBirth() %></th>
				<th><%=dto.getAddr() %></th>
				<th>
					<a href="./Modify.jsp?uid=<%=dto.getUid()%>">수정</a>
					<a href="./Delete.jsp?uid=<%=dto.getUid()%>">삭제</a>
				</th>
			</tr>
			<%} %>
		</table>
	</body>
</html>