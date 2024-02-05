<%@page import="kr.co.jaboard1.DTO.TermsDTO"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="kr.co.jaboard1.db.sql"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String host = "jdbc:mysql://127.0.0.1:3306/Jboard";
	String user = "naimjae";
	String pass = "abc1234";
	
	TermsDTO dto = null;
	//
	try {
		Context initCtx = new InitialContext();
	    Context envCtx = (Context) initCtx.lookup("java:comp/env");
	    DataSource ds = (DataSource) envCtx.lookup("jdbc/jboard");
	    Connection conn = ds.getConnection();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql.SELECT_TERMS);
		
		if (rs.next()) {
			dto = new TermsDTO();
			dto.setTerms(rs.getString(1));
			dto.setPrivacy(rs.getString(2));
		}
		
		conn.close();
		stmt.close();
		rs.close();
	
	}catch (Exception e) {
		e.printStackTrace();
	}
%>

<%@ include file="./_header.jsp" %>
	<main>
	    <section class="terms">
	        <table>
	            <caption>사이트 이용약관</caption>
	            <tr>
	                <td>
	                    <textarea readonly>
							<%= dto.getTerms() %>
						</textarea>
	                    <p>
	                        <label><input type="checkbox" name="agree1"> 동의합니다.</label>
	                    </p>
	                </td>
	            </tr>
	        </table>
	        <table>
	            <caption>개인정보 취급방침</caption>
	            <tr>
	                <td>
	                    <textarea readonly>
							<%= dto.getPrivacy() %>
						</textarea>
	                    <p>
	                        <label><input type="checkbox" name="agree2"> 동의합니다.</label>
	                    </p>
	                </td>
	            </tr>
	        </table>
	        <div>
	            <a href="/Jboard1/User/login.jsp" class="btnCancel">취소</a>
	            <a href="/Jboard1/User/register.jsp" class="btnNext">다음</a>
	        </div>
	    </section>
	</main>
<%@ include file="./_footer.jsp" %>