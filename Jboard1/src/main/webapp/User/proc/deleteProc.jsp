<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 글 번호 받아오기
	request.setCharacterEncoding("UTF-8");
	
	String no = request.getParameter("no");
	
	// 글 삭제하기
	ArticleDAO.getInstance().deleteArticle(no);
	
	response.sendRedirect("/Jboard1/list.jsp");
%>