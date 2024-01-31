<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>4.application</title>
		<!-- 
			날짜 : 2024/01/31
			이름 : 박임재
			내용 : 내장객체 application 실습
			
			application
			- 현재 웹 애플리케이션을 실행하는 WAS(톰캣) 환경 객체
			- 서버 환경값(web.xml)을 설정하고 참조하는 기능 제공
		 -->
	</head>
	<body>
		<h3>4.application 내장객체</h3>
		
		<h4>서버 정보</h4>
		<p>
			<%= application.getServerInfo() %>	
		</p>
		
		<h4>파라미터 정보</h4>
		<p>
			PARAM1 : <%= application.getInitParameter("PARAM1") %><br>
			PARAM2 : <%= application.getInitParameter("PARAM2") %><br>
		</p>
		
		<h4>로그 정보</h4>
		<%
			application.log("4.application ... 로그1");
		%>
		
		<h4>시스템 자원 경로</h4>
		<p>
			파일경로 : <%= application.getRealPath("./application.jsp") %><br>
			톰캣을 이용해서 실제로 구동되는 애플리케이션의 경로
		</p>	
		
	</body>
</html>