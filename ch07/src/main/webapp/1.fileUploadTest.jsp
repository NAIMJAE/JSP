<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>1.file Upload Test</title>
		<!-- 
			날짜 : 2024/02/19
			이름 : 박임재
			내용 : JSP 파일 업로드 실습
		 --> 
	</head>
	<body>
		<h3>1.파일 업로드</h3>
		
		<form action="./proc/fileUploadProc.jsp" method="post" enctype="multipart/form-data">
			<input type="text" name="title"><br>
			<input type="text" name="content"><br>
			<input type="file" name="file"><br>
			<input type="submit" value="전송"><br>
 		</form>
	</body>
</html>