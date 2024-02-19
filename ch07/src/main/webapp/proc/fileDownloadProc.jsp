<%@page import="java.io.File"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="ch07.dto.FileDTO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String fno = request.getParameter("fno");
	
	// 다운로드 파일 정보 조회
	FileDTO fileDTO = null;
	try{
		//1 단계	- JNDI 서비스 객체 생성
		Context initCtx = new InitialContext();
		Context ctx = (Context) initCtx.lookup("java:comp/env"); // JNDI 기본 환경 이름
		
		//2 단계 - 커넥션 가져오기
		DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
		Connection conn = ds.getConnection();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM `FileTest` WHERE `fon`="+fno);
		
		if(rs.next()){
			fileDTO = new FileDTO();
			fileDTO.setFno(rs.getInt(1));
			fileDTO.setTitle(rs.getString(2));
			fileDTO.setoName(rs.getString(3));
			fileDTO.setsName(rs.getString(4));
			fileDTO.setRdate(rs.getString(5));
		}
		conn.close();
		stmt.close();
	}catch (Exception e){
		e.printStackTrace();
	}	
	
	// 다운로드 처리
	response.setContentType("application/octet-stream"); // 8비트 스트림
	response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(fileDTO.getoName(), "utf-8")); // 다운로드할 파일의 이름을 지정
	response.setHeader("Content-Transfer-Encoding", "binary");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "private");
	
	//response 파일 스트림 작업
	String uploadsPath = application.getRealPath("/uploads"); // 업로드된 파일이 저장된 디렉토리의 실제 경로를 가져옴
	
	File file = new File(uploadsPath + File.separator + fileDTO.getsName()); // 다운로드할 파일의 전체 경로를 설정
	out.clear(); // 출력 버퍼를 비움
	out = pageContext.pushBody();
	
	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file)); // 다운로드할 파일을 읽어오기 위한 BufferedInputStream을 생성합니다.
	BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream()); //  클라이언트로 전송할 응답 출력 스트림을 생성
	
	while(true){
		int data = bis.read(); // 파일에서 데이터를 읽어옴
		if(data == -1){ // 파일의 끝에 도달하면 루프를 종료
			break;
		}
		bos.write(data); // 응답 출력 스트림으로 데이터를 전송
	}
	bos.close();
	bis.close();
%>