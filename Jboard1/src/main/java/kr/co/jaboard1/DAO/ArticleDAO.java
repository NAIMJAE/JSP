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
		
		public ArticleDTO selectArticle(int no) {
			ArticleDTO dto = new ArticleDTO();
			try {
				
				conn = getConnection();
				psmt = conn.prepareStatement(sql.SELECT_ARTICLE);
				psmt.setInt(1, no);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					
					dto = new ArticleDTO();
					dto.setNo(rs.getString(1));
					dto.setParent(rs.getString(2));
					dto.setComment(rs.getString(3));
					dto.setCate(rs.getString(4));
					dto.setTitle(rs.getString(5));
					dto.setContent(rs.getString(6));
					dto.setFile(rs.getString(7));
					dto.setHit(rs.getString(8));
					dto.setWriter(rs.getString(9));
					dto.setRegip(rs.getString(10));
					dto.setRdate(rs.getString(11));
				}
				
				rs.close();
				conn.close();
				stmt.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return dto;
		}
		
		public List<ArticleDTO> selectArticles() {
			List<ArticleDTO> articles = new ArrayList<>();
			
			try {
				
				conn = getConnection();
				psmt = conn.prepareStatement(sql.SELECT_ARTICLES);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					
					ArticleDTO dto = new ArticleDTO();
					dto.setNo(rs.getString(1));
					dto.setParent(rs.getString(2));
					dto.setComment(rs.getString(3));
					dto.setCate(rs.getString(4));
					dto.setTitle(rs.getString(5));
					dto.setContent(rs.getString(6));
					dto.setFile(rs.getString(7));
					dto.setHit(rs.getString(8));
					dto.setWriter(rs.getString(9));
					dto.setRegip(rs.getString(10));
					dto.setRdate(rs.getString(11));
					
					articles.add(dto);
				}
				
				rs.close();
				conn.close();
				stmt.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return articles;
		}
		
		public void updateArticle(ArticleDTO article) {}
		
		public void deleteArticle(int no) {}
}
