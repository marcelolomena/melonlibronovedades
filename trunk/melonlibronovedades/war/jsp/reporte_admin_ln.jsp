<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
<%
        out.print("var idUsuario='" + request.getParameter("idUsuario") + "';\n");
%>
</script>
<link rel="stylesheet" type="text/css" href="../css/style.css" />
<link rel="stylesheet" type="text/css" href="../css/start/jquery-ui-1.8.13.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/jquery.tablesorter-update.css" />
<link rel="stylesheet" type="text/css" href="../css/toolbars.css" />
<link rel="stylesheet" type="text/css" media="all" href="../css/validate.css" />
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/jquery.tablesorter-update.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
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
	
    $("#divReporteMeta").show("slow", function(){	
    	$("#fechaini").val(hoydia());
    	$("#fechater").val(hoydia());
		mostrarComboEquipo(idUsuario);
    });    
    	
	$("button").button();
	$("#radio0").button();
	$("#radio1").button();
	
	$('button[id=btn_exportar]').button({ disabled: true });
 	
	$("#btn_buscar").click(function(e){	
		if($.trim($('#fechaini').val()).length==0){
			Alerta('Ingrese una fecha de inicio');
		} else if($.trim($('#fechater').val()).length==0){
			Alerta('Ingrese una fecha de termino');
		}else{
	    	var formInput = $('form[id$=reporteForm]').serialize();

	    	$("#divTableReporteMeta").show("slow", function(){
	    		var value = $('[name="tipo"]:checked').val();
	    		if(value==0){
	    			ReporteFallas(formInput);
	    		}else if(value==1){
	    			ReporteNovedad(formInput);
	    		}
	        });	

		}	
		e.preventDefault();
		e.stopPropagation();
	});
	
    $("#btn_exportar").click(function (e) {
		$("#tipo").attr('value', $('[name="tipo"]:checked').val());  
        var formInput = $("#reporteForm").serialize();
        var url="jsp/reporteFallasPDF?"+formInput;
		abreURL(url, "Reporte PDF", 10, 10, 30, 30);
        
        e.preventDefault();
        e.stopPropagation();
    }); 	
    
	$("#fechaini").datepicker({
		showOn: "button",
		buttonImage: "../images/calendar.gif",
		buttonImageOnly: true
	});

	$("#fechater").datepicker({
		showOn: "button",
		buttonImage: "../images/calendar.gif",
		buttonImageOnly: true
	});	
   
});
</script>
</head>
<body>
<div id="divReporteMeta" style="display:none;">
<form id="frmExportarResultados" action="jsp/reporteFallasPDF" method="post">
<input type="hidden" id="dataResultados" name="dataResultados" />
</form>
<form id="reporteForm" action="jsp/reporteFallasPDF" method="post">
<div class="fg-toolbar ui-widget-header ui-corner-all ui-helper-clearfix">
<div class="fg-buttonset ui-helper-clearfix">
<input type="hidden" id="idUsuario" />
<input type="hidden" id="tipo" />
<label for="fechaini">Desde</label><input name="fechaini" id="fechaini"size="8"/>
<label for="fechater">Hasta</label><input name="fechater" id="fechater" size="8"/>
<label for="equipos">Equipos:</label><select name='idEquipo' id='equipos'></select>
<label for="texto">Texto</label><input name="texto" id="texto" size="20"/>
<label for="radio0">Fuera Servicio</label><input type="radio" name="tipo" id="radio0" value="0" checked/><label for="radio1">Novedad</label><input type="radio" name="tipo" id="radio1" value="1"/>
<button id="btn_buscar">Buscar</button>
<button id="btn_exportar">Exportar</button>
</div>
</div>

<div id="divTableReporteMeta" style="display:none;" class="ui-widget" style="margin-top:2em; font-family:Arial">
<table id="tblReporteMeta" cellspacing="1" border ="0" class="tablesorter">
<thead>
</thead>
<tbody>
</tbody>
</table>
</div>
</form>
</div>
</body>
</html>
