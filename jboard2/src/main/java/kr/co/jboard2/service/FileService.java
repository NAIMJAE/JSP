package kr.co.jboard2.service;

import java.io.File;
import java.util.List;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kr.co.jboard2.DAO.FileDAO;
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
	
	public FileDTO selectFile(String fno) {
		return dao.selectFile(fno);
	} 
	
	public List<FileDTO> selectFiles() {
		return dao.selectFiles();
	}
	
	public void updateFile(FileDTO fileDTO) {
		dao.updateFile(fileDTO);
	}

	public int deleteFile(String fno, int ano) {
		return dao.deleteFile(fno, ano);
	}
	
// 사용자 정의 CRUD 메서드
	public void deleteUploadFile(ServletContext ctx, String sName) {
        // 파일 디렉토리 경로 설정 (서버에서 구동되는 경로)
        String uploadPath = ctx.getRealPath("/uploads");

        // 파일명 생성
        String savedFileName = sName;

        File fileDelete = new File(uploadPath + "/" + savedFileName);

        if (fileDelete.exists()) {
            fileDelete.delete();
            System.out.println("파일 삭제 완료");
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }
}
