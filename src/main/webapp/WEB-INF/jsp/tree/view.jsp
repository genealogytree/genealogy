<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("id") %>'>Add person</a>
<table>
  <thead>
    <tr>
      <th>Person</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${personList}" var="person">
      <tr>
        <td>${person.name}</td>
        <!-- <td>
        	<a href="<c:url value='/tree/form'/>?id=${person.id}">Edit</a>
        	<a href="<c:url value='/tree/delete'/>?id=${person.id}">Delete</a>        
        </td> -->
      </tr>          
    </c:forEach>
  </tbody>
</table>

</body>
</html>