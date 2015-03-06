<%@page import="java.sql.ResultSet"%>
<%@page import="javaStuff.DBStartup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="index.css">
<title>My first JSP</title>
</head>
<body>
	<div class="container" id="form">
	<form action="/Hello/" method="POST">
		Insert Message: <input type="text" name="message"> 
		<input type="submit">
	</form>
	</div>
	<div class="container">
		<h2 class="center">Demo Table</h2>
		<table id="resultsTable" class="center">
			<tr>
				<th>Row</th>
				<th>Message</th>
			</tr>
			<%
				ResultSet resultSet = DBStartup.getDEMOTABLE();
				while (resultSet.next()) {
					%>
					<tr>
					<td><%= resultSet.getRow() %></td>
					<td><%= resultSet.getString("MESSAGE") %></td>
					</tr>
			<%	}
			%>
		</table>
	</div>
</body>
</html>