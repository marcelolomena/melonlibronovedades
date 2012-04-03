<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" media="all" href="../css/niceforms-default.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/validate.css" />
<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" />
<style type="text/css"> 
body { font-size: 62.5%; }
</style> 
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript">
<%
        out.print("var accion='" + request.getParameter("accion") + "';\n");
		out.print("var id='" + request.getParameter("id") + "';\n");
%>
</script>
<script type="text/javascript">
$(document).ready(function() {
	init();
	$("input:button, input:submit").button();

	if (accion == 'editar') {
		$("#divFormUsuario").show("slow", function(){
			editarUsuarioLVS2(id);
        });
		$('#titulo').html("Modificar");
	} else {
		$('#titulo').html("Nuevo Usuario");
		$("#divFormUsuario").fadeIn("slow");
		nuevoUsuarioLN();		
	}

	$("#userForm").validate({
	    rules: {
			nombres: { required: true, minlength: 2 },
			apepa: { required: true, minlength: 2 },
			apema: { required: true, minlength: 2 },
			email: { required: true, email: true },
			cargo: { required: true, minlength: 2 },
			idNegocio: { required: true}						
	    },
	    messages: {
	    	nombres: "ingresar un nombre",
	    	apepa: "falta apellido paterno",
	    	apema: "falta apellido materno",
	    	email: "no es un email válido",	
	    	cargo: "ingresar un cargo",
	    	idNegocio: "debe ingresar un negocio"	    		    	
	    },
	    errorElement: "div",
	    wrapper: "div",
	    errorPlacement: function (error, element) {
	        offset = element.offset();
	        error.insertBefore(element);
	        error.addClass('message');
	        error.css('position', 'absolute');
	        error.css('left', offset.left + element.outerWidth());
	        error.css('top', offset.top);
	    },
	    submitHandler: function (form) {
			 var formInput = $("#userForm").serialize(); 
			 if (accion == 'editar') {
				 actualizarUsuarioLVS(formInput);
			 }else if (accion == 'agregar'){
				 guardarUsuarioLVS(formInput);			 
			 }

	    }     
	});	

});
</script>	
</head>
<body> 	 
<form id="userForm">
<div id="divFormUsuario" style="display:none;"> 
<input type="hidden" name="id" id="id" value=""></input>
<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="titulo" ></legend>

<dl>
<dt><label for="nombres">Nombres</label></dt>
<dd><input type="text" name="nombres" id="nombres" size="30" /></dd>
</dl>

<dl>
<dt><label for="apepa">Apellido Paterno</label></dt>
<dd><input type="text" name="apepa" id="apepa" size="30" /></dd>
</dl>

<dl>
<dt><label for="apema">Apellido Materno</label></dt>
<dd><input type="text" name="apema" id="apema" size="30" /></dd>
</dl>

<dl>
<dt><label for="email">E-mail</label></dt>
<dd><input type="text" name="email" id="email" size="30" /></dd>
</dl>

<dl>
<dt><label for="cargo">Cargo</label></dt>
<dd><input type="text" name="cargo" id="cargo" size="30" /></dd>
</dl>

<dl>
<dt><label for="correo">Correo Novedades</label></dt>
<dd><input type="checkbox" name="correo" id="correo" value="1" /></dd>
</dl>

<dl>
<dt><label for="perfil">Perfil</label></dt>
<dd><select name="perfil" id="perfil"><option value="A">Administrador</option><option value="U">SPT</option><option value="X">Usuario</option></select></dd>
</dl>

<dl>
<dt><label for="idNegocio">Negocios</label></dt>
<dd><select name="idNegocio" id="idNegocio"></select></dd>
</dl>

<dl class="submit">
<input type="submit" name="submit" id="submit" value="Guardar"/>
</dl>

</fieldset>
</div>
</form>
</body> 
</html>