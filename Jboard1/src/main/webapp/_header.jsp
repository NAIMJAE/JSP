<%@page import="kr.co.jaboard1.DTO.UserDTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 사용자 세션 열기
	UserDTO sessUser = (UserDTO) session.getAttribute("sessUser");

	// 로그인을 하지 않았을 경우
	if(sessUser == null ) {
		response.sendRedirect("/Jboard1/User/login.jsp?code=102");
		return;
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>list</title>
    <link rel="stylesheet" href="./CSS/style.css">
    <style>

    </style>
</head>
<body>
    <div id="container">
        <header>
            <h3>Jboard v1.0</h3>
            <p>
                <%= sessUser.getNick() %>님 반갑습니다.
                <a href="/Jboard1/User/proc/logout.jsp">[로그아웃]</a>
            </p>
        </header>