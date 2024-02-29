package kr.co.jboard2.DAO;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kr.co.jboard2.DB.DBHelper;
import kr.co.jboard2.DB.SQL;
import kr.co.jboard2.DTO.ArticleDTO;
import kr.co.jboard2.DTO.FileDTO;

public class ArticleDAO extends DBHelper{
	private static ArticleDAO instance = new ArticleDAO();
	public static ArticleDAO getInstance() {
		return instance;
	}
	private ArticleDAO() {}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
// 기본 CRUD 메서드
	// 게시글 등록
	public int insertArticle(ArticleDTO articleDTO) {
		int pk = 0;
		try {
			conn = getConnection();
			
			// insert가 실행되고 자동 생성되는 pk값을 리턴하는 옵션
			psmt = conn.prepareStatement(SQL.INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1, articleDTO.getTitle());
			psmt.setString(2, articleDTO.getContent());
			psmt.setString(3, articleDTO.getWriter());
			psmt.setString(4, articleDTO.getRegip());
			psmt.setInt(5, articleDTO.getFile());
			
			logger.info("psmt : " + psmt);
			
			// insert 실행
			psmt.executeUpdate();
			
			// 생성된 pk 가져오기
			rs = psmt.getGeneratedKeys();
			if(rs.next()) {
				pk = rs.getInt(1);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("insertArticle : "+e.getMessage());
		}
		return pk;
	}
	
	// 게시글 조회 (view.do)
	public ArticleDTO selectArticle(String no) {
		
		ArticleDTO articleDTO = null;
		List<FileDTO> files = new ArrayList<>();
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_ARTICLE);
			psmt.setString(1, no);
			logger.info("selectArticle : " + psmt);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				if(articleDTO == null) {
					articleDTO = new ArticleDTO();
					articleDTO.setNo(rs.getInt(1));
					articleDTO.setParent(rs.getInt(2));
					articleDTO.setComment(rs.getInt(3));
					articleDTO.setCate(rs.getString(4));
					articleDTO.setTitle(rs.getString(5));
					articleDTO.setContent(rs.getString(6));
					articleDTO.setFile(rs.getInt(7));
					articleDTO.setHit(rs.getInt(8));
					articleDTO.setWriter(rs.getString(9));
					articleDTO.setRegip(rs.getString(10));
					articleDTO.setRdate(rs.getString(11));
				}
				FileDTO fileDTO = new FileDTO();
				fileDTO.setFno(rs.getInt(12));
				fileDTO.setAno(rs.getInt(13));
				fileDTO.setoName(rs.getString(14));
				fileDTO.setsName(rs.getString(15));
				fileDTO.setDownload(rs.getInt(16));
				fileDTO.setRdate(rs.getString(17));
				files.add(fileDTO);
				logger.info("fileDTO : " + fileDTO);
			}
			articleDTO.setFileDTOs(files);
			
			CloseAll();
		}catch (Exception e) {
			logger.error("selectArticle : " + e.getMessage());
		}
		return articleDTO;
	}
	
	// 게시글 전체 조회 (list.do)
	public List<ArticleDTO> selectArticles(int start) {
		List<ArticleDTO> articles = new ArrayList<ArticleDTO>();
		
		StringBuilder sql = new StringBuilder(SQL.SELECT_ARTICLES);
		sql.append(SQL.SELECT_ARTICLES_ORDER);
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, start);
			logger.info("selectArticles : " + psmt);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ArticleDTO article = new ArticleDTO();
				article.setNo(rs.getInt(1));
				article.setParent(rs.getInt(2));
				article.setComment(rs.getInt(3));
				article.setCate(rs.getString(4));
				article.setTitle(rs.getString(5));
				article.setContent(rs.getString(6));
				article.setFile(rs.getInt(7));
				article.setHit(rs.getInt(8));
				article.setWriter(rs.getString(9));
				article.setRegip(rs.getString(10));
				article.setRdate(rs.getString(11));
				article.setNick(rs.getString(12));
				articles.add(article);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("insertArticle : " + e.getMessage());
		}
		return articles;
	}
	
	public int updateArticle(ArticleDTO articleDTO) {
		int pk = 0;
		
		try {
			conn = getConnection();
			
			// insert가 실행되고 자동 생성되는 pk값을 리턴하는 옵션
			psmt = conn.prepareStatement(SQL.UPDATE_ARTICLE, Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1, articleDTO.getTitle());
			psmt.setString(2, articleDTO.getContent());
			psmt.setInt(3, articleDTO.getFile());
			psmt.setInt(4, articleDTO.getNo());
			
			logger.info("updateArticle : " + psmt);
			
			// insert 실행
			psmt.executeUpdate();
			
			// 생성된 pk 가져오기
			rs = psmt.getGeneratedKeys();
			if(rs.next()) {
				pk = rs.getInt(1);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("insertArticle : "+e.getMessage());
		}
		
		return pk;
	}

	public void deleteArticle(String no) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.DELETE_ARTICLE);
			psmt.setString(1, no);
			logger.info("deleteArticle : "+psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.error("deleteArticle" + e.getMessage());
		}
	}
	
