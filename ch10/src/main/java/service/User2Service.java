package service;

import java.util.List;

import dao.User2DAO;
import dto.User2DTO;

public class User2Service {
	// 싱글톤
	private static User2Service instance = new User2Service();
	public static User2Service getInstance() {
		return instance;
	}
	private User2Service() {}
	
	private User2DAO dao = User2DAO.getInstance();
	
	// 기본 CRUD 메서드
	public void insertUser2(User2DTO user) {
		dao.insertUser2(user);
	}
	
	public User2DTO selectUser2(String uid) {
		return dao.selectUser2(uid);
	}
	
	public List<User2DTO> selectUser2s() {
		return dao.selectUser2s();
	}
	
	public void updateUser2(User2DTO user) {
		dao.updateUser2(user);
	}
	
	public void deleteUser2(String uid) {
		dao.deleteUser2(uid);
	}
}
