<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<ArticleDTO> articles = ArticleDAO.getInstance().selectArticles();
%>
<%@ include file="./_header.jsp" %>
	<main>
	    <section class="list">
	        <h3>글 목록</h3>
	        <article>
	            <table>
	                <tr>
	                    <th>번호</th>
	                    <th>제목</th>
	                    <th>글쓴이</th>
	                    <th>날짜</th>
	                    <th>조회</th>
	                </tr>
	                <% for (ArticleDTO article : articles) {%>
					<tr>
						<td><%= article.getNo() %></td>
						<td><a href="./view.jsp?no=<%= article.getNo()%>"><%= article.getTitle() %></a>[<%= article.getComment()%>]</td>
						<td><%= article.getWriter()%></td>
						<td><%= article.getRdate()%></td>
						<td><%= article.getHit()%></td>
					</tr>
					<% } %>
	            </table>
	        </article>
	        <!--페이지 네비게이션-->
	        <div class="paging">
	            <a href="#" class="prev">이전</a>
	            <a href="#" class="num_current">1</a>
	            <a href="#" class="num">2</a>
	            <a href="#" class="num">3</a>
	            <a href="#" class="num">4</a>
	            <a href="#" class="num">5</a>
	            <a href="#" class="next">다음</a>
	        </div>
	        <div>
	            <a href="/Jboard1/write.jsp" class="btnWrite">글쓰기</a>
	        </div>
	    </section>
	</main>
<%@ include file="./_footer.jsp" %>