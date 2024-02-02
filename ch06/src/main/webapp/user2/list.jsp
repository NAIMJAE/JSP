<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
			<tr>
				<th>A10101</th>
				<th>홍길동</th>
				<th>1222-55-54</th>
				<th>부산</th>
				<th>
					<a href="#">수정</a>
					<a href="#">삭제</a>
				</th>
			</tr>
		</table>
	</body>
</html>