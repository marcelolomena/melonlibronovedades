package cl.loso.melon.server.negocio;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.persistencia.BitacoraLNDAO;

public class BitacoraLNBO {
	private static Log log = LogFactory.getLog(BitacoraLNBO.class);
	
	
	public static List<BitacoraLN> obtenerBitacoraLN(String idUsuario,String idNovedad) throws Exception {
		try {
			return BitacoraLNDAO.obtener(idUsuario,idNovedad);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
}
