<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Import tree from Gedcom</title>
</head>
<body>
	<form action="<c:url value='/gedcom/upload'/>" method="post" enctype="multipart/form-data">
	Import tree from GEDCOM: 
	<br />
	<label>Tree title: </label>
	<input type="text" name="tree.title"/> <br />
	<input type="file" name="gedcom" size="60" /> <br />
	<input type="submit" value="Import" />
	</form>
	<br />
	
</body>
</html>