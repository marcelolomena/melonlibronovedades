<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.treeTable.js"></script>
<script type="text/javascript" src="../js/jquery.printarea.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		init();
        $("#divUsuarios").show("slow", function(){		
			listarUsuarioLVS(false);
        });
		
		$("#btnAgregarUsuarioLVS").click(function(mievento) {
			mievento.preventDefault();
			abreURL("/jsp/formulario_usuario_lvs.jsp?accion=agregar", "Agregar Usuario", 20, 20, 30, 30);
		});

		$("#btnAgregarMetaLVS").click(function(mievento) {
			mievento.preventDefault();
			var idUsuario=$('#idUsuario').val();
			abreURL("/jsp/formulario_fuera_servicio.jsp?accion=agregar&idUsuario=" + idUsuario, "Agregar Fuera de Servicio", 35, 40, 30, 30);
		});


		$("#btnListarFueraLN").click(function(mievento) {
			mievento.preventDefault();
			$('#titulo').html("Lista eventos Fuera de Servicio");
			$("#idUsuario").attr('value', $('input[name=idUsuario]:checked').val());
			if( $("#divUsuarios").is(':visible') ){
                $("#divUsuarios").fadeOut("slow");
            }
			$("#divMetas").show("slow", function(){
				listarFueraLN(false,$('input[name=idUsuario]:checked').val());
            });
		});						

		$("#btnAgregarVisitaLVS").click(function(mievento) {
			mievento.preventDefault();
			var idUsuario=$('#idUsuario').val();
			abreURL("/jsp/formulario_visita_lvs.jsp?accion=agregar&idUsuario=" + idUsuario, "Agregar Novedad", 10, 10, 30, 30);
		});				

		$("#btnListarUsuariosLVS").click(function(mievento) {
			mievento.preventDefault();
			$('#titulo').html("Lista Usuarios");

			if( $("#divMetas").is(':visible') ){
                $("#divMetas").fadeOut("slow");
            }
            
			$("#divUsuarios").show("slow", function(){
				listarUsuarioLVS(false);
			});

		});	

		$("#btnListarUsuariosLVS2").click(function(mievento) {
			mievento.preventDefault();
			$('#titulo').html("Lista Usuarios");

			if( $("#divVisitas").is(':visible') ){
                $("#divVisitas").fadeOut("slow");
            }

			$("#divUsuarios").show("slow", function(){
				listarUsuarioLVS(false);
			});

		});					

		$("#btnListarVisitasLVS").click(function(mievento) {
			mievento.preventDefault();
			$('#titulo').html("Lista Novedades");
			$("#idUsuario").attr('value', $('input[name=idUsuario]:checked').val());

			if( $("#divUsuarios").is(':visible') ){
                $("#divUsuarios").fadeOut("slow");
            }
			$("#divVisitas").show("slow", function(){
				listarVisitasLVS(false,$('input[name=idUsuario]:checked').val());
            });
		});

		$("#btnListarBitacora").click(function(mievento) {
			mievento.preventDefault();
			$('#titulo').html("Detalle de Novedades");
			$("#idNovedad").attr('value', $('input[name=idNovedad]:checked').val());

			if( $("#divVisitas").is(':visible') ){
                $("#divVisitas").fadeOut("slow");
            }
			$("#divMetas").show("slow", function(){
				listarMetasLVS(false,$('#idUsuario').val(),$('input[name=idNovedad]:checked').val());
            });
		});
	});

	function editarUsuarioLVS(id){
		abreURL("/jsp/formulario_usuario_lvs.jsp?accion=editar&id="+id, "Editar Usuario", 30, 30, 30, 30);
	}
	function editarVisitaLVS(idUsuario,idVisita){
		var idUsuario=$('#idUsuario').val();
		abreURL("/jsp/formulario_visita_lvs.jsp?accion=editar&idUsuario="+idUsuario+"&idVisita=" + idVisita, "Editar novedad", 35, 10, 30, 30);
	}
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
<h2 id="titulo">Lista Usuarios</h2>
<div id="divUsuarios" style="display:none;">
<form>
<table id="tabla-usuarios" summary="LOSO">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th class="rounded">Nombre</th>
			<th class="rounded">Apellido</th>
			<th class="rounded">Cargo</th>
			<th align="center" class="rounded">Editar</th>
			<th align="center" class="rounded-q4">Borrar</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="5" class="rounded-foot-left"><em></em></td>
			<td class="rounded-foot-right">&nbsp;</td>

		</tr>
	</tfoot>
	<tbody>
	</tbody>
</table>
</form>
<a id="btnAgregarUsuarioLVS" href="#" class="bt_green"><span class="bt_green_lft"></span><strong>Agregar usuario</strong><span class="bt_green_r"></span></a>
<a id="btnListarVisitasLVS"  href="#" class="bt_red"><span class="bt_red_lft"></span><strong>Novedades</strong><span class="bt_red_r"></span></a>
<a id="btnListarFueraLN" href="#" class="bt_blue"><span class="bt_blue_lft"></span><strong>Fuera Servicio</strong><span class="bt_blue_r"></span></a>
</div>


<div id="divMetas" style="display:none;">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<input type="hidden" name="idNovedad" id="idNovedad" value=""></input>
<table id="tabla-metas" summary="LOSO">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th class="rounded">Fecha</th>
			<th class="rounded">Estado</th>
			<th align="center" align="center" class="rounded-q4">Editar</th>
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
<a id="btnListarUsuariosLVS" href="#" class="bt_blue"><span class="bt_blue_lft"></span><strong>Usuarios</strong><span class="bt_blue_r"></span></a>
</div>

<div id="divVisitas" style="display:none;">
<input type="hidden" name="idUsuario" id="idUsuario" value=""></input>
<table id="tabla-visitas" summary="LOSO">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th class="rounded">Fecha</th>
			<th class="rounded">Turno</th>			
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
<a id="btnListarUsuariosLVS2" href="#" class="bt_blue"><span class="bt_blue_lft"></span><strong>Usuarios</strong><span class="bt_blue_r"></span></a>
</div>
</body> 