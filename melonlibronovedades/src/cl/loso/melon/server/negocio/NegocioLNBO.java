package cl.loso.melon.server.negocio;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.persistencia.NegocioLNDAO;

public class NegocioLNBO {
	private static Log log = LogFactory.getLog(NegocioLNBO.class);
	
	public static void guardarNegocioLN(NegocioLN negocioLN) throws Exception {
		try {

			NegocioLNDAO.insertar(negocioLN);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	public static List<NegocioLN> obtenerNegocioLN() throws Exception {
		try {
			return NegocioLNDAO.obtener();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static NegocioLN editarNegocioLN(String id) throws Exception {
		try {
			return NegocioLNDAO.getNegocioLNbyId(Long.valueOf(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;

		}
	}	
	
	public static void actualizarNegocioLN(NegocioLN newNegocioLN) throws Exception {
		try {
			NegocioLNDAO.actualizar(newNegocioLN);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void borrarNegocioLN(String id) throws Exception {
		try {
			NegocioLNDAO.borrar(Long.valueOf(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}	
}
