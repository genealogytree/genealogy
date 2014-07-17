<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search People</title>
</head>
<body>
<form method="post" action="">
	Search: <input type="submit" value=">>" />
	<p>
	Name:
	<input type="text" name="name" value="${name}" />
	</p>
	<p> Similarity: 
		<input type="radio" name="similarity" value="${equal}" />Exact
		<input type="radio" name="similarity" value="${high}" />High
		<input type="radio" name="similarity" value="${low}" checked="checked"/>Low
	</p>
	<p>
		Sex:
		<input type="radio" name="sex" value="A" checked="checked">Any
		<input type="radio" name="sex" value="${male}">M
		<input type="radio" name="sex" value="${female}">F
	</p>	
</form>
<br/>

<table>
  <thead>
    <tr>
      <th>Name</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${personList}" var="person">
      <tr>
        <td>${person.name}</td>
        <td><a href='<c:url value="/tree/view/"/>${person.tree.id}/${person.id}'>View</a></td>
      </tr>          
    </c:forEach>
  </tbody>
</table>
</body>
</html>