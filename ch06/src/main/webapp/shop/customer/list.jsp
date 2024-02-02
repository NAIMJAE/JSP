<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>customer::list</title>
	</head>
	<body>
		<h3>customer 목록</h3>
		
		<a href="../../2.DBCP.jsp">처음으로</a>
		<a href="./register.jsp">등록하기</a>
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>휴대폰</th>
				<th>주소</th>
				<th>등록일</th>
				<th>관리</th>
			</tr>
			<tr>
				<th>a101</th>
				<th>홍홍</th>
				<th>010-1234-1234</th>
				<th>부산</th>
				<th>2020-20-20</th>
				<th>
					<a href="./Modify.jsp">수정</a>
					<a href="./Delete.jsp">삭제 </a>
				</th>
			</tr>
		</table>
	</body>
</html>