<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VRaptor Tree Blank Project</title>
</head>
<body>
	<form method="post" action="<c:url value='/tree/save'/>">
	<input type="hidden" name="tree.id" value="${tree.id}">
	<br/>
	<label for="tree.title">Nome da Árvore:</label>
	<br/>
	<input type="text" name="tree.title" value="${tree.title}">
	<br/>
	<label for="tree.title">Nome da pessoa principal:</label>
	<br/>
	<input type="text" name="person.name" value="${person.name}">
	<br/>
	<input type="submit" value="Salvar">
	</form>
	
</body>
</html>