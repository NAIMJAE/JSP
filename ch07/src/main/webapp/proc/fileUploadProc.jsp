<%@page import="ch07.dto.FileDTO"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.UUID"%>
<%@page import="java.util.concurrent.ExecutionException"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	// 파일 업로드 디렉토리 경로 설정 (서버에서 구동되는 경로)
	String uploadPath = application.getRealPath("/uploads");
	
	// 파일 업로드 처리 객체 생성
	FileItemFactory factory = new DiskFileItemFactory(); // 업로드된 파일을 메모리 또는 디스크에 저장하는데 사용
	ServletFileUpload upload = new ServletFileUpload(factory); // 파일 업로드 요청을 파싱하고 업로드된 파일 및 폼 필드 처리
	
	// 인코딩 설정
	upload.setHeaderEncoding("UTF-8");
	
	// 최대 파일 크기 설정
	upload.setSizeMax(1024 * 1024 * 10); // 10mb
	
	// 파일 객체 생성
	FileDTO fileDTO = new FileDTO();
	
	try {
		// 파일 업로드 스트림 작업
		List<FileItem> items = upload.parseRequest(request); // 업로드된 파일 및 폼 필드를 파싱하여 List<FileItem>으로 반환
		Iterator<FileItem> iter = items.iterator(); // 파일 업로드 항목을 순회하기 위한 Iterator를 생성
	
		while(iter.hasNext()){ // 파일 업로드 항목을 반복하여 처리
			
			FileItem item = iter.next(); // 다음 파일 업로드 항목을 가져옴
			
			if(!item.isFormField()){
				// 파일 업로드 처리
				
				// 확장자 추출
				String fileName = item.getName(); //업로드된 파일의 이름을 가져옴
				int idx = fileName.lastIndexOf("."); // 끝에서 .이 나오는 인덱스 번호 추출
				String ext = fileName.substring(idx); // . 이후 문자(확장자명) 추출
				
				// 파일명 생성
				String savedFileName = UUID.randomUUID().toString()+ext; // 파일 이름이 중복되지 않게 UUID를 이용해 랜덤이름 설정
				fileDTO.setoName(fileName); // 원래 파일명 DTO에 저장
				fileDTO.setsName(savedFileName); // 수정된 파일명 DTO에 저장
				
				// 파일 쓰기
				File uploadedfile = new File(uploadPath + "/" + savedFileName); // 업로드된 파일이 저장될 실제 경로에 새로운 File 객체를 생성
				item.write(uploadedfile); // 업로드된 파일을 지정된 경로에 저장 (파일은 실제 서버의 파일 시스템에 저장)
				
				System.out.println("File upload!");
				
			}else{
				// 일반 데이터 처리
				String fieldName = item.getFieldName(); // 현재 파일 항목의 필드 이름을 가져옴
				String value = item.getString("UTF-8"); // 현재 파일 항목의 값을 UTF-8 형식으로 가져옴 (= request.getParameter)
				
				System.out.println(fieldName + " : " + value);
				
				// fileDTO에 title 저장
				if(fieldName.equals("title")){
					fileDTO.setTitle(value);
				}
			}
		}
	}catch (Exception e){
		e.printStackTrace(); // 파일 최대 크기 초과 예외 처리
	}
	
	// 업로드된 file을 Database에 저장
	try{
		//1 단계	- JNDI 서비스 객체 생성
		Context initCtx = new InitialContext();
		Context ctx = (Context) initCtx.lookup("java:comp/env"); // JNDI 기본 환경 이름
		
		//2 단계 - 커넥션 가져오기
		DataSource ds = (DataSource) ctx.lookup("jdbc/studydb");
		Connection conn = ds.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement("INSERT INTO `FileTest` (`title`, `oName`, `sName`, `rdate`) VALUES (?, ?, ?, NOW())");
		psmt.setString(1, fileDTO.getTitle());
		psmt.setString(2, fileDTO.getoName());
		psmt.setString(3, fileDTO.getsName());

		psmt.executeUpdate();
		
		conn.close();
		psmt.close();
	}catch (Exception e){
		e.printStackTrace();
	}
	
	response.sendRedirect("../1.fileUploadTest.jsp");
%>