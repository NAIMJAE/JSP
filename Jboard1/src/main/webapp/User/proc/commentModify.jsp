<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%



%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>list</title>
    <link rel="stylesheet" href="../../CSS/style.css">
    <style>

    </style>
</head>
<body>
    <div id="container">
		<main>
		<!--댓글 리스트-->
	        <section class="commentList">
	            <h3>댓글 수정 테스트 페이지</h3>
	            <article class="comment">
	                <span>
	                    <span>홍길동</span>
	                    <span>2024-20-11</span>
	                </span>
	                <textarea id="myTextarea" name="comment" readonly>동해물과 백두산이 마르고</textarea>
	                <div>
	                    <a href="#" class="btnDelete">삭제</a>
	                    <a href="#" id="toggleButton" class="btnModify">수정</a>
	                </div>
	            </article>
	            
	            <p class="empty">
	                등록된 댓글이 없습니다.
	            </p>
	        </section>
	    </main>
    </div>
    <script>
	document.getElementById('toggleButton').addEventListener('click', function() {
		var textarea = document.getElementById('myTextarea');
			textarea.readOnly = !textarea.readOnly;
		if (textarea.readOnly) {
	    	this.innerText = '수정';
	  	} else {
	    	this.innerText = '수정 완료';
	  	}
	});
</script>
	</body>
</html>
