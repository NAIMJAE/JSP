package dao;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import dto.User2DTO;

public class User2DAO extends DBHelper{
	// 싱글톤
	private static User2DAO instance = new User2DAO();
	public static User2DAO getInstance() {
		return instance;
	}
	private User2DAO() {}
	
	// 기본 CRUD 메서드
	public void insertUser2(User2DTO user) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `User2` VALUES (?,?,?,?)");
			psmt.setString(1, user.getUid());
			psmt.setString(2, user.getName());
			psmt.setString(3, user.getBirth());
			psmt.setString(4, user.getAddr());
			System.out.println(psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User2DTO selectUser2(String uid) {
		return null;
	}
	
	public List<User2DTO> selectUser2s() {
		List<User2DTO> users = new ArrayList<User2DTO>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM `User2`");
			while(rs.next()) {
				User2DTO user = new User2DTO();
				user.setUid(rs.getString(1));
				user.setName(rs.getString(2));
				user.setBirth(rs.getString(3));
				user.setAddr(rs.getString(4));
				users.add(user);
			}
			System.out.println(users);
			CloseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public void updateUser2(User2DTO user) {}
	
	public void deleteUser2(String uid) {}
}
