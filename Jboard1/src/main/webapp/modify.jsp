<%@page import="kr.co.jaboard1.DTO.ArticleDTO"%>
<%@page import="kr.co.jaboard1.DAO.ArticleDAO"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	request.setCharacterEncoding("UTF-8");
	String no = request.getParameter("no");
	
	ArticleDAO dao = ArticleDAO.getInstance();
	
	// 글 조회
	ArticleDTO articles = dao.selectArticle(no);

%>

<%@ include file="./_header.jsp" %>
<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	    // 페이지가 로드될 때 실행될 함수
	    function pageFocus() {
			var contentFocus = document.getElementById('content');
			if (contentFocus) {
	            contentFocus.focus();
	            
	            const textLength = contentFocus.value.length;
	            contentFocus.selectionStart = textLength;
	            contentFocus.selectionEnd = textLength;
			}
    	}
	    pageFocus();
	});
</script>
	<main>
	    <section class="modify">
	       <h3>글수정</h3>
	        <form action="/Jboard1/User/proc/modifyProc.jsp" method="post">
	        	<input type="hidden" name="no" value="<%= articles.getNo()%>">
	            <table>
	                <tr>
		                <td>제목</td>
		                <td><input type="text" name="title" value="<%= articles.getTitle() %>"></td>
		            </tr>
	                <tr>
	                    <td>내용</td>
	                    <td><textarea id="content" name="content"><%= articles.getContent() %></textarea></td>
	                </tr>
	                <tr>
	                    <td>첨부</td>
	                    <td>
	                        <input type="file" name="file">
	                    </td>
	                </tr>
	            </table>
	            <div>
	                <a href="/Jboard1/view.jsp?no=<%= articles.getNo()%>" class="btnCancel">취소</a>
	                <input type="submit" class="btnWrite" value="수정완료">
	            </div>
	        </form>
	        
	    </section>
	</main>
<%@ include file="./_footer.jsp" %>