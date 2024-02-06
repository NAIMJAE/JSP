<%@page import="kr.co.jaboard1.DTO.UserDTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	UserDTO sessUser = (UserDTO) session.getAttribute("sessUser");

	// 로그인시 -> list.jsp / 비로그인시 -> login.jsp
	if (sessUser == null) {
		//foward는 시스템내에서 자원 이동이기 때문에 서버경로 대신 상대경로 지정
		pageContext.forward("./User/login.jsp");
	}else {
		pageContext.forward("./list.jsp");
	}
%>