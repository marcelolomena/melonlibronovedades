package cl.loso.melon.server.negocio;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.persistencia.EquipoLNDAO;

public class EquipoLNBO {
	private static Log log = LogFactory.getLog(EquipoLNBO.class);
	
	public static void guardarEquipoLN(String idNegocio,String nombre, String fecha) throws Exception {
		try {
			EquipoLNDAO.insertar(idNegocio,nombre,fecha);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	public static List<EquipoLN> obtenerEquipoLN(String idNegocio) throws Exception {
		try {
			NegocioLN negocio= NegocioLNBO.editarNegocioLN(idNegocio);
			return EquipoLNDAO.obtener(negocio);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	public static List<EquipoLN> obtenerEquipoLN() throws Exception {
		try {
			return EquipoLNDAO.obtener();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}	
	
	public static EquipoLN editarEquipoLN(String idNegocio,String idEquipo) throws Exception {
		try {
			return EquipoLNDAO.getEquipoLNbyId(Long.valueOf(idNegocio),Long.valueOf(idEquipo));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;

		}
	}	
	
	public static void actualizarEquipoLN(String idNegocio, String idEquipo, String nombre, String fecha) throws Exception {
		try {
			EquipoLNDAO.actualizar(Long.valueOf(idNegocio), Long.valueOf(idEquipo), nombre, fecha);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void borrarEquipoLN(String idNegocio,String idEquipo) throws Exception {
		try {
			EquipoLNDAO.borrar(Long.parseLong(idNegocio),Long.parseLong(idEquipo));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}	
}
