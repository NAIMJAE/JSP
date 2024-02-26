package kr.co.jboard2.DAO;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DB.DBHelper;
import kr.co.jboard2.DB.SQL;
import kr.co.jboard2.DTO.UserDTO;

public class UserDAO extends DBHelper {
	private static UserDAO instance = new UserDAO();
	public static UserDAO getInstance() {
		return instance;
	}
	private UserDAO() {}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
// 기본 CRUD 메서드
	// 회원가입(register.do)
	public void insertUser(UserDTO userDTO) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.INSERT_USER);
			psmt.setString(1, userDTO.getUid());
			psmt.setString(2, userDTO.getPass());
			psmt.setString(3, userDTO.getName());
			psmt.setString(4, userDTO.getNick());
			psmt.setString(5, userDTO.getEmail());
			psmt.setString(6, userDTO.getHp());
			psmt.setString(7, userDTO.getZip());
			psmt.setString(8, userDTO.getAddr1());
			psmt.setString(9, userDTO.getAddr2());
			psmt.setString(10, userDTO.getRegip());
			psmt.setString(11, userDTO.getSms());
			
			logger.info("psmt : "+psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.error("insertUser" + e.getMessage());
		}
	}
	
	public UserDTO selectUser(String uid) {
		UserDTO user = new UserDTO();
		return user;
	}
	
	public List<UserDTO> selectUsers() {
		List<UserDTO> users = new ArrayList<UserDTO>();
		return users;
	}
	
	public void updateUser(UserDTO userDTO) {}
	
	public void deleteUser(String uid) {}
	
// 사용자 정의 CRUD 메서드
	// 회원가입 중복 검사 메서드
	public int selectCountUser(String type, String value) {
		int result = 0;
		StringBuilder sql = new StringBuilder(SQL.SELECT_COUNT_USER);
		if(type.equals("uid")) {
			sql.append(SQL.WHERE_UID);
		}else if(type.equals("nick")) {
			sql.append(SQL.WHERE_NICK);
		}else if(type.equals("hp")) {
			sql.append(SQL.WHERE_HP);
		}else if(type.equals("email")) {
			sql.append(SQL.WHERE_EMAIL);
		}
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, value);

			logger.info("psmt : " + psmt);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectCountUser" + e.getMessage());
		}
		return result;
	}	
	
	// 로그인 확인 메서드
	public UserDTO selectUserForLogin(String uid, String pass) {
		UserDTO users = new UserDTO();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL.SELECT_USER_FOR_LOGIN);
			psmt.setString(1, uid);
			psmt.setString(2, pass);
			
			logger.info("psmt : "+psmt);
			rs = psmt.executeQuery();
			if(rs.next()) {
				users.setUid(rs.getString(1));
				users.setPass(rs.getString(2));
				users.setName(rs.getString(3));
				users.setNick(rs.getString(4));
				users.setEmail(rs.getString(5));
				users.setHp(rs.getString(6));
				users.setRole(rs.getString(7));
				users.setZip(rs.getString(8));
				users.setAddr1(rs.getString(9));
				users.setAddr2(rs.getString(10));
				users.setRegip(rs.getString(11));
				users.setAgree1(rs.getInt(12));
				users.setSms(rs.getString(13));
				users.setRdate(rs.getString(14));
				users.setLeaveDate(rs.getString(15));
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectUserForLogin" + e.getMessage());
		}
		return users;
	}
}
