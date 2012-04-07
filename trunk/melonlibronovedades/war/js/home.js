$(document).ready(function() {
    $('body').append(
            $('<div>').attr('id', 'loading').append(
                $('<img>').attr('src', '../images/ajax_loading.gif').attr('alt', 'Cargando...')
              ).css({
                  position: 'fixed',
                  display: 'none'
              })
    );
});
$(function () {
	$("#menu11").click(function(mievento) {
		mievento.preventDefault();
		 $('#der_contenido').load('/jsp/listado_tipo_ln.jsp');
	});
	$("#menu12").click(function(mievento) {
		mievento.preventDefault();
		 $('#der_contenido').load('/jsp/listado_usuario_ln.jsp');
	});	
	$("#menu13").click(function(mievento) {
		mievento.preventDefault();
		$('#der_contenido').load('/jsp/listado_negocio_ln.jsp');
	});
	$("#menu14").click(function(mievento) {
		mievento.preventDefault();
		abreURL('/jsp/reporte_admin_ln.jsp?idUsuario='+uid, "Reporte de Novedades y Fuera de Servicio", 5, 5, 30, 30);
	});	
	$("#menu15").click(function(mievento) {
		repoHome("/equiposhome","#novedades");
	});
	$("#menu21").click(function(mievento) {
		mievento.preventDefault();
		$('#der_contenido').load('/jsp/listado_fueraservicio_usuario.jsp?idUsuario='+uid);
	});	
	$("#menu23").click(function(mievento) {
		mievento.preventDefault();
		abreURL('/jsp/reporte_usuario_ln.jsp?idUsuario='+uid, "Reporte de Novedades y Fuera de Servicio", 5, 5, 30, 30);
	});		
	$("#menu32").click(function(mievento) {
		mievento.preventDefault();
		$('#der_contenido').load('/jsp/listado_novedades_usuario.jsp?idUsuario='+uid);
	});
	$("#der_contenido").show("slow", function(){
		repoHome("/novedadeshome","#novedades");
    });	
});