package kr.co.jboard2.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DAO.FileDAO;
import kr.co.jboard2.DTO.ArticleDTO;
import kr.co.jboard2.DTO.FileDTO;

public class FileService {
	private static FileService instance = new FileService();
	public static FileService getInstance() {
		return instance;
	}
	private FileService() {}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private FileDAO dao = FileDAO.getInstance();
	
// 기본 CRUD 메서드
	public void insertFile(FileDTO fileDTO) {
		dao.insertFile(fileDTO);
	}
	
	public FileDTO selectFile(int ano) {
		return dao.selectFile(ano);
	}
	
	public List<FileDTO> selectFiles() {
		return dao.selectFiles();
	}
	
	public void updateFile(FileDTO fileDTO) {
		dao.updateFile(fileDTO);
	}

	public void deleteFile(int fno) {
		dao.deleteFile(fno);
	}
	
// 사용자 정의 CRUD 메서드
}
