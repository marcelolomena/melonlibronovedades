<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript">
<%
		out.print("var idUsuario='" + request.getParameter("idUsuario") + "';\n");
%>
</script>
<script type="text/javascript">
	$(document).ready(function() {
		listarVisitasLVS(true,idUsuario)
	});
</script>