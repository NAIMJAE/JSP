package kr.co.jaboard1.DAO;

import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxConnection.Close;

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
				psmt = conn.prepareStatement(sql.SELECT_ARTICLES);
				psmt.setInt(1, start);
				
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
				
				rs.close();
				conn.close();
				stmt.close();
				
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
		
		public void deleteArticle(int no) {
			try {
				conn = getConnection();
				psmt = conn.prepareStatement(sql.DELETE_ARTICLE);
				psmt.setInt(1, no);
				
				System.out.println(psmt);
				
				psmt.executeUpdate();
				CloseAll();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public int SelectCountTotal() {
			
			int total = 0;
			
			try {
				conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql.SELECT_COUNT_TOTAL);
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
}
