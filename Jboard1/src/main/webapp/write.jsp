<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./_header.jsp" %>
	<main>
	    <section class="write">
	       <h3>글쓰기</h3>
	        <form action="#">
	            <table border="0">
	                <tr>
	                    <td>제목</td>
	                    <td><input type="text" name="title" value="제목을 입력하세요."></td>
	                </tr>
	                <tr>
	                    <td>내용</td>
	                    <td><textarea name="content"></textarea></td>
	                </tr>
	                <tr>
	                    <td>첨부</td>
	                    <td>
	                        <input type="file" name="file">
	                    </td>
	                </tr>
	            </table>
	            <div>
	                <a href="#" class="btnCancel">취소</a>
	                <a href="#" class="btnWrite">작성완료</a>
	            </div>
	        </form>
	        
	    </section>
	</main>
<%@ include file="./_footer.jsp" %>