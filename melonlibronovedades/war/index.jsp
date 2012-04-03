<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<html>
  <body>
<%
try {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
    	response.sendRedirect("jsp/login");
    } else {
    	 response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
    }
}catch (Exception e){
	System.out.println("Murio : " + e.getMessage());
}
%>
  </body>
</html>