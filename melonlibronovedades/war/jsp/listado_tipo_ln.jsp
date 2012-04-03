<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		init();	
        $("#divItems").show("slow", function(){
        	listarTipoLN(false);
        });
		
		$("#btnAgregarItemLVS").click(function(mievento) {
			mievento.preventDefault();
			abreURL("/jsp/formulario_tipo_ln.jsp?accion=agregar", "Turnos", 55, 40, 30, 30);
		});		 
	});

	function editarTipoLN(id){
		abreURL("/jsp/formulario_tipo_ln.jsp?accion=editar&id="+id, "Turnos", 55, 40, 30, 30);
	}	
</script>
<body> 
<h2>Turnos</h2>
<div id="divItems" style="display:none;">
<table id="tabla-item" summary="LOSO">
	<thead>
		<tr>
		    <th class="rounded-company" align="center"></th>
			<th class="rounded">Descripción</th>
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

<a id="btnAgregarItemLVS" href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Agregar Turno</strong><span class="bt_green_r"></span></a>
</div>
<body> 