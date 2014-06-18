<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VRaptor Blank Project</title>
</head>
<body>
	<h1>List of Candidates to Merge.</h1>
	
	<form method="post" action="<c:url value='/merge/save'/>">
	<table>
		<tr>
			<td>Person1</td>
			<td>Person2</td>
			<td>Status</td>
		</tr>
	<c:forEach items="${candidates}" var="candidate">
		<tr>
			<td>${candidate.person1.name}</td>
			<td>${candidate.person2.name}</td>
			<td><select name="status[]">
			  <option value="none"></option>
			  <option value="accept">accept</option>
			  <option value="reject">reject</option>
			  <option value="maybe">maybe</option>
			</select></td>
		</tr>
	</c:forEach>
	</table>
	
	<!-- <input type="hidden" name="ids" value="${candidates_ids}"> -->
	<input type="submit" value="Save">
	</form>
</body>
</html>