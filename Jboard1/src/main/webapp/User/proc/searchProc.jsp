<%@page import="java.util.List"%>
<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String searchType = request.getParameter("searchType");
	String keyword = request.getParameter("keyword");
	String no = request.getParameter("no");
	
	ArticleDTO search = new ArticleDTO();
	search.setSearchType(searchType);
	search.setKeyword(keyword);
	search.setNo(no);

	List<ArticleDTO> searchs = ArticleDAO.getInstance().selectSearchArticle(search);
		
	
%>