package kr.co.jboard2.DAO;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kr.co.jboard2.DB.DBHelper;
import kr.co.jboard2.DB.SQL;
import kr.co.jboard2.DTO.ArticleDTO;
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
			logger.info("psmt : " + psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.error("insertFile : " + e.getMessage());
		}
	}
	
	// 파일 조회 (view.do)
	public FileDTO selectFile(int ano) {
		FileDTO file = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_FILE);
			psmt.setInt(1, ano);
			
			logger.info(""+psmt);
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
		} catch (Exception e) {
			logger.error("selectArticle"+e.getMessage());
		}
		return file;
	}
	
	public List<FileDTO> selectFiles() {
		return null;
	}
	
	public void updateFile(FileDTO fileDTO) {
		
	}

	public void deleteFile(int fno) {
		
	}
	
// 사용자 정의 CRUD 메서드
}
