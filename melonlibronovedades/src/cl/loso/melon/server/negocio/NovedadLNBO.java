package cl.loso.melon.server.negocio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.NovedadLN;
import cl.loso.melon.server.model.TipoLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.persistencia.NovedadLNDAO;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class NovedadLNBO {
	private static Log log = LogFactory.getLog(NovedadLNBO.class);

	public static List<NovedadLN> obtenerNovedadLN(String idUsuario)
			throws Exception {
		try {
			return NovedadLNDAO.obtener(idUsuario);
		} catch (Exception e) {
			log.error("VisitaLVSBO" + e.getMessage());
			throw e;
		}
	}

	public static void borrarNovedadLN(String idUsuario,String idNovedad) throws Exception {
		try {
			NovedadLNDAO.borrar(Long.valueOf(idUsuario), Long.valueOf(idNovedad));
		} catch (Exception e) {
			log.error("VisitaLVSBO" + e.getMessage());
			throw e;
		}
	}

	public static List<Entity> obtenerNovedadLN(String idUsuario,
			String idNovedad) throws Exception {
		try {
			return NovedadLNDAO.obtenerFoto(idUsuario, idNovedad);
		} catch (Exception e) {
			log.error("VisitaLVSBO" + e.getMessage());
			throw e;
		}
	}

	public static void guardarNovedadLN(String fecha, String idUsuario,
			String idTurno, String empleado, Hashtable<Long, String> ht)
			throws Exception {
		try {
			DateFormat formato1 = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha_novedad = (Date) formato1.parse(fecha);
			UsuarioLN usuario = UsuarioLNBO.editarUsuarioLN(idUsuario);
			TipoLN turno = TipoLNBO.editarTipoLN(idTurno);
			NegocioLN negocio = NegocioLNBO.editarNegocioLN(String
					.valueOf(usuario.getIdNegocio()));
			NovedadLN novedad = new NovedadLN(fecha_novedad, negocio.getId(),
					negocio.getNombre(), usuario, turno.getId(), turno
							.getDescripcion(), empleado);

			long tiempoInicio = System.currentTimeMillis();

			List<NovedadLN> novedades = new ArrayList<NovedadLN>();
			novedades.add(novedad);
			usuario.setNovedades(novedades);
			List<BitacoraLN> comentarios = new ArrayList<BitacoraLN>();

			Enumeration<Long> e = ht.keys();
			while (e.hasMoreElements()) {
				Long idEquipo = e.nextElement();
				EquipoLN equipo = EquipoLNBO.editarEquipoLN(String
						.valueOf(((UsuarioLN) UsuarioLNBO
								.editarUsuarioLN(idUsuario)).getIdNegocio()),
						String.valueOf(idEquipo));
				String comentario = ht.get(idEquipo);
				Text texto=new Text(comentario);
				
				comentarios.add(new BitacoraLN(fecha_novedad, texto,
						idEquipo, equipo.getNombre(), turno.getId(), turno
								.getDescripcion(), negocio.getId(), negocio
								.getNombre(),usuario.getNombres() + " " + usuario.getApepa() + " " + usuario.getApema()));
			}
			novedad.setComentarios(comentarios);
			NovedadLNDAO.insertar(usuario, novedad);

			long totalTiempo = System.currentTimeMillis() - tiempoInicio;
			log.info("El tiempo de demora es en insertar una novedad es:"
					+ totalTiempo + " miliseg");
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	public static void actualizarNovedadLN(String fecha, String idUsuario,
			String idNovedad, String idTurno, String empleado,
			Hashtable<Long, String> ht) throws Exception {
		try {
			DateFormat formato1 = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha_novedad = (Date) formato1.parse(fecha);

			NovedadLNDAO.actualizar(Long.valueOf(idUsuario), Long
					.valueOf(idNovedad), fecha_novedad, Long.valueOf(idTurno),
					empleado, ht);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	public static NovedadLN editarNovedadLN(String idUsuario, String idVisita)
			throws Exception {
		try {
			NovedadLN novedad = NovedadLNDAO.getNovedadLNbyId(Long
					.valueOf(idUsuario), Long.valueOf(idVisita));

			return novedad;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;

		}
	}
}
