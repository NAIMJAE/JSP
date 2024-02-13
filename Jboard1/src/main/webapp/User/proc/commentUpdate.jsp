<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String parent = request.getParameter("parent");
	String no = request.getParameter("no");
	String content = request.getParameter("content");
	
	ArticleDTO comment = new ArticleDTO();
	comment.setNo(no);
	comment.setContent(content);
	
	ArticleDAO dao = ArticleDAO.getInstance();
	dao.updateComment(comment);
	
	response.sendRedirect("/Jboard1/view.jsp?no="+parent);
%>