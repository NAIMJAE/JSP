<%@page import="kr.co.jaboard1.DAO.UserDAO"%>
<%@page import="kr.co.jaboard1.DTO.UserDTO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mysql.cj.protocol.Resultset"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="kr.co.jaboard1.db.sql"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String uid = request.getParameter("uid");
	String pass = request.getParameter("pass");
	
	UserDTO user = UserDAO.getInstance().selectUserForLogin(uid, pass);
	
	// 회원 체크
	if (user != null) {
		// 회원일 경우 (회원 세션 저장)
		session.setAttribute("sessUser", user);
		// 글목록으로 이동
		response.sendRedirect("/Jboard1/list.jsp");
		
	}else {
		// 회원이 아닐 경우
		response.sendRedirect("/Jboard1/User/login.jsp?code=100");
	}
%>