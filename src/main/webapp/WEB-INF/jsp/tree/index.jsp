<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VRaptor Tree Blank Project</title>
</head>
<body>
	The tree works!! ${variable} ${linkTo[IndexController].index}
<table>
  <thead>
    <tr>
      <th>Nome</th>
      <th>Endereço</th>
      <th>Latitudo</th>
      <th>Longitude</th>
      <th>Ações</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${treelist}" var="tree">
      <tr>
        <td>${tree.title}</td>
        <td>
        	<a href="produtos/formulario?id=${tree.id}">Editar</a>
        	<a href="produtos/remove?id=${tree.id}">Remover</a>        
        </td>
      </tr>          
    </c:forEach>
  </tbody>
</table>
</body>
</html>