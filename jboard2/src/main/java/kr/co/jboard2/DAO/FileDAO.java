package kr.co.jboard2.DAO;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kr.co.jboard2.DB.DBHelper;
import kr.co.jboard2.DB.SQL;
import kr.co.jboard2.DTO.FileDTO;

public class FileDAO extends DBHelper{
	private static FileDAO instance = new FileDAO();
	public static FileDAO getInstance() {
		return instance;
	}
	private FileDAO() {}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
// 기본 CRUD 메서드
	public void insertFile(FileDTO fileDTO) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.INSERT_FILE);
			psmt.setInt(1, fileDTO.getAno());
			psmt.setString(2, fileDTO.getoName());
			psmt.setString(3, fileDTO.getsName());
			logger.info("insertFile : " + psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.error("insertFile : " + e.getMessage());
		}
	}
	
	// 파일 조회 (view.do)
	public FileDTO selectFile(String fno) {
		FileDTO file = null;
		try {
			conn = getConnection();
			
			psmt = conn.prepareStatement(SQL.SELECT_FILE);
			psmt.setString(1, fno);
			logger.info("selectFile : " + psmt);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				file = new FileDTO();
				file.setFno(rs.getInt(1));
				file.setAno(rs.getInt(2));
				file.setoName(rs.getString(3));
				file.setsName(rs.getString(4));
				file.setDownload(rs.getInt(5));
				file.setRdate(rs.getString(6));
			}
			CloseAll();
		}catch (Exception e) {
			logger.error("selectFile : " + e.getMessage());
		}
		return file;
	}
	
	public List<FileDTO> selectFiles(String no) {
		List<FileDTO> fileDTO = new ArrayList<FileDTO>();
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_FILES);
			psmt.setString(1, no);
			logger.info("selectFiles : " + psmt);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				FileDTO file = new FileDTO();
				file.setFno(rs.getInt(1));
				file.setAno(rs.getInt(2));
				file.setoName(rs.getString(3));
				file.setsName(rs.getString(4));
				file.setDownload(rs.getInt(5));
				file.setRdate(rs.getString(6));
				fileDTO.add(file);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectFiles : " + e.getMessage());
		}
		
		return fileDTO;
	}
	
	public void updateFile(FileDTO fileDTO) {
		
	}
	// file 데이터 삭제 & article의 file 카운팅 -1
	public int deleteFile(String fno, int ano) {
		int success = 0;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(SQL.DELETE_FILE);
			psmt.setString(1, fno);
			
			psmtEtc1 = conn.prepareStatement(SQL.UPDATE_FILE_COUNT);
			psmtEtc1.setInt(1, ano);
			
			logger.info("psmt : " + psmt);
			logger.info("psmtEtc1 : " + psmtEtc1);
			
			success = psmt.executeUpdate();
			psmtEtc1.executeUpdate();
			
			conn.commit();
			CloseAll();
		} catch (Exception e) {
			logger.error("deleteFile"+e.getMessage());
		}
		return success;
	}
	
// 사용자 정의 CRUD 메서드
	// 파일 테이블 조회 & 다운로드 카운팅
	public FileDTO selectFileCheck(String fno) {
		
		FileDTO fileDTO = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(SQL.SELECT_FILE);
			psmt.setString(1, fno);
			logger.info("selectFile : " + psmt);
			
			psmtEtc1 = conn.prepareStatement(SQL.UPDATE_FILE_DOWNLOAD);
			psmtEtc1.setString(1, fno);
			
			rs = psmt.executeQuery();
			psmtEtc1.executeUpdate();
			
			conn.commit();
			
			if(rs.next()) {
				fileDTO = new FileDTO();
				fileDTO.setFno(rs.getInt(1));
				fileDTO.setAno(rs.getInt(2));
				fileDTO.setoName(rs.getString(3));
				fileDTO.setsName(rs.getString(4));
				fileDTO.setDownload(rs.getInt(5));
				fileDTO.setRdate(rs.getString(6));
			}
			CloseAll();
		}catch (Exception e) {
			logger.error("selectFile : " + e.getMessage());
		}
		return fileDTO;
	}
	// 게시글 삭제시 file 테이블에 있는 데이터 전부 삭제
	public void deleteFileAll(String no) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.DELETE_FILE_ALL);
			psmt.setString(1, no);
			logger.info("deleteFileAll : "+psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.error("deleteFileAll : " + e.getMessage());
		}
	}
}
