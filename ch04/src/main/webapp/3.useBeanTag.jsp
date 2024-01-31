<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>3.useBeanTag</title>
		<!-- 
			날짜 : 2024/01/31
			이름 : 박임재
			내용 : JSP 액션태그 useBean 실습
		 -->
	</head>
	<body>
		<h3>3.useBeanTag</h3>
		
		<form action="./proc/userProc.jsp" method="get">
			<input type="text" name="uid"><br>
			<input type="text" name="name"><br>
			<input type="text" name="age"><br>
			<input type="text" name="addr"><br>
			<input type="submit" name="전송">
		</form>
	</body>
</html>