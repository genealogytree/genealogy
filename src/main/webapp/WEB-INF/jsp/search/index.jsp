<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search People</title>
</head>
<body>
<form method="post" action="">
Search:
<br />
<input type="text" name="name" value="${name}" />
${equal}
<br /><br />
<input type="submit" value="Search" />
</form>

</body>
</html>