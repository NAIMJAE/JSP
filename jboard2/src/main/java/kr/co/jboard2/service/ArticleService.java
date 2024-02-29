package kr.co.jboard2.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DAO.ArticleDAO;
import kr.co.jboard2.DTO.ArticleDTO;
import kr.co.jboard2.DTO.FileDTO;

public class ArticleService {
	private static ArticleService instance = new ArticleService();
	public static ArticleService getInstance() {
		return instance;
	}
	private ArticleService() {}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ArticleDAO dao = ArticleDAO.getInstance();
	
// 기본 CRUD 메서드
	public int insertArticle(ArticleDTO articleDTO) {
		return dao.insertArticle(articleDTO);
	}
	public ArticleDTO selectArticle(String no) {
		return dao.selectArticle(no);
	}
	public List<ArticleDTO> selectArticles(int start) {
		return dao.selectArticles(start);
	}
	
	public int updateArticle(ArticleDTO articleDTO) {
		return dao.updateArticle(articleDTO);
	}
	public void deleteArticle(String no) {
		dao.deleteArticle(no);
	}
	
// 사용자 정의 CRUD 메서드
	// 파일 업로드
	public ArticleDTO fileUpload(HttpServletRequest req) {
		ServletContext ctx = req.getServletContext();
		String uploadPath = ctx.getRealPath("/uploads");
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		upload.setSizeMax(1024 * 1024 * 10); // 10mb
		
		ArticleDTO articleDTO = new ArticleDTO();
		// 파일 DTO 생성
		List<FileDTO> fileDTOs = new ArrayList<FileDTO>();
		
		try {
			List<FileItem> items = upload.parseRequest(req);
			int count = 0;
			
			for(FileItem item : items) {
				if(!item.isFormField()) {
					// 첨부파일일 경우
					if (!item.getName().isEmpty()) {
						count++;
						
						String fname = item.getName();
						int idx = fname.lastIndexOf(".");
						String ext = fname.substring(idx);
						
						String saveName = UUID.randomUUID().toString() + ext;
						
						File file = new File(uploadPath + File.separator + saveName);
						item.write(file);
						
						FileDTO fileDTO = new FileDTO();
						fileDTO.setoName(fname);
						fileDTO.setsName(saveName);
						fileDTOs.add(fileDTO);
					}
				}else {
					// 폼 데이터일 경우
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					
					if(fieldName.equals("title")) {
						articleDTO.setTitle(fieldValue);
					}else if(fieldName.equals("content")) {
						articleDTO.setContent(fieldValue);
					}else if(fieldName.equals("writer")) {
						articleDTO.setWriter(fieldValue);
					}else if(fieldName.equals("no")) {
						articleDTO.setNo(count);
					}
				}
			}
			articleDTO.setFile(count);
		} catch (Exception e) {
			logger.error("fileUpload : "+e.getMessage());
		}
		
		articleDTO.setFileDTOs(fileDTOs);
		
		return articleDTO;
	}

	public void fileDownload() {
		
	}
	
public void fileDownload(HttpServletRequest req, HttpServletResponse resp, FileDTO fileDTO) {
		
		try {
			// response 헤더 설정
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(fileDTO.getoName(), "utf-8"));
			resp.setHeader("Content-Transfer-Encoding", "binary");
			resp.setHeader("Pragma", "no-cache");
			resp.setHeader("Cache-Control", "private");
			
			// response 파일 스트림 작업
			ServletContext ctx = req.getServletContext();
			String uploadsPath = ctx.getRealPath("/uploads");
			
			File file = new File(uploadsPath + File.separator + fileDTO.getsName());
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
			
			while(true){
				int data = bis.read();
				if(data == -1){
					break;
				}
				bos.write(data);
			}
			
			bos.close();
			bis.close();
			
		}catch (Exception e) {
			logger.error("fileDownload : " + e.getMessage());
		}
	}
	
	// 전체 게시글 수 조회
		public int selectCountTotal() {
			return dao.selectCountTotal();
		}
	// file 갯수 조회
	public int selectArticleFile(int no) {
		return dao.selectArticleFile(no);
	}
	
	// 댓글 입력
	public int insertComment(ArticleDTO articleDTO) {
		return dao.insertComment(articleDTO);
	}
	
	// 댓글 조회
	public List<ArticleDTO> selectComments(String parent) {
		return dao.selectComments(parent);
	}
	
	// 댓글 삭제
	public int deleteComment(String no, String parent) {
		return dao.deleteComment(no, parent);
	}
	
	// 조회수 +1
	public void updateHitCount(String no) {
		dao.updateHitCount(no);
	}
	
}
