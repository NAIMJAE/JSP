package kr.co.jaboard1.DAO;

import java.util.ArrayList;
import java.util.List;

import kr.co.jaboard1.DTO.ArticleDTO;
import kr.co.jaboard1.db.DBHelper;
import kr.co.jaboard1.db.sql;

public class ArticleDAO extends DBHelper {
	
	private static ArticleDAO instance = new ArticleDAO();
	public static ArticleDAO getInstance() {
		return instance;
	}
	private ArticleDAO() {}
	
	// 기본 CRUD 메서드
		public void insertArticle(ArticleDTO article) {
			try {
				conn = getConnection();
				psmt = conn.prepareStatement(sql.INSERT_ARTICLE);
				
				psmt.setString(1, article.getTitle());
				psmt.setString(2, article.getContent());
				psmt.setString(3, article.getWriter());
				psmt.setString(4, article.getRegip());
				
				psmt.executeUpdate();
				
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public ArticleDTO selectArticle(String no) {
			ArticleDTO dto = new ArticleDTO();
			try {
				
				conn = getConnection();
				psmt = conn.prepareStatement(sql.SELECT_ARTICLE);
				psmt.setString(1, no);
				
				System.out.println(psmt);
				
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					
					dto = new ArticleDTO();
					dto.setNo(rs.getInt(1));
					dto.setParent(rs.getInt(2));
					dto.setComment(rs.getInt(3));
					dto.setCate(rs.getString(4));
					dto.setTitle(rs.getString(5));
					dto.setContent(rs.getString(6));
					dto.setFile(rs.getInt(7));
					dto.setHit(rs.getInt(8));
					dto.setWriter(rs.getString(9));
					dto.setRegip(rs.getString(10));
					dto.setRdate(rs.getString(11));
				}
				
				CloseAll();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return dto;
		}
		
		public List<ArticleDTO> selectArticles(int start) {
			List<ArticleDTO> articles = new ArrayList<>();
			
			try {
				
				conn = getConnection();
				psmt = conn.prepareStatement(sql.SELECT_ARTICLES + sql.SELECT_ARTICLES_ORDER);
				psmt.setInt(1, start);
				
				System.out.println(psmt);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					
					ArticleDTO dto = new ArticleDTO();
					dto.setNo(rs.getInt(1));
					dto.setParent(rs.getInt(2));
					dto.setComment(rs.getInt(3));
					dto.setCate(rs.getString(4));
					dto.setTitle(rs.getString(5));
					dto.setContent(rs.getString(6));
					dto.setFile(rs.getInt(7));
					dto.setHit(rs.getInt(8));
					dto.setWriter(rs.getString(9));
					dto.setRegip(rs.getString(10));
					dto.setRdate(rs.getString(11));
					dto.setNick(rs.getString(12));
					
					articles.add(dto);
				}
				
				CloseAll();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return articles;
		}
		
		public void updateArticle(ArticleDTO article) {
			try {
				conn = getConnection();
				psmt = conn.prepareStatement(sql.UPDATE_ARTICLE);
				psmt.setString(1, article.getTitle());
				psmt.setString(2, article.getContent());
				psmt.setInt(3, article.getNo());
				
				System.out.println(psmt);
				
				psmt.executeUpdate();
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void deleteArticle(String no) {
			try {
				conn = getConnection();
				conn.setAutoCommit(false);
				
				psmt = conn.prepareStatement(sql.DELETE_ARTICLE);
				psmt.setString(1, no);
				System.out.println(psmt);
				
				psmtEtc1 = conn.prepareStatement(sql.DELETE_COMMENTS);
				psmtEtc1.setString(1, no);
				System.out.println(psmtEtc1);
				
				psmt.executeUpdate();
				psmtEtc1.executeUpdate();
				
				conn.commit();
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public int SelectCountTotal(String searchType, String keyword) {
			
			int total = 0;
			
			// StringBuilder를 이용한 동적 쿼리
			StringBuilder SQL = new StringBuilder(sql.SELECT_COUNT_TOTAL);
			
			if(searchType != null && keyword != null) {
				if(searchType.equals("title")) {
					SQL.append(sql.SELECT_ARTICLES_WHERE_TITLE);
				}else if(searchType.equals("content")) {
					SQL.append(sql.SELECT_ARTICLES_WHERE_CONTENT);
				}else if(searchType.equals("title_content")) {
					SQL.append(sql.SELECT_ARTICLES_WHERE_TITLE_CONTENT);
				}else if(searchType.equals("writer")) {
					SQL.append(sql.SELECT_ARTICLES_WHERE_WRITER);
				}
			}
			
			try {
				conn = getConnection();
				psmt = conn.prepareStatement(SQL.toString());
				
				if(searchType != null && keyword != null) {
					if(searchType.equals("title_content")) {
						psmt.setString(1, "%" + keyword + "%");
						psmt.setString(2, "%" + keyword + "%");
					}else{
						psmt.setString(1, "%" + keyword + "%");
					}
				}
				System.out.println(psmt);
				
				rs = psmt.executeQuery();

				if(rs.next()) {
					total = rs.getInt(1);
				}
				CloseAll();
			}catch (Exception e) {
				e.printStackTrace();
			}
			return total;
		}
		
		public void updateHitCount(String no) {
			try {
				conn = getConnection();
				psmt = conn.prepareStatement(sql.UPDATE_HIT_COUNT);
				psmt.setString(1, no);
				
				System.out.println(psmt);
				
				psmt.executeUpdate();
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void updateComment(ArticleDTO comment) {
			try {
				
				conn = getConnection();
				psmt = conn.prepareStatement(sql.UPDATE_COMMENT);
				psmt.setString(1, comment.getContent());
				psmt.setInt(2, comment.getNo());
				
				psmt.executeUpdate();
				
				CloseAll();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public void insertComment(ArticleDTO comment) {
			
			try {
				conn = getConnection();
				conn.setAutoCommit(false); // 트랜잭션 시작
				
				psmt = conn.prepareStatement(sql.INSERT_COMMENT);
				psmt.setInt(1, comment.getParent());
				psmt.setString(2, comment.getContent());
				psmt.setString(3, comment.getWriter());
				psmt.setString(4, comment.getRegip());
				System.out.println(psmt);
				
				psmtEtc1 = conn.prepareStatement(sql.UPDATE_ARTICLE_COMMENT_PLUS);
				psmtEtc1.setInt(1, comment.getParent());
				System.out.println(psmtEtc1);
				
				psmt.executeUpdate();
				psmtEtc1.executeUpdate();
				
				conn.commit();
				
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public List<ArticleDTO> selectComments(String parent) {
			List<ArticleDTO> comments = new ArrayList<>();
			
			try {
				conn = getConnection();
				psmt = conn.prepareStatement(sql.SELECT_COMMENTS);
				psmt.setString(1, parent);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					
					ArticleDTO comment = new ArticleDTO();
					comment.setNo(rs.getInt(1));
					comment.setParent(rs.getInt(2));
					comment.setContent(rs.getString(6));
					comment.setWriter(rs.getString(9));
					comment.setRegip(rs.getString(10));
					comment.setRdate(rs.getString(11));
					comment.setNick(rs.getString(15));
					
					comments.add(comment);
				}
				
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return comments;
		}
		
		public void deleteComment(String parent, String no) {
			try {
				conn = getConnection();
				conn.setAutoCommit(false);
				
				psmt = conn.prepareStatement(sql.DELETE_COMMENT);
				psmt.setString(1, no);
				System.out.println(psmt);
				
				psmtEtc1 = conn.prepareStatement(sql.UPDATE_ARTICLE_COMMENT_MINUS);
				psmtEtc1.setString(1, parent);
				System.out.println(psmtEtc1);
				
				psmt.executeUpdate();
				psmtEtc1.executeUpdate();
				
				conn.commit();
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public List<ArticleDTO> selectArticlesForSearch(String searchType, String keyword, int start) {
			
			List<ArticleDTO> articles = new ArrayList<ArticleDTO>();
			
			// StringBuilder를 이용한 동적 쿼리
			StringBuilder SQL = new StringBuilder(sql.SELECT_ARTICLES);
			
			if(searchType.equals("title")) {
				SQL.append(sql.SELECT_ARTICLES_WHERE_TITLE);
			}else if(searchType.equals("content")) {
				SQL.append(sql.SELECT_ARTICLES_WHERE_CONTENT);
			}else if(searchType.equals("title_content")) {
				SQL.append(sql.SELECT_ARTICLES_WHERE_TITLE_CONTENT);
			}else if(searchType.equals("writer")) {
				SQL.append(sql.SELECT_ARTICLES_WHERE_WRITER);
			}
			SQL.append(sql.SELECT_ARTICLES_ORDER);
			
			try {
				conn = getConnection();
				psmt = conn.prepareStatement(SQL.toString());
				if(searchType.equals("title_content")) {
					psmt.setString(1, "%" + keyword + "%");
					psmt.setString(2, "%" + keyword + "%");
					psmt.setInt(3, start);
				}else {
					psmt.setString(1, "%" + keyword + "%");
					psmt.setInt(2, start);
				}
				System.out.println(psmt);
				rs = psmt.executeQuery();
					
				while(rs.next()) {
					ArticleDTO dto = new ArticleDTO();
					dto.setNo(rs.getInt(1));
					dto.setParent(rs.getInt(2));
					dto.setComment(rs.getInt(3));
					dto.setCate(rs.getString(4));
					dto.setTitle(rs.getString(5));
					dto.setContent(rs.getString(6));
					dto.setFile(rs.getInt(7));
					dto.setHit(rs.getInt(8));
					dto.setWriter(rs.getString(9));
					dto.setRegip(rs.getString(10));
					dto.setRdate(rs.getString(11));
					dto.setNick(rs.getString(12));
					
					articles.add(dto);
				}

				CloseAll();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return articles;
		}
}
