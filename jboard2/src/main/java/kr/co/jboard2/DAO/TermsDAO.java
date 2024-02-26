package kr.co.jboard2.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.DB.DBHelper;
import kr.co.jboard2.DB.SQL;
import kr.co.jboard2.DTO.TermsDTO;

public class TermsDAO extends DBHelper{
	private static TermsDAO instance = new TermsDAO();
	public static TermsDAO getInstance() {
		return instance;
	}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private TermsDAO() {}
	
	public void insertTerms() {}
	public TermsDTO selectTerms() {
		TermsDTO terms = new TermsDTO();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL.SELECT_TERMS);
			logger.info("selectTerms : "+SQL.SELECT_TERMS);
			if(rs.next()) {
				terms.setTerms(rs.getString(1));
				terms.setPrivacy(rs.getString(2));
				terms.setSms(rs.getString(3));
			}
			CloseAll();
		} catch (Exception e) {
			logger.error("selectTerms : " + e.getMessage());
		}
		return terms;
	}
	public void updateTerms() {}
	public void deleteTerms() {}
}
