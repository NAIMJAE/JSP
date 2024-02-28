<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./_header.jsp" %>
<script>
	window.onload = function() {
	// btnFileDelete 버튼 클릭시 
		const btnFileDeletes = document.getElementsByClassName('btnFileDelete');
		
		Array.from(btnFileDeletes).forEach(btnFileDelete => {
		    btnFileDelete.onclick = function(e) {
		        e.preventDefault();
		        
		        let check = confirm('선택한 첨부파일을 삭제하시겠습니까?');
		        var parent = this.parentNode;
		        var fno = this.dataset.fno;
		        console.log(fno);
		        if(check) {
		            fetch("/jboard2/fileDelete.do?no="+fno)
		            .then((response)=>response.json())
		            .then((data)=>{
		                if(data.success > 0){
		                    console.log(data.success);
		                    parent.remove();
		                    alert('첨부파일이 삭제되었습니다.');
		                }else{
		                    alert('해당파일을 찾을 수 없습니다.');
		                }
		            })
		            .catch((err)=>{
		                console.log(err);
		            });
		        }
		    }
		});
	// - 정말 삭제하시겠습니까 물어보기
	// - fetch 이용해서 deleteFile 수행하기 (fno값으로 delete)
	// - 파일 삭제하면서 업로드된 파일도 삭제 
	// - 파일 삭제하면서 게시글에 있는 file 갯수도 감소 시키기 (no값으로 update)
	// - 파일 td도 지우기
	// - 삭제 완료되면 정상적으로 삭제되었습니다 메세지 띄우기		
	}
</script>
        <main id="board">
            <section class="modify">

                <form action="/jboard2/modify.do?no=${article.no}" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="writer" value="${sessionScope.sessUser.uid}"/>
                    <table>
                        <caption>글수정</caption>
                        <tr>
                            <th>제목</th>
                            <td><input type="text" name="title" value="${article.title}"/></td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td>
                                <textarea name="content">${article.content}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <th>업로드된 파일</th> <!-- 스크립트로 파일 삭제 처리 -->
                            <td>
		                		<c:choose>
				                	<c:when test="${article.file > 0 }">
					                	<c:forEach var="file" items="${article.fileDTOs}">
					                	<p>${file.oName}&nbsp;
					                		<a href="#" class="btn btnFileDelete" data-fno="${file.fno}">삭제</a>
					                	</p>
					                	</c:forEach>
				                	</c:when>
				                	<c:otherwise>
				                		<p>파일 없음</p>
				                	</c:otherwise>
				                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>새 파일 첨부</th>
                            <td>
                                <input type="file" name="fname1" multiple="multiple" maxlength="2"/>
                            </td>
                        </tr>
                    </table>
                    
                    <div>
                        <a href="/jboard2/view.do?no=${article.no}" class="btn btnCancel">취소</a>
                        <input type="submit" value="작성완료" class="btn btnComplete"/>
                    </div>
                </form>

            </section>
        </main>
<%@ include file="./_footer.jsp" %>