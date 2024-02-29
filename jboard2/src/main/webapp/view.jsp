<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./_header.jsp" %>
<script>
	window.onload = function(e) {
		
		const commentList = document.getElementsByClassName('commentList')[0];
		const btnRemove = document.getElementsByClassName('btnRemove')[0];
		const CommentRemove = commentList.getElementsByClassName('remove'); // ★
		const form = document.commentForm;
		const btnSubmit = document.commentForm.submit;
		const commentContent = document.getElementsByClassName('commentContent')[0];
		const CommentEmpty = document.getElementsByClassName('empty')[0];
		
		// 게시글 삭제
		if(btnRemove != null){
			btnRemove.addEventListener('click', function(e){
				e.preventDefault();
							
				let check = confirm('정말 삭제하시겠습니까?');
				
				if(check){
					let link = this.getAttribute('href');
			        window.location.href = link;
			        alert('게시글이 삭제되었습니다.');
				}
			});
		} // 게시글 삭제 끝
		
		// 댓글 삭제
		document.addEventListener('click', function(e) {
			
			// 이벤트가 발생 했을때!
			if(e.target.classList == 'remove'){
				e.preventDefault();
				// 해당 삭제 부모 artecle 문서 객체 생성
				const article = e.target.closest('article');
		        
		        // 사용자 정의 속성 data-no 참조
		        const no = e.target.dataset.no;
		        const parent = e.target.dataset.parent;
		        console.log('no : ' + no)
		        
		        fetch('/jboard2/comment.do?type=remove&no='+no+'&parent='+parent)
		        	.then((response) => response.json())
		        	.then((data) => {
		        		if(data.result > 0){
		        			alert('삭제되었습니다.');
		        			article.remove();
		         			// 댓글이 1개도 없을경우 '등록된 댓글이 없습니다.' 추가  			
		        			if (commentList.children.length === 1){ // h3태그가 남아 있음
		        				const empty = document.createElement('p');
		        				empty.className = 'empty';
		        				empty.textContent = '등록된 댓글이 없습니다.';
		        				commentList.appendChild(empty);
		        			}
		        		}
		        	})
		        	.catch((err) => {
						console.log(err);
					});
			}
		})
		// 댓글 삭제 끝
		
		// 댓글 작성
		btnSubmit.onclick = function(e){
			e.preventDefault();
			
			const parent = form.parent.value;
			const writer = form.writer.value;
			const content = form.content.value;
			
			const jsonData = {
				"parent": parent, 	
				"writer": writer, 	
				"content": content
			};
			
			console.log(jsonData);
			
			fetch('/jboard2/comment.do', {
					method: 'POST',
					headers: {"Content-type": "application/json"},
					body: JSON.stringify(jsonData)
				})
				.then((resp) => resp.json())
				.then((data) => {
					console.log(data);
					if(data.pk > 0){
						const today = new Date();
						const year = today.getFullYear();
						const month = today.getMonth()+1;
						const date = today.getDate();
						
						// 태그 동적 처리 (태그 문자열)
						let commentArticle = `<article>
												<span class='nick'>${sessUser.nick}</span>
												<span class='date'>\${year}-\${month}-\${date}</span>
												<p class='content'>\${content}</p>                      
												<div>
													<a href='#' data-no="\${data.pk}" data-parent="\${data.parent}" class='remove'>삭제 </a>
													<a href='#' class='modify'>수정</a>
												</div>
											</article>`;
							 
						console.log(commentArticle);
						
						commentContent.value = '';
						commentList.insertAdjacentHTML('beforeend', commentArticle);
					}
					// 여기 반응 안함
					if(commentList.children.length > 2){
						CommentEmpty.remove();
					}
					
					alert('댓글 작성이 완료되었습니다.');
			})
			.catch((err)=> {console.log(err)});
		};// 댓글 작성 끝
		
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
	                    <a href="/jboard2/deleteArticle.do?no=${articleDTO.no}" class="btn btnRemove">삭제</a>
	                    <a href="/jboard2/modify.do?no=${articleDTO.no}" class="btn btnModify">수정</a>
                    </c:if>
                    <a href="/jboard2/list.do" class="btn btnList">목록</a>
                </div>

                <!-- 댓글목록 -->
                <section class="commentList">
                    <h3>댓글목록</h3>                   
					<c:forEach var="comment" items="${comments}">
                    <article>
                        <span class="nick">${comment.nick}</span>
                        <span class="date">${comment.rdate}</span>
                        <p class="content">${comment.content}</p>                        
                        <div>
                            <a href="#" data-no="${comment.no}" data-parent="${comment.parent}" class="remove">삭제</a>
                            <a href="#" class="modify">수정</a>
                        </div>
                    </article>
					</c:forEach>
					<c:if test="${empty comments}">
                    	<p class="empty">등록된 댓글이 없습니다.</p>
					</c:if>
                </section>

                <!-- 댓글쓰기 -->
                <section class="commentForm">
                    <h3>댓글쓰기</h3>
                    <form action="#" name="commentForm" method="post">
                    	<input type="hidden" name="writer" value="${sessUser.uid}">
                    	<input type="hidden" name="parent" value="${articleDTO.no}">
                        <textarea name="content" class="commentContent"></textarea>
                        <div>
                            <a href="#" class="btn btnCancel">취소</a>
                            <input type="submit" name="submit" value="작성완료" class="btn btnComplete"/>
                        </div>
                    </form>
                </section>

            </section>
        </main>
<%@ include file="./_footer.jsp" %>