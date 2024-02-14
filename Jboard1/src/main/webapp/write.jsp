<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	request.setCharacterEncoding("UTF-8");
	String no = request.getParameter("no");
	String pg = request.getParameter("pg");
	String searchType = request.getParameter("searchType");
	String keyword = request.getParameter("keyword");
	
	String params = "";
	String params2 = "";
	
	if((searchType == null || searchType.isEmpty()) && (keyword == null || keyword.isEmpty())){
		// 글 조회
		params = "pg="+pg;
	}else{
		// 동적 파라미터 생성
		params = "searchType="+searchType+"&keyword="+keyword+"&pg="+pg;
		params2 = "searchType="+searchType+"&keyword="+keyword;
	}
%>
<%@ include file="./_header.jsp" %>
	<main>
	    <section class="write">
	       <h3>글쓰기</h3>
	        <form action="/Jboard1/User/proc/writeProc.jsp" method="post">
	        	<input type="hidden" name="writer" value="<%= sessUser.getUid()%>">
	            <table>
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
	                <a href="/Jboard1/list.jsp?<%=params%>" class="btnCancel">취소</a>
	                <input type="submit" class="btnWrite" value="작성완료">
	            </div>
	        </form>
	        
	    </section>
	</main>
<%@ include file="./_footer.jsp" %>