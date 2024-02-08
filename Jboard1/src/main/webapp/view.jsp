<%@page import="java.util.List"%>
<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	request.setCharacterEncoding("UTF-8");
	String no = request.getParameter("no");
	
	ArticleDAO dao = ArticleDAO.getInstance();
	
	// 글 조회
	ArticleDTO articles = dao.selectArticle(no);
	
	// 글 조회 카운트 업데이트
	dao.updateHitCount(no);
	
	// 댓글 조회
	List<ArticleDTO> comments = dao.selectComments(no);
%>
<%@ include file="./_header.jsp" %>
<script type="text/javascript">
	
	window.onload = function(){
		
		// 댓글 작성 취소 버튼
		const btnCancel = document.getElementsByClassName('btnCancel')[0];
		btnCancel.onclick = function(e){
			e.preventDefault();
			document.frmComment.reset();
		}
		// 댓글 삭제 버튼
		const del = document.querySelectorAll('.btnDelete');
		
		del.forEach((item)=>{
			
			item.onclick = function(){
				
				const result = confirm('정말 삭제 하시겠습니까?');
				
				if(result){
					return true;	
				}else{
					// 표준 이벤트 모델(addEventListener)은 작업취소 안됨
					return false;
				}
			}
		});
		
		// 댓글 수정
		
	}
</script>
	<main>
	    <section class="view">
	       <h3>글보기</h3>
	
	        <table>
	            <tr>
	                <td>제목</td>
	                <td><input type="text" name="title" value="<%= articles.getTitle() %>" readonly></td>
	            </tr>
	            <% if(articles.getFile() > 0){ %>
	            <tr>
	                <td>첨부파일</td>
	                <td>
	                    <a href="#"><%= articles.getFile() %></a>
	                    <span><%= articles.getFile() %>회 다운로드</span>
	                </td>
	            </tr>
	            <%} %>
	            <tr>
	                <td>내용</td>
	                <td>
	                    <textarea name="content" readonly><%= articles.getContent() %></textarea>
	                </td>
	            </tr>
	        </table>
	        <div>
	        	<% if(articles.getWriter().equals(sessUser.getUid())) {%>
	            <a href="/Jboard1/User/proc/deleteProc.jsp?no=<%= articles.getNo()%>" class="btnDelete">삭제</a>
	            <a href="./modify.jsp?no=<%= articles.getNo()%>" class="btnModify">수정</a>
	            <%} %>
	            <a href="./list.jsp?" class="btnList">목록</a>
	        </div>
	
	        <!--댓글 리스트-->
	        <section class="commentList">
	            <h3>댓글목록</h3>
	            <%for (ArticleDTO comment : comments) {%>
	            <article class="comment">
	                <span>
	                    <span><%=comment.getNick() %></span>
	                    <span><%=comment.getRdate() %></span>
	                </span>
	                <textarea name="comment" readonly><%=comment.getContent() %></textarea>
	                <% if(comment.getWriter().equals(sessUser.getUid())) { %>
	                <div>
	                    <a href="./User/proc/CommentDelete.jsp?no=<%= comment.getNo()%>&parent=<%= comment.getParent()%>" class="btnDelete">삭제</a>
	                    <a href="#" class="btnModify">수정</a>
	                </div>
	                <%} %>
	            </article>
	            <%} %>
	            
	            <% if(comments.isEmpty()) {%>
	            <p class="empty">
	                등록된 댓글이 없습니다.
	            </p>
	            <%} %>
	        </section>
	
	        <!--댓글입력폼-->
	        <section class="commentForm">
	            <h3>댓글쓰기</h3>
	            <form action="./User/proc/commentInsert.jsp" name="frmComment" method="post">
	            	<input type="hidden" name="parent" value="<%= no %>">
	            	<input type="hidden" name="writer" value="<%= sessUser.getUid() %>">
	            	
	                <textarea name="content"></textarea>
	                <div>
	                    <a href="#" class="btnCancel">취소</a>
	                    <input type="submit" class="btnWrite" value="작성완료">
	                </div>
	            </form>
	        </section>
	    </section>
	</main>
<%@ include file="./_footer.jsp" %>