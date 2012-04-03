<%@ page language="java"  contentType="text/html" import="java.util.*"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
    <div class="logo"><img src="../images/logo_melon.gif" alt="" title="" border="0" /></div>
   
		<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		%>
    <div class="right_header">Bienvenido <%=user.getNickname() %><br /><a href="login" class="home">Inicio</a>,<a href="<%= userService.createLogoutURL("/jsp/logout") %>" class="logout">Salir</a></div> 
    <div class="melonln">Libro de Novedades</div>