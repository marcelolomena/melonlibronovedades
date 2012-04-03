<link rel="stylesheet" type="text/css" media="all" href="../css/validate.css" />
<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/timePicker.css" />
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
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript">
<%
        out.print("var accion='" + request.getParameter("accion") + "';\n");
%>
</script>
<script type="text/javascript">
$(function(){
	$("#horini, #horter").timepicker({});	
});
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

		if (accion == 'editar') {
			
		} else {
	
			$('#titulo').html("Nuevo detalle");
		}

    	$("#metaForm").validate({
    	    rules: {
    			nombre: { required: true},
    			orden:{required:true,number:true}
    	    },
    	    messages: {
    	   		nombre: "ingresar un nombre ",
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
   			 	var formInput = $('form[id$=metaForm]').serialize();
   			 	guardarMetaLVS(formInput,idUsuario,idNovedad);
    	    }     
    	});	        	

	});
</script>
<body>   
<div id="divFormMeta">  
<form id="metaForm" style="margin-left:50px;width:50%">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<input type="hidden" name="idNovedad" id="idNovedad" value=""></input>
<fieldset class="ui-widget ui-widget-content ui-corner-all">
<legend class="ui-widget ui-widget-header ui-corner-all" id="titulo"></legend>


<div style="float:left; width:50%;"> 
<label for="nombre">Nombressssssss</label>
</div>
<div style="float:left; width:50%;"> 
<input type="text" name="nombre" id="nombre" size="20" />
</div>
<div style="clear:both; padding:0px; margin:0px;"></div>

<div style="float:left; width:50%;"> 
<label for="horini">Hora de Inicio</label>
</div>
<div style="float:left; width:50%;"> 
<input type="text" name="horini" id="horini" size="10" value="08:00" />
</div>
<div style="clear:both; padding:0px; margin:0px;"></div>

<div style="float:left; width:50%;"> 
<label for="horter">Hora de termino</label>
</div>
<div style="float:left; width:50%;"> 
<input type="text" name="horter" id="horter" size="10" value="09:00" />
</div>
<div style="clear:both; padding:0px; margin:0px;"></div>

<div style="float:left; width:50%;"> 
<label for="horter">Orden</label>
</div>
<div style="float:left; width:50%;"> 
<input type="text" name="orden" id="orden" />
</div>
<div style="clear:both; padding:0px; margin:0px;"></div>


<p style="text-align:center"><input type="submit" name="submit" id="submit" value="Guardar" /></p>
</fieldset>
</form>
</div>
</body>       