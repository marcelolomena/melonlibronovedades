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
		out.print("var idNegocio='" + request.getParameter("idNegocio") + "';\n");
		out.print("var idEquipo='" + request.getParameter("idEquipo") + "';\n");		
%>
</script>
<script type="text/javascript">
$(document).ready(function() {
	init();
	$("input:button, input:submit").button();
	$("#idNegocio").attr('value', idNegocio);
	
	if (accion == 'editar') {
		$("#idEquipo").attr('value', idEquipo);
		$('#leyenda').html("Modificar Equipo LN");		
		$("#divFormSitio").show("slow", function(){
			editarSitioLVS2(idNegocio,idEquipo);
        });		
	} else {
		$('#leyenda').html("Nuevo Equipo LN");
		$("#divFormSitio").fadeIn("slow");		
	}

	$("#sitioForm").validate({
	    rules: {
			nombre: { required: true, minlength: 2 }
	    },
	    messages: {
	    	nombre: "ingresar un equipo"
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
			 var formInput = $('form[id$=sitioForm]').serialize();
			 if (accion == 'editar') {
				 actualizarSitioLVS(formInput,idNegocio);	
			 }else if (accion == 'agregar'){
				 guardarSitioLVS(formInput,idNegocio);			 
			 }
	    }     
	});	

});
</script>
</head>	
<body>	 
<div id="divFormSitio" style="display:none;"> 
<form id="sitioForm">
<input type="hidden" name="idNegocio" id="idNegocio" value=""></input>
<input type="hidden" name="idEquipo" id="idEquipo" value=""></input>
<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="leyenda"></legend>  

<dl> 
<dt><label for="nombre">Equipo</label></dt>
<dd><input type="text" name="nombre" id="nombre" size="40" /></dd>
</dl>

<dl class="submit">
<input type="submit" name="submit" id="submit" value="Guardar"/>
</dl>

</fieldset>
</form>
</div>
</body>
</html>