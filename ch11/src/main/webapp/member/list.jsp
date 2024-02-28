<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Member List</title>
	</head>
	<body>
		<h3>Member 목록</h3>
		
		<a href="/ch11/index.jsp">처음으로</a>
		<a href="/ch11/member/register.do">등록하기</a>
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>연락처</th>
				<th>직급</th>
				<th>부서</th>
				<th>입사일</th>
				<th>관리</th>
			</tr>
			<c:forEach var="members" items="${members}">
			<tr>
				<th>${members.getUid()}</th>
				<th>${members.getName()}</th>
				<th>${members.getHp()}</th>
				<th>${members.getPos()}</th>
				<th>${members.getDep()}</th>
				<th>${members.getRdate()}</th>
				<th>
					<a href="/ch11/member/modify.do?uid=${members.getUid()}">수정</a>
					<a href="/ch11/member/delete.do?uid=${members.getUid()}">삭제</a>
				</th>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>