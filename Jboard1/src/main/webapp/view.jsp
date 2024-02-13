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
		// 원글 수정 버튼
		const btnModify = document.querySelector('.btnModify');
		
		if(btnModify != null){			
			btnModify.onclick = ()=>{
				if(confirm('수정 하시겠습니까?')){
					return true;
				}else{
					return false;
				}
			}
		}
		
		// 원글삭제
		const btnDelete = document.querySelector('.btnDelete');
		
		btnDelete.onclick = () => {
			if(confirm('정말 삭제 하시겠습니까?')){
				return true;
			}else{
				return false;
			}
		}
		
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
		const mod = document.querySelectorAll('.mod');
		mod.forEach((item)=>{
			item.onclick = function(e){
				e.preventDefault(); // a태그 해제
				
				if(this.innerText == '수정') {
					// 수정 모드 전환
					this.innerText = '수정완료';
					const textarea = this.parentElement.previousElementSibling; // 부모 선택 + 이전 형제 선택
					textarea.readOnly = false;
					textarea.style.background = 'white';
					textarea.style.border = '1px solid black';
					
					// 포커스를 문장의 제일 뒤로 설정
					textarea.focus();
					const textLength = textarea.value.length;
					textarea.selectionStart = textLength;
					textarea.selectionEnd = textLength;
				}else{
					// 수정 완료 클릭
					const form = this.closest('form'); // 상위 노드에서 가장 가까운 태그 찾기
					form.submit();
					
					// 수정 모드 해제
					this.innerText = '수정';
					const textarea = this.parentElement.previousElementSibling; // 부모 선택 + 이전 형제 선택
					textarea.readOnly = true;
					textarea.style.background = 'transparent';
					textarea.style.border = 'none';
				}
			}
		});
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
	            <form action="/Jboard1/User/proc/commentUpdate.jsp" method="post">
	            <input type="hidden" name="no" value="<%= comment.getNo()%>">
	            <input type="hidden" name="parent" value="<%= comment.getParent()%>">
	            <article class="comment">
	                <span>
	                    <span><%=comment.getNick() %></span>
	                    <span><%=comment.getRdate() %></span>
	                </span>
	                <textarea name="content" readonly><%=comment.getContent() %></textarea>
	                
	                <% if(comment.getWriter().equals(sessUser.getUid())) { %>
	                <div>
	                    <a href="./User/proc/CommentDelete.jsp?no=<%= comment.getNo()%>&parent=<%= comment.getParent()%>" class="btnDelete">삭제</a>
	                    <a href="#" class="mod btnModify">수정</a>
	                </div>
	                <%} %>
	            </article>
	            </form>
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