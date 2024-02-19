// 유효성 검사에 사용할 변수
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
		
	const form = document.querySelector('.register > form');
	
	const btnCheckUid = document.getElementById('btnCheckUid');
	const btnCheckNick = document.getElementById('btnCheckNick');
	const btnCheckEmail = document.getElementById('btnCheckEmail');
	const btnCheckHp = document.getElementById('btnCheckHp');
	
	const url = './proc/checkCountUserProc.jsp';
	
	const resultUid = document.getElementsByClassName('resultuid')[0];
	const resultNick = document.getElementsByClassName('resultNick')[0];
	const resultEmail = document.getElementsByClassName('resultEmail')[0];
	const resultHp = document.getElementsByClassName('resultHp')[0];
	const resultPass = document.getElementsByClassName('resultPass')[0];
	const resultName = document.getElementsByClassName('resultName')[0];
	
	// 아이디 체크
	btnCheckUid.onclick = function(e){
		e.preventDefault();
		
		// 아이디 유효성 검사
		const uid = form.uid.value
		if(!uid.match(reUid)){
			resultUid.innerText = '아이디 형식이 맞지 않습니다.';
			resultUid.style.color = 'red';
			isUidOk = false;
			return;
		}
		
		// 입력한 아이디 가져오기
		const params = '?type=uid&value='+uid;
		isUidOk = getCheckResult(url+params, resultUid);
		
		console.log('isUidOk : ' + isUidOk);
	}
	// 닉네임 체크
	btnCheckNick.onclick = function(e) {
		e.preventDefault();
		
		// 닉네임 유효성 검사
		const nick = form.nick.value
		if(!nick.match(reNick)){
			resultNick.innerText = '닉네임 형식이 맞지 않습니다.';
			resultNick.style.color = 'red';
			isNickOk = false;
			return;
		}
		
		// 입력한 닉네임 가져오기
		const params = '?type=nick&value='+form.nick.value;
		getCheckResult(url+params, resultNick)
	}
	// 이메일 체크
	btnCheckEmail.onclick = function(e) {
		e.preventDefault();
		
		// 이메일 유효성 검사
		const email = form.email.value
		if(!email.match(reEmail)){
			resultEmail.innerText = '이메일 형식이 맞지 않습니다.';
			resultEmail.style.color = 'red';
			isEmailOk = false;
			return;
		}
		
		// 입력한 이메일 가져오기
		const params = '?type=email&value='+form.email.value;
		getCheckResult(url+params, resultEmail)
	}
	// 전화번호 체크
	btnCheckHp.onclick = function(e) {
		e.preventDefault();
		
		// 전화번호 유효성 검사
		const hp = form.hp.value
		if(!hp.match(reHp)){
			resultHp.innerText = '전화번호 형식이 맞지 않습니다.';
			resultHp.style.color = 'red';
			isHpOk = false;
			return;
		}
		
		// 전화번호 닉네임 가져오기
		const params = '?type=hp&value='+form.hp.value;
		const aa = getCheckResult(url+params, resultHp) // 비동기 시작
		console.log(aa);
	}
	
	// 비밀번호 유효성 검사
	form.pass2.addEventListener ('focusout', function(){
		// 전화번호 유효성 검사
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
			}
		}else{
			resultPass.innerText = '비밀번호가 일치하지 않습니다.';
			resultPass.style.color = 'red';
			isPassOk = false;
			return;
		}
	});
	
	// 이름 유효성 검사
	form.name.addEventListener ('keyup', function(){
		const name = form.name.value;
		if(!name.match(reName)){
			resultName.innerText = '이름 형식이 맞지 않습니다.';
			resultName.style.color = 'red';
			isNameOk = false;
			return;
		}else{
			resultName.innerText = ' ';
			isNameOk = true;
			return;
		}
	});
	
	// 유효성 검사 (서버에 전송 하기 전에 유효성 검사 먼저 시행 / 서버 부담 줄이기)
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
			
		}else{
			alert('가입을 진행 하시겠습니까?');
			return true; // 폼 전송 시작
			
		}
	}
	
	// 공통 fetch 함수
	async function getCheckResult(url, target) {
    let result = false;
    console.log('result1 : ' + result);

    try {
        const response = await fetch(url);
        const data = await response.json();

        console.log(data);
        if (data.result > 0) {
            // 중복되는 데이터 false
            target.innerText = "이미 사용 중인 " + data.type + "입니다.";
            target.style.color = 'red';
            result = false;
            console.log('result2 : ' + result);
        } else {
            // 중복되지 않는 데이터 true
            target.innerText = "사용 가능한 " + data.type + "입니다.";
            target.style.color = 'green';
            result = true;
            console.log('result3 : ' + result);
        }
    } catch (err) {
        console.error("오류 발생:", err);
        target.innerText = "서버와의 통신 중 오류가 발생했습니다.";
        target.style.color = 'red';
        result = false;
    }
    
    // 위에 fetch 끝날때까지 대기
    console.log('result4 : ' + result);
    return result;
}
	
	/*
	// 공통 fetch 함수
	function getCheckResult(url, target){
		
		let result = false;
		console.log('result1 : ' + result);
		
		fetch(url)
		.then(response => response.json())
		.then((data) => {
			console.log(data);
			if(data.result > 0) {
				// 중복되는 데이터 false
				target.innerText = "이미 사용 중인 "+data.type+"입니다.";
				target.style.color = 'red';
				result = false;
				console.log('result2 : ' + result);
				
			} else {
				// 중복되지 않는 데이터 true
				target.innerText = "사용 가능한 "+data.type+"입니다.";
				target.style.color = 'green';
				result = true;
				console.log('result3 : ' + result);
			}
		})
		.catch((err) => {console.log("잘못 입력하셨습니다.");})
		// 위에 fetch 끝날때까지 대기
		console.log('result4 : ' + result);
		return result;	
		
		// 콘솔에서 순서가 1->2or3->4->isUidOk 로 찍혀야하는데
	}	
	 */
}