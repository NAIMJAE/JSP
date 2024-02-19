<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.mysql.cj.protocol.Resultset"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ch07.dto.FileDTO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<FileDTO> files = new ArrayList<>();
	
	try{
		//1 단계	- JNDI 서비스 객체 생성
		Context initCtx = new InitialContext();
		Context ctx = (Context) initCtx.lookup("java:comp/env"); // JNDI 기본 환경 이름
		
		//2 단계 - 커넥션 가져오기
		DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
		Connection conn = ds.getConnection();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `FileTest` ORDER BY `rdate` DESC");
		
		while(rs.next()){
			FileDTO file = new FileDTO();
			file.setFno(rs.getInt(1));
			file.setTitle(rs.getString(2));
			file.setoName(rs.getString(3));
			file.setsName(rs.getString(4));
			file.setRdate(rs.getString(5));
			
			files.add(file);
		}
		conn.close();
		stmt.close();
	}catch (Exception e){
		e.printStackTrace();
	}

%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>2.file Download Test</title>
		<script type="text/javascript">
			window.onload = function() {
				
				const btnDel = document.getElementById('btnDel');
				
				btnDel.addEventListener ('click', function(){
					const check = confirm('정말 삭제하시겠습니까?');
					
					if (!check) {
		            	event.preventDefault();
					}
				})
				
			}
		</script>
	</head>
	<body>
		<h3>2.파일 다운로드</h3>
		
		<table border="1">
			<tr>
				<td>번호</td>
				<td>제목</td>
				<td>원본파일명</td>
				<td>저장파일명</td>
				<td>날짜</td>
				<td>관리</td>
				<td>삭제</td>
			</tr>

			<%for(FileDTO file : files){ %>			
			<tr>
				<td><%=file.getFno() %></td>	
				<td><%=file.getTitle() %></td>	
				<td><%=file.getoName() %></td>	
				<td><%=file.getsName() %></td>	
				<td><%=file.getRdate() %></td>	
				<td><a href="./proc/fileDownloadProc.jsp?fno=<%=file.getFno()%>">다운로드</a></td>	
				<td><a id="btnDel" href="./proc/fileDeleteProc.jsp?sName=<%= file.getsName() %>">삭제</a></td>	
			</tr>
			<%} %>
		</table>
	</body>
</html>