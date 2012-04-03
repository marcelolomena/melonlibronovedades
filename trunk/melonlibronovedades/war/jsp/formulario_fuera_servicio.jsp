<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/validate.css" />
<style type="text/css"> 
body { font-size: 62.5%; }
label { display: inline-block; width: 100px; }
legend { padding: 0.5em; }
fieldset fieldset label { display: block; }
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
		$("input:button, input:submit").button();
        $('body').append(
                $('<div>').attr('id', 'loading').append(
                    $('<img>').attr('src', '../images/ajax_loading.gif').attr('alt', 'Cargando...')
                  ).css({
                      position: 'fixed',
                      display: 'none'
                  })
        );
        
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
<form id="visitaForm" style="margin-left:25px;margin-right:25px;width:90%">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<input type="hidden" name="idListaEvento" id="idListaEvento" value=""></input> 
<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="titulo"></legend>
<p><label for="fecha">Fecha:</label><input type="text" name="fecha" id="fecha" size="10" /></p>
<p><label for="idResponsable">Responsable:</label><select name='idResponsable' id='idResponsable'></select></p>
<p><label for="idEquipo">Equipo:</label><select name='idEquipo' id='idEquipo'></select></p>
<p><label for="problema">Falla:</label><textarea name="problema" id="problema" rows="5" cols="50" maxlength="500"></textarea></p>
<br>
<p style="text-align:center"><input type="submit" name="submit" id="submit" value="Guardar" /></p>
</fieldset>  
</form>
</div>
</body>