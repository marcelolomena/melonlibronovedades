package cl.loso.melon.server.negocio;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.persistencia.UsuarioLNDAO;

public class UsuarioLNBO {
	private static Log log = LogFactory.getLog(UsuarioLNBO.class);
	
	public static void guardarUsuarioLN(String nombres,
			String apepa,
			String apema,
			String email,
			char perfil,
			String cargo,
			String idNegocio,
			String correo) throws Exception {
		try {

			UsuarioLNDAO.insertar(nombres.toLowerCase(),
					apepa.toLowerCase(),
					apema.toLowerCase(),
					email.toLowerCase(),
					perfil,
					cargo.toLowerCase(),
					Long.valueOf(idNegocio),
					correo);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static List<UsuarioLN> obtenerUsuarioLN() throws Exception {
		try {
			return UsuarioLNDAO.obtener();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static List<UsuarioLN> obtenerUsuarioNegocioLN(String idNegocio) throws Exception {
		try {
			return UsuarioLNDAO.buscarUsuarioPorNegocio(idNegocio);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}	
	
	public static UsuarioLN editarUsuarioLN(String idUsuario) throws Exception {
		try {
			return UsuarioLNDAO.getUsuarioLNbyId(Long.valueOf(Long.parseLong(idUsuario)));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;

		}
	}	
	
	public static void actualizarUsuarioLN(String id,
			String nombres,
			String apepa,
			String apema,
			String email,
			char perfil,
			String cargo,
			String idNegocio,String correo) throws Exception {
		try {
			UsuarioLNDAO.actualizar(Long.valueOf(id),
					 nombres.toLowerCase(),
					 apepa.toLowerCase(),
					 apema.toLowerCase(),
					 email.toLowerCase(),
					 perfil,
					 cargo.toLowerCase(),
					 Long.valueOf(idNegocio),
					 correo);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public static void borrarUsuarioLN(String id) throws Exception {
		try {
			UsuarioLNDAO.borrar(Long.valueOf(Long.parseLong(id)));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	public static List<UsuarioLN> obtenerUsuarioPorEmailLVS(String email) throws Exception {
		try {
			return UsuarioLNDAO.buscarUsuarioPorEmail(email);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}		
}
