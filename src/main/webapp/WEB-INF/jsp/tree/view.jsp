<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
  <thead>
    <tr>
      <td>Person:</td>
      <td>${person.name}</td>
      <td> <a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("tree_id") %>&person_id=${person.id}'>Alterar</a></td>
    </tr>
  </thead>
  <tbody>
      <tr>
        <td>
      
      <td> Adicionar/Visualizar:</td>      
      <td> <a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("tree_id") %>&relation_id=${person.id}&relation_type=F'>Pai</a></td>
      <td> <a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("tree_id") %>&relation_id=${person.id}&relation_type=M'>Mãe</a></td>
      <td> <a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("tree_id") %>&relation_id=${person.id}&relation_type=C'>Filho(a)</a></td>
      <td> <a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("tree_id") %>&relation_id=${person.id}&relation_type=S'>Esposo(a)</a></td>
      </tr>          
  </tbody>
</table>


<c:set var="i" value="0" />
<c:forEach begin="0" end="${klevel}" var="k">
	<c:set var="n" value="1" />
	<c:forEach begin="1" end="${k}" var="l">
		<c:set var="n" value="${n*2}" />
	</c:forEach>
	
	<c:set var="sex_m" value="${true}" />
	<c:forEach begin="1" end="${n}" var="l">
		<c:choose>
			<c:when test="${people[i]!=null}">
				<a href='<c:url value="/tree/view/"/><%= request.getParameter("tree_id") %>/${people[i].id}' >${people[i].name}</a>
			</c:when>
			<c:otherwise>
				<c:set var="filho" value="${(i/2)-((i+1)%2)}" />
				<c:if test="${people[filho]!=null}">
					<c:if test="${sex_m}">
						<a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("tree_id") %>&relation_id=${people[filho].id}&relation_type=F'>Pai</a>				
					</c:if>
					<c:if test="${sex_m == false}">
						<a href='<c:url value="/person/addPerson?tree_id="/><%= request.getParameter("tree_id") %>&relation_id=${people[filho].id}&relation_type=M'>Mãe</a>				
					</c:if>
				</c:if>				
			</c:otherwise>
		</c:choose>
		<c:set var="sex_m" value="${sex_m ? false : true}" />
		<c:set var="i" value="${i+1}" />	
	</c:forEach>	
	
	<hr />
	
</c:forEach>

<p>Children</p>
<table>
<c:forEach items="${children}" var="child">
	<tr><td>
	<a href='<c:url value="/tree/view/"/><%= request.getParameter("tree_id") %>/${child.id}' >${child.name}</a>
	</td></tr>
</c:forEach>
</table>

<p>Spouses</p>
<table>
<c:forEach items="${spouses}" var="spouse">
	<tr><td>
	<a href='<c:url value="/tree/view/"/><%= request.getParameter("tree_id") %>/${spouse.id}' >${spouse.name}</a>
	</td></tr>
</c:forEach>
</table>

</body>
</html>