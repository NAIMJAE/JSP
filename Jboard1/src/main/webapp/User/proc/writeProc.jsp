<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String file = request.getParameter("file");
	String writer = request.getParameter("writer");
	String regip = request.getRemoteAddr();
	
	ArticleDTO article = new ArticleDTO();
	article.setTitle(title);
	article.setContent(content);
	article.setWriter(writer);
	article.setRegip(regip);
	
	ArticleDAO.getInstance().insertArticle(article);
	
	response.sendRedirect("/Jboard1/list.jsp");
	
%>