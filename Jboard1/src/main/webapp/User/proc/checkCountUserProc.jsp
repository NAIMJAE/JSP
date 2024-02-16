<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="kr.co.jaboard1.DAO.UserDAO"%>
<%@ page contentType="application/json;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String type = request.getParameter("type");
	String value = request.getParameter("value");

	UserDAO dao = UserDAO.getInstance();
	int result = dao.selectCountUser(type, value);

	// JSON 출력
	JsonObject json = new JsonObject();
	json.addProperty("result", result);
	json.addProperty("type", type);
	out.print(json.toString());
%>