<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VRaptor Tree Blank Project</title>    
</head>
<body>
	<form method="post" action="<c:url value='/person/save'/>">
	<input type="hidden" name="relation_id" value="${relation_id}" />
	<input type="hidden" name="relation_type" value="${relation_type}" />
	<input type="hidden" name="person.id" value="${person.id}">
	<label for="${person.id}" >Id</label>
	<br/>
	<label for="${person.name}" >Name</label>
	<input type="text" name="person.name" value="${person.name}">
    <br/>
	<br/>
	<c:forEach items="${types}" var="type">
	${type.type}<br />
	<input type="text" name="idxs[]" value="${type.id}" /><br />
	Data - Hora:<br />
	<input type="text" name="datas[]" /><br />
	Local:<br />
	<input type="text" name="places[]" /><br />
	Descri&ccedil;&atilde;o:<br />
	<input type="text" name="descriptions[]" /><br /><br />	
	</c:forEach>

	<input type="hidden" name="tree.id" value="${tree.id}">
	<input type="submit" value="Salvar">
	</form>
</body>
</html>