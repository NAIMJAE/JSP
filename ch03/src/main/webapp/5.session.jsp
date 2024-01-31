<%@page import="sub1.UserDTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>5.session</title>
		<!-- 
			날짜 : 2024/01/31
			이름 : 박임재
			내용 : 내장객체 session 실습
			
			session
			- session은 최초 요청부터 마지막 요청까지의 전체 시간의 경과
			- 클라이언트에 대응되는 객체
			- 서버에 기록되는 클라이언트 명단(Session Table)
			- 서버는 각 클라이언트에 대한 고유 식별번호(SID)를 발급
		 -->
	</head>
	<body>
		<h3>5.session 내장 객체</h3>
		
		<h4>session ID 확인</h4>
		<p>웹브라우저마다 다른 클라이언트로 서버가 인식</p>
		<%= session.getId() %>
		
		
		<h4>session 설정</h4>
		<%
			String agent = request.getHeader("User-Agent");
		
			if(agent.contains("Edg")){
				UserDTO user = new UserDTO("a101", "김유신", 23, "부산");
				
				// session 값 설정
				session.setAttribute("user", user);
			}else{
				UserDTO user = new UserDTO("a102", "김춘추", 25, "서울");
				
				// session 값 설정
				session.setAttribute("user", user);
			}
			// session 값 반환
			UserDTO userDTO = (UserDTO) session.getAttribute("user");
		%>
		<p>
			아이디 : <%= userDTO.getUid() %><br>
			이름 : <%= userDTO.getName() %><br>
			나이 : <%= userDTO.getAge() %><br>
			주소 : <%= userDTO.getAddr() %><br>
		</p>
		
	</body>
</html>