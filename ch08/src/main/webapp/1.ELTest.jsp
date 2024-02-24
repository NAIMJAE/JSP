<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>1.Expression Language</title>
		<!-- 
			날짜 : 2024/02/20
			이름 : 박임재
			내용 : JSP 표현언어 실습
		 -->
	</head>
	<body>
		<h3>1.표현언어</h3>
		
		<%
			int num1 = 1;
			int num2 = 2;
			
			String str1 = "hello";
			String str2 = "welcome";
			String str3 = "";
			String str4 = null;
			
			// 표현 언어로 출력하기 위해 내장 객체 스코프 저장
			pageContext.setAttribute("num1", num1);
			request.setAttribute("num2", num2);
			session.setAttribute("str1", str1);
			application.setAttribute("str2", str2);
			
			request.setAttribute("str3", str3);
			request.setAttribute("str4", str4);
		%>

		<h4>표현식</h4>
		<p>
			num1 : <%=num1 %><br>
			num2 : <%=num2 %><br>
			str1 : <%=str1 %><br>
			str2 : <%=str2 %><br>
		</p>
		
		<h4>표현언어</h4>
		<p>
			num1 : ${num1 }<br>
			num2 : ${num2 }<br>
			str1 : ${str1 }<br>
			str2 : ${str2 }<br>
		</p>
		
		<h4>표현 언어 내장 객체</h4>
		<p> <!-- 이름이 같으면 가장 작은 스코프 참조 -->
			num1 : ${pageScope.num1 }<br>
			num2 : ${requestScope.num2 }<br>
			str1 : ${sessionScope.str1 }<br>
			str2 : ${applicationScope.str2 }<br>
		</p>
		<h4>연산자</h4>
		 <p>
		 	num1 + num2 = ${num1 + num2}<br>
		 	num1 - num2 = ${num1 - num2}<br>
		 	num1 / num2 = ${num1 * num2}<br>
		 	num1 / num2 = ${num1 / num2}<br>
		 	num1 % num2 = ${num1 % num2}<br><br>

		 	num1 > num2 = ${num1 > num2}<br>
		 	num1 < num2 = ${num1 < num2}<br>
		 	num1 >= num2 = ${num1 >= num2}<br>
		 	num1 <= num2 = ${num1 <= num2}<br>
		 	num1 == num2 = ${num1 == num2}<br>
		 	num1 != num2 = ${num1 != num2}<br><br>
		 	
		 	<!-- (Greater than) num1이 num2보다 크다 -->
		 	num1 gt num2 = ${num1 gt num2}<br>
		 	<!-- (less than) num1이 num2보다 작다 -->
		 	num1 lt num2 = ${num1 lt num2}<br>
		 	<!-- (Greater than or equal to) num1이 num2와 같거나 크다 -->
		 	num1 ge num2 = ${num1 ge num2}<br>
		 	<!-- (less than or equal to) num1이 num2와 같거나 작다 -->
		 	num1 le num2 = ${num1 le num2}<br>
		 	<!-- (equal to) num1이 num2와 같다 -->
		 	num1 eq num2 = ${num1 eq num2}<br>
		 	<!-- (not equal to) num1이 num2와 같지 않다 -->
		 	num1 ne num2 = ${num1 ne num2}<br>
		 	
		 	empty str1 = ${empty str1 }<br>
		 	empty str2 = ${empty str2 }<br>
		 	empty str3 = ${empty str3 }<br>
		 	empty str4 = ${empty str4 }<br>
		 	str1 eq "hello" = ${str1 eq "hello"}<br>
		 </p>
		 
		
	</body>
</html>