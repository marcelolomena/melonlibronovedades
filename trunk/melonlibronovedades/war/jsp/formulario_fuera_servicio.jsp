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
<script type="text/javascript" src="../js/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript" src="../js/jquery.autoresize-min.js"></script>
<script type="text/javascript">
<%
        out.print("var accion='" + request.getParameter("accion") + "';\n");
		out.print("var idUsuario='" + request.getParameter("idUsuario") + "';\n");
		out.print("var idListaEvento='" + request.getParameter("idListaEvento") + "';\n");
%>
</script>

<script type="text/javascript">
	
	$(document).ready(function() {
		init();
		
		$("input:button, input:submit").button();
        
		$("#idUsuario").attr('value', idUsuario);

		if (accion == 'editar') {
			$("#idListaEvento").attr('value', idListaEvento);
			$("#divFormVisita").show("slow", function(){
				editarFueraServicio(idUsuario,idListaEvento);
            });
			
			$('#titulo').html("Modificar Fuera de Servicio");
		} else {
			$("#fecha").val(hoydia());
			$("#divFormVisita").show("slow", function(){
				CargarFueraServicio(idUsuario);
            });			
						
			$('#titulo').html("Nuevo Fuera de Servicio");
		}	

		$( "#fecha" ).datepicker({
			showOn: "button",
			buttonImage: "../images/calendar.gif",
			buttonImageOnly: true
		});
	      $('textarea[maxlength]').keyup(function () {
	          var limit = parseInt($(this).attr('maxlength'));
	          var text = $(this).val();
	          var chars = text.length;
	          if (chars > limit) {
	              var new_text = text.substr(0, limit);
	              $(this).val(new_text);
	          }
	      });		


		jQuery.validator.addMethod('fechachilena', function(value, element) {
			return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
		});
				
			
	      jQuery.validator.addMethod("digitsAndLineBreak", function (value, element) {
	          return /^[\d|a-zA-Z ·ÈÌÛ˙A…Õ”⁄—Ò|ø!°;,:\.\?#@()]+$/.test(value);
	      }); 		
	
    	$("#visitaForm").validate({
    	    rules: {
    	    	fecha:{ fechachilena: true},
    	    	problema: { required: true, digitsAndLineBreak: true }
    	    },
    	    messages: {
        	    fecha:'utilice el calendario',
        	    problema:"ingrese el problema"     	    	
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
    			 var formInput = $("input[type=text],select,input[type=hidden],textarea").serialize(); 
    			 if (accion == 'editar') {
    				 actualizarVisitaLVS(formInput,idUsuario);	
    			 }else if (accion == 'agregar'){
    				 guardarFueraServicio(formInput,idUsuario);			 
    			 }
    	    }     
    	});	
	});
</script>
<body>   
<div id="divFormVisita" style="display:none;">         
<form id="visitaForm">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<input type="hidden" name="idListaEvento" id="idListaEvento" value=""></input> 
<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="titulo"></legend>

<dl>  
<dt><label for="fecha">Fecha</label></dt>
<dd><input type="text" name="fecha" id="fecha" size="10" /></dd>
</dl>
<dl>
<dt><label for="idResponsable">Responsable</label></dt>
<dd><select name='idResponsable' id='idResponsable'></select></dd>
</dl>
<dl>
<dt><label for="idEquipo">Equipo</label></dt>
<dd><select name='idEquipo' id='idEquipo'></select></dd>
</dl>
<dl>
<dt><label for="problema">Falla</label></dt>
<dd><textarea name="problema" id="problema" rows="5" cols="50" maxlength="500"></textarea></dd>
</dl>
<dl class="submit">
<dt>
<input type="submit" name="submit" id="submit" value="Guardar" />
</dt>
</dl>
</fieldset>  
</form>
</div>
</body>