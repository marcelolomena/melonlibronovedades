<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" media="all" href="../css/niceforms-default.css" />
<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/validate.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/fileuploader.css" />
<style type="text/css"> 

body { font-size: 62.5%; }
	
/* Pesta�as verticales 
----------------------------------*/
.ui-tabs-vertical { width: 55em; }
.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-selected { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 40em;}

</style> 

<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript" src="../js/fileuploader.js"></script>
<script type="text/javascript">
<%
        out.print("var accion='" + request.getParameter("accion") + "';\n");
		out.print("var idUsuario='" + request.getParameter("idUsuario") + "';\n");
		out.print("var idVisita='" + request.getParameter("idVisita") + "';\n");		
%>
</script>
<script type="text/javascript">
	$(document).ready(function() {
	    $('body').append(
	            $('<div>').attr('id', 'loading').append(
	                $('<img>').attr('src', '../images/ajax_loading.gif').attr('alt', 'Cargando...')
	              ).css({
	                  position: 'fixed',
	                  display: 'none'
	              })
	    );	
		$("input:button, input:submit").button();
		/*
	      $('textarea[maxlength]').keyup(function () {
	          var limit = parseInt($(this).attr('maxlength'));
	          var text = $(this).val();
	          var chars = text.length;
	          if (chars > limit) {
	              var new_text = text.substr(0, limit);
	              $(this).val(new_text);
	          }
	      }); 
		*/
		$("#idUsuario").attr('value', idUsuario);
		$("#accion").attr('value', accion);
		if (accion == 'editar') {
			$("#idVisita").attr('value', idVisita);
			$("#divFormVisita").show("slow", function(){
				editarVisitaLVS2(idUsuario,idVisita);
            });
			
			$('#titulo').html("Modificar Novedad");
		} else {
			$("#fecha").val(hoydia());
			$("#divFormVisita").show("slow", function(){
				ingresoNovedad(idUsuario);
            });			
						
			$('#titulo').html("Novedad LN");
		}	
		
		$( "#fecha" ).datepicker({
			showOn: "button",
			buttonImage: "../images/calendar.gif",
			buttonImageOnly: true
		});

		jQuery.validator.addMethod('fechachilena', function(value, element) {
			return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
		});
		
    	$("#visitaForm").validate({
    	    rules: {
    	    	fecha:{ fechachilena: true}
    	    },
    	    messages: {
        	    fecha:'utilice el calendario'
  	    	
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
    	    	var formInput = $('form[id$=visitaForm]').serialize();
    			 if (accion == 'editar') {
    				 actualizarNovedad(formInput,idUsuario);	
    			 }else if (accion == 'agregar'){
    				 guardarNovedad(formInput,idUsuario);	
    			 }
    	    }     
    	});	
	});
</script>
</head>
<body>   
<div id="divFormVisita" style="display:none;">         
<form  id="visitaForm">                    
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<input type="hidden" name="idVisita" id="idVisita" value=""></input> 
<input type="hidden" name="accion" id="accion" value=""></input> 
<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="titulo"></legend>

<dl>
<dt><label for="fecha">Fecha</label></dt>
<dd><input type="text" name="fecha" id="fecha" size="10" /></dd>
</dl>

<dl>
<dt><label for="tipo">Turno</label></dt>
<dd><select name='tipo' id='tipo'></select></dd>
</dl>

<dl>
<dt><label for="empleado">Usuario:</label></dt>
<dd><input type="text" name="empleado" id="empleado" size="30" /></dd>
</dl>

<dl>
<div id="fotos"></div>
</dl>
<dl>
<div id="tabs"></div>
</dl>

<dl class="submit">
<input type="submit" name="submit" value="Guardar" />
</dl>

</fieldset>  
</form>
</div>
</body>
</html>