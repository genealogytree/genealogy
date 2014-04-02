<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VRaptor Tree Blank Project</title>
</head>
<body>
	The tree works!! ${variable} ${linkTo[IndexController].index}
	
	<form action="<c:url value='/tree/save'/>">
	<input type="text" name="tree.id">
	<br/>
	<input type="text" name="tree.title">
	<br/>
	<input type="submit" value="Salvar">
	</form>
	
</body>
</html>