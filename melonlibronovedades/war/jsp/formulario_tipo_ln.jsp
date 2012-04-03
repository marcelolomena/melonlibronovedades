<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" media="all" href="../css/niceforms.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/validate.css" />
<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" /> 
<link rel="stylesheet" type="text/css" media="all" href="../css/timePicker.css" />
<style type="text/css"> 
body { font-size: 62.5%; }
</style> 
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
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
	$("#horini, #horter").timepicker({});
	$("input:button, input:submit").button();
		
	if (accion == 'editar') {
		$('#titulo').html("Modificar");
		$("#divFormItem").show("slow", function(){
			editarTipoLN2(id);
        });		
	} else {
		$('#titulo').html("Agregar");
		$("#divFormItem").fadeIn("slow");
	}

	$("#itemForm").validate({
	    rules: {
			tipo: { required: true, minlength: 2 },
			orden:{required:true,number:true}						
	    },
	    messages: {
	    	tipo: "ingresar un nombre",
	    	orden:"debe ingresar un número"    		    	
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
			 var formInput = $('form[id$=itemForm]').serialize();
			 if (accion == 'editar') {
				 actualizarTipoLN(formInput);	//no es lo mismo
			 }else if (accion == 'agregar'){
				 guardarTipoLN(formInput);			 
			 }
			  
	    }     
	});		

});
</script>	
</head>
<body>	
<form id="itemForm">
<div id="divFormItem" style="display:none;">  
<input type="hidden" name="id" id="id" value=""></input>
<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="titulo"></legend> 

<dy>
<ds><label for=tipo>Nombre Turno</label></ds>
<de><input type="text" name="tipo" id="tipo" size="20" /></de>
</dy> 

<dy>
<ds><label for="horini">Hora de Inicio</label></ds>
<de><input type="text" name="horini" id="horini" size="10" value="08:00" /></de>
</dy>

<dy>
<ds><label for="horter">Hora de termino</label></ds>
<de><input type="text" name="horter" id="horter" size="10" value="09:00" /></de>
</dy>

<dy>
<ds><label for="horter">Orden</label></ds>
<de><input type="text" name="orden" id="orden" size="2"/></de>
</dy>

<dy class="submit">
<input type="submit" name="submit" id="submit" value="Guardar"/>
</dy>

</fieldset>
</div>
</form>
</body>  
</html>