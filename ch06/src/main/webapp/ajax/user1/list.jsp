<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
		<script>
		
		window.onload = function() {
			
			const table = document.getElementById('list');
			
			// 데이터 불러오기
			fetch('./proc/getUser1s.jsp')
			.then((response)=> response.json()) // 넘겨받은 JSON 문자열을 자바스크립트에서 사용 가능하게 변환
			.then((data)=>{
				console.log(data);
				for(const user of data){
					
					const tr = document.createElement('tr');
					const td1 = document.createElement('td');
					const td2 = document.createElement('td');
					const td3 = document.createElement('td');
					const td4 = document.createElement('td');
					const td5 = document.createElement('td');
					const td6 = document.createElement('td');
					const a1 = document.createElement('a');
					const a2 = document.createElement('a');
					
					td1.innerText = user.uid;
                    td2.innerText = user.name;
                    td3.innerText = user.birth;
                    td4.innerText = user.hp;
                    td5.innerText = user.age;
                    a1.href = './modify.jsp?uid='+user.uid;
                    a1.innerText = '수정';
                    a2.href = './proc/deleteUser1.jsp?uid='+user.uid;
                    a2.innerText = '삭제';
                    // 삭제 버튼 함수
                    a2.onclick = function(e){
						e.preventDefault();
						
						const parent = e.target.closest('tr');
						const url = this.href;
						const params = url.split('?')[1];
						const value = params.split('=')[1];
						console.log(value); // uid
						
						fetch('./proc/deleteUser1.jsp?uid='+value)
							.then(response=>response.json())
							.then((data)=>{
								console.log(data);
								if(data.result > 0){
									alert('삭제완료');
									
									// 태그 동적 삭제
									parent.remove();											
								}
							})
							.catch((err)=>{
								console.log(err);
							});
					}

                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tr.appendChild(td4);
                    tr.appendChild(td5);
                    tr.appendChild(td6);
                    td6.appendChild(a1);
                    td6.appendChild(a2);

                    table.append(tr);
				}
				
			})
			.catch((err)=>{
				console.log(err);
			});
		}
			
		</script>
	</head>
	<body>
		<h3>User1 목록</h3>
		<a href="./register.jsp">등록하기</a>
		<table border="1" id="list">
			<tr>
				<td>아이디</td>
				<td>이름</td>
				<td>생년월일</td>
				<td>휴대폰</td>
				<td>나이</td>
				<td>관리</td>
			</tr>
		</table>
		
		
	</body>
</html>