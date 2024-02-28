package dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DBHelper;
import dto.MemberDTO;

public class MemberDAO extends DBHelper{
	// 싱글톤
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	// 로거 생성
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 기본 CRUD 메서드
	public void insertMember(MemberDTO member) {
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("INSERT INTO `Member` VALUES (?,?,?,?,?,?)");
			psmt.setString(1, member.getUid());
			psmt.setString(2, member.getName());
			psmt.setString(3, member.getHp());
			psmt.setString(4, member.getPos());
			psmt.setInt(5, member.getDep());
			psmt.setString(6, member.getRdate());
			logger.info("psmt : " + psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.info("insertMember() : " + e.getMessage());
		}
	}
	
	public MemberDTO selectMember(String uid) {
		MemberDTO member = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("SELECT * FROM `Member` WHERE `uid`=?");
			psmt.setString(1, uid);
			logger.info("psmt : " + psmt);
			rs = psmt.executeQuery();
			if(rs.next()) {
				member = new MemberDTO();
				member.setUid(rs.getString(1));
				member.setName(rs.getString(2));
				member.setHp(rs.getString(3));
				member.setPos(rs.getString(4));
				member.setDep(rs.getInt(5));
				member.setRdate(rs.getString(6));
			}
			CloseAll();
		} catch (Exception e) {
			logger.info("selectMember() : "+e.getMessage());
		}
		return member;
	}
	
	public List<MemberDTO> selectMembers() {
		List<MemberDTO> members = new ArrayList<MemberDTO>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("SELECT * FROM `Member`");

			logger.info("psmt : " + psmt);
			
			rs = psmt.executeQuery();
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setUid(rs.getString(1));
				member.setName(rs.getString(2));
				member.setHp(rs.getString(3));
				member.setPos(rs.getString(4));
				member.setDep(rs.getInt(5));
				member.setRdate(rs.getString(6));
				members.add(member);
			}
			CloseAll();
			
		} catch (Exception e) {
			logger.error("selectMember() : " + e.getMessage());
		}
		return members;
	}
	
	public void updateMember(MemberDTO member) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("UPDATE `Member` SET `name`=?,`hp`=?,`pos`=?,`dep`=?,`rdate`=? WHERE `uid`=?");
			psmt.setString(1, member.getName());
			psmt.setString(2, member.getHp());
			psmt.setString(3, member.getPos());
			psmt.setInt(4, member.getDep());
			psmt.setString(5, member.getRdate());
			psmt.setString(6, member.getUid());
			logger.info("psmt : " + psmt);
			psmt.executeUpdate();
			CloseAll();
		} catch (Exception e) {
			logger.info("updateMember() : " + e.getMessage());
		}
	}
	
	public void deleteMember(String uid) {
	
	}
}
