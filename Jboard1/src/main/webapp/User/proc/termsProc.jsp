<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String chk1 = request.getParameter("chk1");
	String chk2 = request.getParameter("chk2");
	String sms = request.getParameter("sms");
	
	// 데이터베이스 처리
	
	
	
	// 세션 처리
	try{
		if(chk1.equals("on") && chk2.equals("on")) {
			
			session.setAttribute("agree", true);
			
			if(sms != null) {
				session.setAttribute("sms", "Y");
			}else {
				session.setAttribute("sms", "N");
			}
			
			response.sendRedirect("/Jboard1/User/register.jsp");
		}else {
			response.sendRedirect("/Jboard1/User/terms.jsp?code=300");
		}
	}catch (Exception e) {
		e.printStackTrace();
		response.sendRedirect("/Jboard1/User/terms.jsp?code=300");
	}
%>