package kr.co.jboard2.DAO;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kr.co.jboard2.DB.DBHelper;
import kr.co.jboard2.DB.SQL;
import kr.co.jboard2.DTO.ArticleDTO;

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
	public ArticleDTO selectArticle(int no) {
		ArticleDTO article = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_ARTICLE);
			psmt.setInt(1, no);
			logger.info(""+psmt);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				article = new ArticleDTO();
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
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectArticle"+e.getMessage());
		}
		return article;
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
	
	public void updateArticle(ArticleDTO articleDTO) {
		
	}

	public void deleteArticle(int no) {
		
	}
	
// 사용자 정의 CRUD 메서드
	// 전체 게시글 수 조회
	public int selectCountTotal() {
		int total = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_COUNT_TOTAL);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectCountTotal" + e.getMessage());
		}
		return total;
	}
}
