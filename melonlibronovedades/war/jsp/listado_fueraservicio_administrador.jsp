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
        
		$("#divMetas").show("slow", function(){
			$("#idUsuario").attr('value', idUsuario);			
			listarFueraLN(false,idUsuario);
        });

	});
	
	$("#btnAgregarMetaLVS").click(function(mievento) {
		mievento.preventDefault();
		var idUsuario=$('#idUsuario').val();
		abreURL("/jsp/formulario_fuera_servicio.jsp?accion=agregar&idUsuario=" + idUsuario, "Agregar Fuera de Servicio", 35, 40, 30, 30);
	});	

	function editarFueraServicio(idUsuario,idListaEvento,idpadre){
		var idUsuario=$('#idUsuario').val();
		if(idpadre==0){
			abreURL("/jsp/formulario_fuera_servicio.jsp?accion=editar&idUsuario="+idUsuario+"&idListaEvento=" + idListaEvento, "Editar Fuera Servicio", 35, 10, 30, 30);
		}else{
			abreURL("/jsp/formulario_fuera_servicio_usuario.jsp?accion=editar&idUsuario="+idUsuario+"&idListaEvento=" + idListaEvento, "Fuera Servicio", 35, 10, 30, 30);

		}
	}			
</script>
<body> 
<h2 id="titulo">Lista eventos Fuera de Servicio</h2>

<div id="divMetas" style="display:none;">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<input type="hidden" name="idNovedad" id="idNovedad" value=""></input>
<table id="tabla-metas" summary="LOSO">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th class="rounded">Fecha</th>
			<th class="rounded">Estado</th>
			<th class="rounded-q4">Editar</th>
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

<div class="clear"></div>
<a id="btnAgregarMetaLVS" href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Agregar Fuera Servicio</strong><span class="bt_green_r"></span></a>


</div>
</body> 