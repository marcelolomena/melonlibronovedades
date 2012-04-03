<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		init();
		$("#divNegocio").show("slow", function(){
			listarNegocioLVS(false);
        });
		
		$("#btnAgregarNegocioLVS").click(function(mievento) {
			mievento.preventDefault();
			abreURL("/jsp/formulario_negocio_lvs.jsp?accion=agregar", "Negocio", 50, 40, 30, 30);
		});		

		$("#btnAgregarSitioLVS").click(function(mievento) {
			mievento.preventDefault();
			var idNegocio=$('#idNegocio').val();
			abreURL("/jsp/formulario_sitio_lvs.jsp?accion=agregar&idNegocio=" + idNegocio, "Equipo", 50, 40, 30, 30);
		});

		$("#btnListarSitiosLVS").click(function(mievento) {
			mievento.preventDefault();
			$("#idNegocio").attr('value', $('input[name=idNegocio]:checked').val());
			if( $("#divNegocio").is(':visible') ){
                $("#divNegocio").fadeOut("slow");
            }
			$("#divSitio").show("slow", function(){
				listarSitioLVS(false,$('input[name=idNegocio]:checked').val());
            });
		});

		$("#btnListarNegocioLVS").click(function(mievento) {
			mievento.preventDefault();
			$('#titulo').html("Lista Negocio");

			if( $("#divSitio").is(':visible') ){
                $("#divSitio").fadeOut("slow");
            }            
			$("#divNegocio").show("slow", function(){
				listarNegocioLVS(false);
            });
		});

		$("#btnListarSitiosLVS2").click(function(mievento) {
			mievento.preventDefault();

			$("#idNegocio").attr('value', $('#idNegocio').val());
			if( $("#divArea").is(':visible') ){
                $("#divArea").fadeOut("slow");
            }
			$("#divSitio").show("slow", function(){
				listarSitioLVS(false,$('input[name=idNegocio]:checked').val());
            });
		});		

		$("#btnListarNegocioLVS2").click(function(mievento) {
			mievento.preventDefault();
			$('#titulo').html("Lista Negocio");

			if( $("#divArea").is(':visible') ){
                $("#divArea").fadeOut("slow");
            }            
			$("#divNegocio").show("slow", function(){
				listarNegocioLVS(false);
            });
		});		
				 
	});

	function editarNegocioLVS(id){
		abreURL("/jsp/formulario_negocio_lvs.jsp?accion=editar&id="+id, "Negocio", 50, 40, 30, 30);
	}
	
	function editarSitioLVS(id){
		var idNegocio=$('#idNegocio').val();
		abreURL("/jsp/formulario_sitio_lvs.jsp?accion=editar&idEquipo="+id+"&idNegocio=" + idNegocio, "Equipo", 50, 40, 30, 30);
	}
	
</script>
<body> 
<h2 id="titulo">Lista Negocio</h2>

<div id="divNegocio" style="display:none;">
<table id="rounded-corner" summary="LOSO">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th class="rounded">Nombre</th>
			<th align="center" class="rounded">Editar</th>
			<th align="center" class="rounded-q4">Borrar</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="3" class="rounded-foot-left"><em></em></td>
			<td class="rounded-foot-right">&nbsp;</td>

		</tr>
	</tfoot>
	<tbody>
	</tbody>
</table>

<a id="btnAgregarNegocioLVS" href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Agregar Negocio</strong><span class="bt_green_r"></span></a>
<a id="btnListarSitiosLVS" href="#" class="bt_blue"><span class="bt_blue_lft"></span><strong>Equipo</strong><span class="bt_blue_r"></span></a>
</div>

<div id="divSitio" style="display:none;">
<form>
<input type="hidden" name="idNegocio" id="idNegocio" value=""></input>
<table id="tabla-sitios" summary="LOSO">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th class="rounded">Nombre</th>
			<th align="center"  class="rounded">Editar</th>
			<th align="center"  class="rounded-q4">Borrar</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="3" class="rounded-foot-left"><em></em></td>
			<td class="rounded-foot-right">&nbsp;</td>

		</tr>
	</tfoot>
	<tbody>
	</tbody>
</table>
</form>
<a id="btnAgregarSitioLVS" href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Agregar Equipo</strong><span class="bt_green_r"></span></a>
<a id="btnListarNegocioLVS" href="#" class="bt_red"><span class="bt_red_lft"></span><strong>Negocios</strong><span class="bt_red_r"></span></a>
</div>

<body> 
