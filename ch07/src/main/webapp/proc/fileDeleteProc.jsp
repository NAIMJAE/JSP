<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.UUID"%>
<%@page import="ch07.dto.FileDTO"%>
<%@page import="java.io.File"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String sName = request.getParameter("sName"); // 파일의 실제 이름
	
	// 파일 디렉토리 경로 설정 (서버에서 구동되는 경로)
	String uploadPath = application.getRealPath("/uploads");
	
	// 파일명 생성
	String savedFileName = sName;
	
	File fileDelete = new File(uploadPath + "/" + savedFileName);
	
	if (fileDelete.exists()) {
		fileDelete.delete();
		System.out.println("파일 삭제 완료");
	}else {
	    System.out.println("파일이 존재하지 않습니다.");
	    return;
	}
	
	try{
		//1 단계	- JNDI 서비스 객체 생성
		Context initCtx = new InitialContext();
		Context ctx = (Context) initCtx.lookup("java:comp/env"); // JNDI 기본 환경 이름
		
		//2 단계 - 커넥션 가져오기
		DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
		Connection conn = ds.getConnection();

		PreparedStatement psmt = conn.prepareStatement("DELETE FROM `FileTest` WHERE `sName`=?");
		psmt.setString(1, sName);
		
		psmt.executeUpdate();
		
		System.out.println(psmt);

		conn.close();
		psmt.close();
	}catch (Exception e){
		e.printStackTrace();
	}	
	
	response.sendRedirect("../2.fileDownloadTest.jsp");
%>