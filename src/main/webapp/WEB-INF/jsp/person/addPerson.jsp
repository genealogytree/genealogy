<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VRaptor Tree Blank Project</title>

    <script type='text/javascript'>
    function addFields(){
        // Container <div> where dynamic content will be placed
        var container = document.getElementById("container");
        var new_info_field = document.createElement("div");


if( typeof id_field == 'undefined' ) {
            id_field = 0;
}
     id_field++;

        new_info_field.setAttribute("id",id_field);
        container.appendChild(new_info_field);
        // Append a node with a random text
        new_info_field.appendChild(document.createTextNode("Info "));
        // Create an <input> element, set its type and name attributes
        var input = document.createElement("input");
        input.type = "text";
        input.name = "infotypes";
        new_info_field.appendChild(input);
        
        var input2 = document.createElement("input");
        input2.type = "text";
        input2.name = "infos";
        new_info_field.appendChild(input2);
        
        var delbutton = document.createElement("IMG");
        var link = document.createElement("a");
        link.setAttribute('href','#');
        link.setAttribute('id','removeinfo');
        link.setAttribute('onClick','removeField('+id_field+')');
        
        delbutton.setAttribute('src', "http://www.laps.ufpa.br/professores/html/img/x.png");
        link.appendChild(delbutton);
        new_info_field.appendChild(link);
        //Append a line break 
        new_info_field.appendChild(document.createElement("br"));
    }
    
    function removeField(id) {
    	var container = document.getElementById("container");
    	container.removeChild(document.getElementById(id));
    	
    }
    </script>
    
</head>
<body>
	<form method="post" action="<c:url value='/person/save'/>">
	<label for="${person.id}" >Id</label>
	<input type="text" name="person.id" value="${person.id}">
	<br/>
	<label for="${person.name}" >Name</label>
	<input type="text" name="person.name" value="${person.name}">
	<br/>
	<br/>
	<a href="#" id="addinfo" onclick="addFields()">Insert Info</a>
    <div id="container"></div>

	<input type="hidden" name="tree.id" value="${tree.id}">
	<input type="submit" value="Salvar">
	</form>
</body>
</html>