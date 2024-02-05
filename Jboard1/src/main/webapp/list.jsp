<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
	                <tr>
	                    <td>1</td>
	                    <td><a href="#">테스트 제목1입니다.</a>[3]</td>
	                    <td>길동이</td>
	                    <td>24-01-29</td>
	                    <td>12</td>
	                </tr>
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
	            <a href="#" class="btnWrite">글쓰기</a>
	        </div>
	    </section>
	</main>
<%@ include file="./_footer.jsp" %>