// 사용자 정의 CRUD 메서드
	// 전체 게시글 수 조회
	public int selectCountTotal() {
		int total = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_COUNT_TOTAL);
			rs = psmt.executeQuery();
			logger.info("selectCountTotal" + psmt);
			if(rs.next()) {
				total = rs.getInt(1);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectCountTotal" + e.getMessage());
		}
		return total;
	}
	// file 갯수 조회
	public int selectArticleFile(int no) {
		int file = 0;
		
		try {
			
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_ARTICLE_FILE);
			psmt.setInt(1, no);
			logger.info("selectArticleFile" + psmt);
			rs = psmt.executeQuery();
			if(rs.next()) {
				file = rs.getInt(1);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectArticleFile : "+e.getMessage());
		}
		
		return file;
	}
	
	// 댓글 입력 & 게시글 댓글 수 +1
	public int insertComment(ArticleDTO articleDTO) {
		int pk = 0;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			psmt = conn.prepareStatement(SQL.INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS);
			psmt.setInt(1, articleDTO.getParent());
			psmt.setString(2, articleDTO.getContent());
			psmt.setString(3, articleDTO.getWriter());
			psmt.setString(4, articleDTO.getRegip());

			psmtEtc1 = conn.prepareStatement(SQL.UPDATE_ARTICLE_COMMENT_PLUS);
			psmtEtc1.setInt(1, articleDTO.getParent());
			
			logger.info("insertComment : " + psmt);
			logger.info("insertComment : " + psmtEtc1);
			
			psmt.executeUpdate();
			psmtEtc1.executeUpdate();
			
			// INSERT 해서 부여된 AUTO_INCREMENT PK 가져오기
			rs = psmt.getGeneratedKeys();
			if(rs.next()) {
				pk = rs.getInt(1);
			}
			conn.commit();
			CloseAll();
		} catch (Exception e) {
			logger.error("insertComment : " + e.getMessage());
		}
		return pk;
	}
	// 댓글 조회
	public List<ArticleDTO> selectComments(String parent) {
		List<ArticleDTO> comments = new ArrayList<ArticleDTO>();
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_COMMENTS);
			psmt.setString(1, parent);
			logger.info("selectComments" + psmt);
			
			rs = psmt.executeQuery();
			while(rs.next()) {
				ArticleDTO article = new ArticleDTO();
				article.setNo(rs.getInt(1));
				article.setParent(rs.getInt(2));
				article.setComment(rs.getInt(3));
				article.setCate(rs.getString(4));
				article.setTitle(rs.getString(5));
				article.setContent(rs.getString(6));
				article.setFile(rs.getInt(7));
				article.setHit(rs.getInt(8));
				article.setWriter(rs.getString(9));
				article.setRegip(rs.getString(10));
				article.setRdate(rs.getString(11));
				article.setNick(rs.getString(12));
				comments.add(article);
			}
			CloseAll();
		} catch (Exception e) {
			logger.info("selectComments" + e.getMessage());
		}
		return comments;
	}
	// 댓글 삭제 & 게시글 댓글 수 -1
	public int deleteComment(String no, String parent) {
		int result = 0;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(SQL.DELETE_COMMENT);
			psmt.setString(1, no);
			
			psmtEtc1 = conn.prepareStatement(SQL.UPDATE_ARTICLE_COMMENT_MINUS);
			psmtEtc1.setString(1, parent);
			
			logger.info("deleteComment : " + psmt);
			logger.info("deleteComment : " + psmtEtc1);
			
			result = psmt.executeUpdate();
			psmtEtc1.executeUpdate();
			
			conn.commit();
			CloseAll();
		} catch (Exception e) {
			logger.error("deleteComment : " + e.getMessage());
		}
		return result;
	}
	
	// 조회수 +1
	public void updateHitCount(String no) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.UPDATE_HIT_COUNT);
			psmt.setString(1, no);
			logger.info("selectArticle : " + psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.error("updateHitCount" + e.getMessage());
		}
	}
}
