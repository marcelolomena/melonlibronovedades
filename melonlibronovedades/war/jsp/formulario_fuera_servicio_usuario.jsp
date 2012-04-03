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
		function Nom(form,boton){
		    form.botPress.value = boton;
		    $("#visitaForm").submit(); 
		}	
       
		$("#idUsuario").attr('value', idUsuario);

		if (accion == 'editar') {
			$("#idListaEvento").attr('value', idListaEvento);
			$("#divFormVisita").show("slow", function(){
				editarFueraServicioUsuario(idUsuario,idListaEvento);
            });
			
			$('#titulo').html("Fuera de Servicio");
		} 	

		$( "#fechacompromiso" ).datepicker({
			showOn: "button",
			buttonImage: "../images/calendar.gif",
			buttonImageOnly: true
		});
		$( "#fechasolucion" ).datepicker({
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
    			fechacompromiso:{ fechachilena: true},
    			fechasolucion:{ fechachilena: true},
    	    	problema: { required: true, digitsAndLineBreak: true }
    	    },
    	    messages: {
    	    	fechacompromiso:'utilice el calendario',
    	    	fechasolucion:'utilice el calendario',
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
    			 var botPress=$("#botPress").val();
    			 if (botPress == 'guardar') {
    				 guardarFueraServicioUsuario(formInput,idUsuario);	
    			 }else if (botPress == 'cerrar'){
    				 cerrarFueraServicioUsuario(formInput,idUsuario);			 
    			 }

    	    }     
    	});	
	});
</script>
</head>
<body>   
<div id="divFormVisita" style="display:none;">         
<form id="visitaForm" style="margin-left:25px;margin-right:25px;width:90%">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<input type="hidden" name="idListaEvento" id="idListaEvento" value=""></input> 
<input type="hidden" name="botPress" id="botPress"></input>
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

<dl>
<dt><label for="fechacompromiso">Fecha Compromiso</label></dt>
<dd><input type="text" name="fechacompromiso" id="fechacompromiso" size="10" /></dd>
</dl>

<dl>
<dt><label for="fechasolucion">Fecha Solucion</label></dt>
<dd><input type="text" name="fechasolucion" id="fechasolucion" size="10" /></dd>
</dl>

<dl>
<dt><label for="comentario">Comentario</label></dt>
<dd><textarea name="comentario" id="comentario" rows="5" cols="50" maxlength="500"></textarea></dd>
</dl>



<div id="botonera" style="text-align:center">
<dl class="submit">
<dt>
<input type="button" name="operacion" value="guardar" id="tbnguardar" onclick="Nom(this.form,'guardar')" />
<input type="button" name="operacion" value="cerrar FC" id="btncerrar" onclick="Nom(this.form,'cerrar')" />
</dt>
</dl>
</div>


</fieldset>  
</form>
</div>
</body>
</html>