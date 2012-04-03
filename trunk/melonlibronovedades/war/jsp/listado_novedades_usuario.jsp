<script type="text/javascript">
<%
        out.print("var idUsuario='" + request.getParameter("idUsuario") + "';\n");
%>
</script>
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
 		init();	
		$("#btnAgregarVisitaLVS").click(function(mievento) {
			mievento.preventDefault();
			abreURL("/jsp/formulario_visita_lvs.jsp?accion=agregar&idUsuario=" + idUsuario, "Agregar Novedad", 10, 10, 30, 30);
		});				

		$("#divVisitas").show("slow", function(){
			listarVisitasLVS(false,idUsuario);
		});
	});

	function editarVisitaLVS(idUsuario,idVisita){
		abreURL("/jsp/formulario_visita_lvs.jsp?accion=editar&idUsuario="+idUsuario+"&idVisita=" + idVisita, "Editar novedad", 10, 10, 30, 30);
	}
</script>
<body> 
<h2 id="titulo">Lista Novedades</h2>

<div id="divVisitas" style="display:none;">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<table id="tabla-visitas" summary="LOSO">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th align="left" class="rounded">Fecha</th>
			<th align="left" class="rounded">Turno</th>			
			<th align="center" class="rounded">Editar</th>
			<th align="center" class="rounded-q4">Borrar</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="4" class="rounded-foot-left"><em></em></td>
			<td class="rounded-foot-right">&nbsp;</td>

		</tr>
	</tfoot>
	<tbody>
	</tbody>
</table>

<a id="btnAgregarVisitaLVS" href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Agregar Novedad</strong><span class="bt_green_r"></span></a>
</div>
</body> 