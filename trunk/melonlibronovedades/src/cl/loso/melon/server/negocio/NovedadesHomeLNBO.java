package cl.loso.melon.server.negocio;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cl.loso.melon.server.model.NovedadLN;
import cl.loso.melon.server.persistencia.NovedadLNDAO;

public class NovedadesHomeLNBO {
	private static Log log = LogFactory.getLog(NovedadesHomeLNBO.class);

	public static List<NovedadLN> obtenerNovedadesHome(String idUsuario)
			throws Exception {
		try {
			return NovedadLNDAO.obtener(idUsuario);
		} catch (Exception e) {
			log.error("VisitaLVSBO" + e.getMessage());
			throw e;
		}
	}
}
