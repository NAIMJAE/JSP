<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String parent = request.getParameter("parent");
	String no= request.getParameter("no");
	
	ArticleDAO dao = ArticleDAO.getInstance();
	dao.deleteComment(parent, no);

	response.sendRedirect("/Jboard1/view.jsp?no=" + parent);
%>