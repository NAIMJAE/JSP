<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	String parent = request.getParameter("parent");
	String content = request.getParameter("content");
	String writer = request.getParameter("writer");
	String regip = request.getRemoteAddr();

	ArticleDTO comment = new ArticleDTO();
	comment.setParent(parent);
	comment.setContent(content);
	comment.setWriter(writer);
	comment.setRegip(regip);
	
	ArticleDAO dao = ArticleDAO.getInstance();
	
	dao.insertComment(comment);
	
	response.sendRedirect("/Jboard1/view.jsp?no="+parent);
	
%>