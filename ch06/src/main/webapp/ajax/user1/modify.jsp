<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String uid = request.getParameter("uid");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>AJAX User1</title>
		<!-- 
			날짜 : 2024/02/15
			이름 : 박임재
			내용 : JSP AJAX 실습
		 -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
		 
		<script>
		window.onload = function() {
			
			// 문서 객체 생성
			const formUser = document.getElementsByTagName('form')[0];
			const btnSubmit = formUser.submit;
			
			// 전송 버튼
			btnSubmit.onclick = (e) => {
				e.preventDefault();
				
				const uid = formUser.uid.value;
				const name = formUser.name.value;
				const birth = formUser.birth.value;
				const hp = formUser.hp.value;
				const age = formUser.age.value;
				
				
				const jsonData = {
						"uid" : uid,
						"name" : name,
						"birth" : birth,
						"hp" : hp,
						"age" : age,
				};
				
				/*
					- jquery ajax를 이용하면 json 문자열 변환 없이 바로 전송 가능
					- 서버측에서는 request.getParameter 수신 가능
				*/
								
				$.ajax({
					method: 'POST',
					url: './proc/putUser1.jsp',
					data: jsonData,
					success: function(data){
						
						// 자동으로 객체 변환되어 들어옴
						console.log(data);
						
						if(data.result > 0){
							alert('수정완료');
							location.href = './list.jsp';
						}
						
					}
				});
				
			}
			
			// 현재 주소
			const url = location.href;
			const params = url.split('?')[1];
			const value = params.split('=')[1];
			console.log(value);
			
			// 서버 요청
			const xhr = new XMLHttpRequest();
			xhr.open('GET', './proc/getUser1.jsp?uid='+value);
			xhr.send();
			
			// 응답 처리
			xhr.onreadystatechange = function(){
				
				
				// 응답 완료
				if(xhr.readyState == XMLHttpRequest.DONE){
					
					// 요청 성공
					if(xhr.status == 200){
						const resData = JSON.parse(xhr.responseText);
						console.log(resData);
						
						formUser.uid.value = resData.uid;
						formUser.name.value = resData.name;
						formUser.birth.value = resData.birth;
						formUser.hp.value = resData.hp;
						formUser.age.value = resData.age;
						
						
					}else {
						// 요청 실패
						console.log('요청 실패...');
					}
				}
			}
		}
		</script>
	</head>
	<body>
		<h3>User1 수정</h3>
		<form action="#">
			<table border="1">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="uid" readonly></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td>생년월일</td>
					<td><input type="date" name="birth"></td>
				</tr>
				<tr>
					<td>휴대폰</td>
					<td><input type="text" name="hp"></td>
				</tr>
				<tr>
					<td>나이</td>
					<td><input type="number" name="age"></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" name="submit" value="수정하기">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>