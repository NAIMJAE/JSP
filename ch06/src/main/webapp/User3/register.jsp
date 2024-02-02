<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User3::register</title>
	</head>
	<body>
		<h3>User3 register</h3>
		<a href="../1.Jdbc.jsp">처음으로</a>
		<a href="./list.jsp">목록보기</a>
		<form action="./registerProc.jsp">
			<table border="1">
			<tr>
				<td>아이디</td>
				<td><input type="text" name="uid" placeholder="영문과 숫자 조합" required ></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" placeholder="이름을 입력해주세요" required ></td>
			</tr>
			<tr>
				<td>생일</td>
				<td><input type="date" name="birth"></td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type="text" name="hp" placeholder="010-0000-0000"></td>
			</tr>
			<tr>
				<td>주소</td>
				<td><input type="text" name="addr"></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="등록하기"></td>
			</tr>
		</table>
		</form>
	</body>
</html>