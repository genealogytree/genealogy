<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Genealogy tree list</title>
</head>
<body>
<a href="<c:url value='/tree/form'/>">Create new tree </a><br>
<a href="<c:url value='/gedcom/import'/>">Import tree from Gedcom file</a><br>
<br>

<table>
  <thead>
    <tr>
      <th>Tree</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${treeList}" var="tree">
      <tr>
        <td>${tree.title}</td>
        <td>
        	<a href="<c:url value='/tree/form'/>?id=${tree.id}">Edit</a>
        	<a href="<c:url value='/tree/delete'/>?id=${tree.id}">Delete</a>
        	<a href="<c:url value='/tree/view/${tree.id}/${tree.rootPerson.id}'/>">View</a>        
        </td>
      </tr>          
    </c:forEach>
  </tbody>
</table>
</body>
</html>