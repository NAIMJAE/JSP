<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 글 수정 값 받아오기
	request.setCharacterEncoding("UTF-8");

	int no = Integer.parseInt(request.getParameter("no"));
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	// 글 수정하기
	ArticleDTO article = new ArticleDTO();
	article.setNo(no);
	article.setTitle(title);
	article.setContent(content);
	
	ArticleDAO dao = ArticleDAO.getInstance();
	dao.updateArticle(article);
	
	response.sendRedirect("../../view.jsp?no="+article.getNo());
%>