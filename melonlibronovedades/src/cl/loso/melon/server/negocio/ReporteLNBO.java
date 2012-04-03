package cl.loso.melon.server.negocio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.FallaLN;
import cl.loso.melon.server.model.BusquedaML;
import cl.loso.melon.server.persistencia.FueraServicioLNDAO;
import cl.loso.melon.server.persistencia.NovedadLNDAO;

public class ReporteLNBO {
	
	private static Log log = LogFactory.getLog(ReporteLNBO.class);	
	
	public static List<FallaLN> reporteFallas(
			String fechaini, String fechater, String texto,String idEquipo) throws Exception {
		List<FallaLN> searchResults = null;

		try {

			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha_ini = (Date) formato.parse(fechaini);
			Date fecha_ter = (Date) formato.parse(fechater);

			searchResults = BusquedaML.busquedaFallas(fecha_ini,fecha_ter,texto,idEquipo);


		} catch (Exception e) {
			log.error("reporteMetaDetalleUsuarioLVS: " + e.getMessage());
			throw e;
		}

		return searchResults;
	}
	
	public static List<FallaLN> reporteFallasSinTexto(
			String fechaini, String fechater, String idEquipo) throws Exception {
		List<FallaLN> searchResults = null;

		try {

			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha_ini = (Date) formato.parse(fechaini);
			Date fecha_ter = (Date) formato.parse(fechater);
			searchResults=FueraServicioLNDAO.obtenerFallas(fecha_ini, fecha_ter, Long.valueOf(idEquipo));

		} catch (Exception e) {
			log.error("reporteMetaDetalleUsuarioLVS: " + e.getMessage());
			throw e;
		}

		return searchResults;
	}	
	
	public static List<BitacoraLN> reporteNovedades(
			String fechaini, String fechater, String texto,String idEquipo) throws Exception {
		List<BitacoraLN> searchResults = null;

		try {

			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha_ini = (Date) formato.parse(fechaini);
			Date fecha_ter = (Date) formato.parse(fechater);

			searchResults = BusquedaML.busquedaNovedades(fecha_ini, fecha_ter, texto,idEquipo);


		} catch (Exception e) {
			log.error("reporteMetaDetalleUsuarioLVS: " + e.getMessage());
			throw e;
		}

		return searchResults;
	}	
	
	public static List<BitacoraLN> reporteNovedadesSinTexto(
			String fechaini, String fechater, String idEquipo) throws Exception {
		List<BitacoraLN> searchResults = null;

		try {

			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha_ini = (Date) formato.parse(fechaini);
			Date fecha_ter = (Date) formato.parse(fechater);
			searchResults=NovedadLNDAO.obtenerNovedades2(fecha_ini, fecha_ter, Long.valueOf(idEquipo));
		} catch (Exception e) {
			log.error("reporteMetaDetalleUsuarioLVS: " + e.getMessage());
			throw e;
		}

		return searchResults;
	}		
}
