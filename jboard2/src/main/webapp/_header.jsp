<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>글목록</title>
    <link rel="stylesheet" href="./css/style.css"/>    
</head>
<body>
    <div id="wrapper">
        <header>
            <h3>Board System v2.0</h3>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <p>
                        <span>${sessionScope.user.name}</span>님 반갑습니다.
                        <a href="#">[로그아웃]</a>
                    </p>
                </c:when>
                <c:otherwise>
                    <p>
                    	<a href="/jboard2/user/login.do">[로그인]</a>
                        <a href="/jboard2/user/register.do">[회원가입]</a>
                    </p>
                </c:otherwise>
            </c:choose>
        </header>