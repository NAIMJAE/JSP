<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./_header.jsp" %>
<script>
	window.onload = function(e) {
		const btnRemove = document.getElementsByClassName('btnRemove')[0];
		
		btnRemove.onclick = function(e){
			e.preventDefault();
			
			let check = confrim('정말 삭제하시겠습니까?');
			
			// 게시글 번호 no -> 게시글, file테이블, upload 폴더 삭제
			if(check){
				fetch("/jboard2/deleteArticle.do")
				.then((rsponse)=>rsponse.json())
				.then((data)=>{
					// 제대로 삭제되었는지 data로 확인하고 
					// 여기서 삭제되었습니다 띄우고 list로 이동
				})
				.catch((err)=>console.log(err))				
			}
		}
	}
</script>
        <main id="board">
            <section class="view">
                
                <table>
                    <caption>글보기</caption>
                    <tr>
                        <th>제목</th>
                        <td><input type="text" name="title" value="${articleDTO.getTitle()}" readonly/></td>
                    </tr>
                    <tr>
                        <th>파일</th>
		                <td>
		                <c:choose>
		                	<c:when test="${articleDTO.file > 0 }">
			                	<c:forEach var="file" items="${articleDTO.fileDTOs}">
			                	<p><a href="/jboard2/fileDownload.do?fno=${file.fno}">${file.oName}</a>&nbsp;<span>${file.download}</span>회 다운로드</p>
			                	</c:forEach>
		                	</c:when>
		                	<c:otherwise>
		                		<p><a href="#"></a>&nbsp;<span></span></p>
		                	</c:otherwise>
		                </c:choose>
		                </td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td>
		                    <textarea name="content" readonly>${articleDTO.content}</textarea>
		                </td>
                    </tr>                    
                </table>
                
                <div>
                	<c:if test="${articleDTO.writer == sessUser.uid}">
	                    <a href="/jboard2/delete.do?no=${articleDTO.no}" class="btn btnRemove">삭제</a>
	                    <a href="/jboard2/modify.do?no=${articleDTO.no}" class="btn btnModify">수정</a>
                    </c:if>
                    <a href="/jboard2/list.do" class="btn btnList">목록</a>
                </div>

                <!-- 댓글목록 -->
                <section class="commentList">
                    <h3>댓글목록</h3>                   

                    <article>
                        <span class="nick">길동이</span>
                        <span class="date">20-05-20</span>
                        <p class="content">댓글 샘플 입니다.</p>                        
                        <div>
                            <a href="#" class="remove">삭제</a>
                            <a href="#" class="modify">수정</a>
                        </div>
                    </article>

                    <p class="empty">등록된 댓글이 없습니다.</p>

                </section>

                <!-- 댓글쓰기 -->
                <section class="commentForm">
                    <h3>댓글쓰기</h3>
                    <form action="#">
                        <textarea name="content"></textarea>
                        <div>
                            <a href="#" class="btn btnCancel">취소</a>
                            <input type="submit" value="작성완료" class="btn btnComplete"/>
                        </div>
                    </form>
                </section>

            </section>
        </main>
<%@ include file="./_footer.jsp" %>