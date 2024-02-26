package service;

import java.util.List;
import dao.MemberDAO;
import dto.MemberDTO;

public class ServiceMember {
	// 싱글톤
		private static ServiceMember instance = new ServiceMember();
		public static ServiceMember getInstance() {
			return instance;
		}
		private ServiceMember() {}
		
		MemberDAO dao = MemberDAO.getInstance();
		
		// 기본 CRUD 메서드
		public void insertMember(MemberDTO member) {
			dao.insertMember(member);;
		}
		
		public MemberDTO selectMember(String uid) {
			return dao.selectMember(uid);
		}
		
		public List<MemberDTO> selectMembers() {
			return dao.selectMembers();
		}
		
		public void updateMember(MemberDTO member) {
			dao.updateMember(member);;
		}
		
		public void deleteMember(String uid) {
			dao.deleteMember(uid);
		}
}
