<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" media="all" href="../css/niceforms-default.css" />
<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/validate.css" />
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
		$('#leyenda').html("Modificar Negocio LN");		
		$("#divFormNegocio").show("slow", function(){
			editarNegocioLVS2(id);
        });		
	} else {
		$('#leyenda').html("Nuevo Negocio LN");
		$("#divFormNegocio").fadeIn("slow");
	}

	$("#negocioForm").validate({
	    rules: {
			nombre: { required: true, minlength: 2 }
	    },
	    messages: {
	    	nombre: "ingresar un negocio"
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
			 var formInput = $('form[id$=negocioForm]').serialize();
			 if (accion == 'editar') {
				 actualizarNegocioLVS(formInput);	
			 }else if (accion == 'agregar'){
				 guardarNegocioLVS(formInput);			 
			 }
	    }     
	});	
});
</script>
</head>	
<body>	 
<div id="divFormNegocio" style="display:none;"> 
<form id="negocioForm">
<input type="hidden" name="id" id="id" value=""></input>

<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="leyenda"></legend>    
 
<dl>   
<dt><label for="nombre">Negocio</label></dt>
<dd><input type="text" name="nombre" id="nombre" size="40" /></dd>
</dl>

<dl class="submit">
<dt>
<input type="submit" name="submit" id="submit" value="Guardar"/>
</dt>
</dl>

</fieldset>
</form>
</div>
</body>
</html>