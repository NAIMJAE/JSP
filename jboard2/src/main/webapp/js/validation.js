// register.jsp의 script
// 유효성 검사에 사용할 상태 변수
let isUidOk = false;
let isPassOk = false;
let isNickOk = false;
let isNameOk = false;
let isEmailOk = false;
let isHpOk = false;

// 유효성 검사에 사용할 정규 표현식
const reUid   = /^[a-z]+[a-z0-9]{4,19}$/g;
const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reName  = /^[가-힣]{2,10}$/ 
const reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

window.onload = function(){
	// 문서 객체 생성
	const form = document.formRegister;
	const btnCheckUid  = document.getElementById('btnCheckUid');
	const btnCheckNick = document.getElementById('btnCheckNick');
	const btnSendEmail = document.getElementById('btnSendEmail');
	const btnAuthEmail = document.getElementById('btnAuthEmail');
	const auth = document.getElementById('auth');
	const hp = document.getElementById('hp');
	const fieldAuth = document.getElementsByClassName('auth')[0];
	
	const btnCheckHp = document.getElementById('btnCheckHp');
	const btnZip       = document.getElementById('btnZip');
	
	const resultUid = document.getElementsByClassName('resultUid')[0];
	const resultPass = document.getElementsByClassName('resultPass')[0];
	const resultName = document.getElementsByClassName('resultName')[0];
	const resultNick = document.getElementsByClassName('resultNick')[0];
	const resultEmail = document.getElementsByClassName('resultEmail')[0];
	const resultHp = document.getElementsByClassName('resultHp')[0];
	
	// 아이디 유효성 검사, 중복 체크
	btnCheckUid.onclick = function(){
		const value = form.uid.value;
		
		if(!value.match(reUid)){
			resultUid.innerText = '아이디 형식이 맞지 않습니다.';
			resultUid.style.color = 'red';
			isUidOk = false;
			return;
		}
		// 중복체크
		fetch('/jboard2/user/checkUser.do?type=uid&value='+value)
		.then((response)=> response.json())
		.then((data)=>{
			console.log(data);
			if(data.result > 0){
				resultUid.innerText = '이미 사용중인 아이디 입니다.';
				resultUid.style.color = 'red';
				isUidOk = false;
			}else{
				resultUid.innerText = '사용가능한 아이디 입니다.';
				resultUid.style.color = 'green';
				isUidOk = true;
			}
		})
		.catch((err)=>{
			console.log(err);
		});
	}
	// 비밀번호 유효성 검사
	form.pass2.addEventListener('focusout',function(){
		const pass1 = form.pass1.value;
		const pass2 = form.pass2.value;
		
		if(pass1 == pass2){
			if(!pass1.match(rePass)){
				resultPass.innerText = '비밀번호 형식이 맞지 않습니다.';
				resultPass.style.color = 'red';
				isPassOk = false;
				return;
			}else{
				resultPass.innerText = '사용가능한 비밀번호 입니다.';
				resultPass.style.color = 'green';
				isPassOk = true;
				return;
			}			
		}else{
			resultPass.innerText = '비밀번호가 일치하지 않습니다.';
			resultPass.style.color = 'red';
			isPassOk = false;
			return;
		}
	})
	
	// 이름 유효성 검사
	form.name.addEventListener('focusout',function(){
		const value = form.name.value;
		
		if(!value.match(reName)){
			resultName.innerText = '이름 형식에 맞지 않습니다.';
			resultName.style.color = 'red';
			isNameOk = false;
			return;
		}else{
			resultName.innerText = '';
			resultName.style.color = 'green';
			isNameOk = true;
			return;
		}			
	})
	
	// 닉네임 유효성 검사, 중복 체크
	btnCheckNick.onclick = function(){
		const value = form.nick.value;
		
		if(!value.match(reNick)){
			resultNick.innerText = '닉네임 형식이 맞지 않습니다.';
			resultNick.style.color = 'red';
			isNickOk = false;
			return;
		}
		// 중복체크
		fetch('/jboard2/user/checkUser.do?type=nick&value='+value)
		.then((response)=> response.json())
		.then((data)=>{
			console.log(data);
			if(data.result > 0){
				resultNick.innerText = '이미 사용중인 닉네임 입니다.';
				resultNick.style.color = 'red';
				isNickOk = false;
			}else{
				resultNick.innerText = '사용가능한 아이디 입니다.';
				resultNick.style.color = 'green';
				isNickOk = true;
			}
		})
		.catch((err)=>{
			console.log(err);
		});
	}
	
	// 이메일 유효성 검사, 중복 체크, 인증번호 처리
	btnSendEmail.onclick = function(){
		const value = form.email.value;
		
		if(!value.match(reEmail)){
			resultEmail.innerText = '이메일 형식이 맞지 않습니다.';
			resultEmail.style.color = 'red';
			isEmailOk = false;
			return;
		}
		
		resultEmail.style.color = 'green';
		resultEmail.innerText = '이메일 인증 코드 전송 중..'
		
		// 중복체크, 인증 코드 전송
		fetch('/jboard2/user/checkUser.do?type=email&value='+value)
		.then((response)=> response.json())
		.then((data)=>{
			console.log(data);
			if(data.result > 0){
				resultEmail.innerText = '이미 사용중인 이메일 입니다.';
				resultEmail.style.color = 'red';
				isEmailOk = false;
			}else{
				// 인증 코드 입력 활성화
				fieldAuth.style.display = 'block';
				auth.focus();
				resultEmail.innerText = '이메일 인증 코드 6자리 입력'
				resultEmail.style.color = 'green';
			}
		})
		.catch((err)=>{
			console.log(err);
		});
	}
	
	// 이메일 인증 코드 확인
	btnAuthEmail.onclick = function(){
		const value = form.auth.value;
		
		fetch('/jboard2/user/checkUser.do', {
			method: 'POST',
			body: JSON.stringify({"code": value})
		})
		.then((response)=> response.json())
		.then((data)=>{
			console.log(data);
			if(data.result > 0){
				resultEmail.innerText = '이메일이 인증되었습니다.';
				resultEmail.style.color = 'green';
				isEmailOk = true;
				form.email.readOnly = true;
				form.email.style.border = '1px solid green';
				form.email.style.color = 'gray';
				fieldAuth.style.display = 'none';
				hp.focus();
			}else{
				// 인증 코드 입력 활성화
				resultEmail.innerText = '이메일 인증 코드가 일치하지 않습니다.';
				resultEmail.style.color = 'red';
				isEmailOk = false;
			}

		})
		.catch((err)=>{
			console.log(err);
		});
	}
	
	// 연락처 유효성 검사, 중복 체크
	btnCheckHp.onclick = function(){
		const value = form.hp.value;
		
		if(!value.match(reHp)){
			resultHp.innerText = '연락처 형식이 맞지 않습니다.';
			resultHp.style.color = 'red';
			isHpOk = false;
			return;
		}
		
		// 중복체크
		fetch('/jboard2/user/checkUser.do?type=hp&value='+value)
		.then((response)=> response.json())
		.then((data)=>{
			console.log(data);
			if(data.result > 0){
				resultHp.innerText = '이미 사용중인 연락처 입니다.';
				resultHp.style.color = 'red';
				isHpOk = false;
			}else{
				resultHp.innerText = '사용가능한 연락처 입니다.';
				resultHp.style.color = 'green';
				isHpOk = true;
			}
		})
		.catch((err)=>{
			console.log(err);
		});
	}
	
	// 공통 fetch 함수		
	
	// 최종 전송하기
	form.onsubmit = function(){
		
		if(!isUidOk){
			alert('아이디가 유효하지 않습니다.');
			return false; // 전송 취소	
		}else if(!isPassOk){
			alert('비밀번호가 유효하지 않습니다.');
			return false; // 전송 취소	
		}else if(!isNameOk){
			alert('이름이 유효하지 않습니다.');
			return false; // 전송 취소	
		}else if(!isNickOk){
			alert('별명이 유효하지 않습니다.');
			return false; // 전송 취소	
		}else if(!isEmailOk){
			alert('이메일이 유효하지 않습니다.');
			return false; // 전송 취소	
		}else if(!isHpOk){
			alert('전화번호가 유효하지 않습니다.');
			return false; // 전송 취소	
		}
		return true; // 폼 전송 시작
	}
}



