package cl.loso.melon.server.persistencia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import cl.loso.melon.server.gae.PMF;
import cl.loso.melon.server.model.FallaLN;
import cl.loso.melon.server.model.ListaEventoLN;
import cl.loso.melon.server.model.SolucionLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.pojos.FueraServicio;
import cl.loso.melon.server.pojos.FueraServicioUsuario;

public class FueraServicioLNDAO {

	private static Log log = LogFactory.getLog(FueraServicioLNDAO.class);

	@SuppressWarnings("unchecked")
	public static List<ListaEventoLN> obtener(String idUsuario) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UsuarioLN usuario = null;
		List<ListaEventoLN> detached = null;
		Query query = null;
		try {
			usuario = pm
					.getObjectById(UsuarioLN.class, Long.valueOf(idUsuario));
			query = pm.newQuery(ListaEventoLN.class);
			query.setFilter("usuario == pusuario");
			query.setOrdering("fecha DESC");// DESC
			query.declareParameters("UsuarioLN pusuario");
			detached = (List<ListaEventoLN>) pm
					.detachCopyAll((List<ListaEventoLN>) query.execute(usuario));

		} catch (Exception e) {
			log.error("DAO " + e.getMessage());
		} finally {
			pm.close();
		}
		return detached;

	}

	public static List<ListaEventoLN> obtenerEventosPendientes() {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		List<ListaEventoLN> detached = null;

		try {

			Map<String, Object> pars = new HashMap<String, Object>();
			String queryBuffer = "estado == :est && idUsuarioPadre != :id";
			pars.put("est", "P");
			pars.put("id", new Long(0));
			Query query = pm.newQuery(ListaEventoLN.class);
			query.setFilter(queryBuffer);
			detached = (List<ListaEventoLN>) pm
					.detachCopyAll((List<ListaEventoLN>) (query
							.executeWithMap(pars)));

		} catch (Exception e) {
			log.error("DAO " + e.getMessage());
		} finally {
			pm.close();
		}
		return detached;

	}

	public static FueraServicio getListaEventoLNbyId(Long idUsuario,
			Long idListaEvento) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ListaEventoLN evento = null;
		FueraServicio fueraServicio = null;
		try {
			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuario);
			Key k2 = KeyFactory.createKey(k1, ListaEventoLN.class
					.getSimpleName(), idListaEvento);
			evento = pm.getObjectById(ListaEventoLN.class, k2);

			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"dd/MM/yyyy");
			String fecha = sdf.format(evento.getFecha());
			fueraServicio = new FueraServicio(fecha, evento.getFalla()
					.getProblema(), String.valueOf(evento.getIdResponsable()),
					String.valueOf(evento.getFalla().getEquipo()));

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return fueraServicio;

	}

	public static FueraServicioUsuario getFueraServicioUsuario(Long idUsuario,
			Long idListaEvento, Long idUsuarioResponsable,
			Long idListaEventoUsuarioResponsable) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ListaEventoLN evento, evento1 = null;
		FueraServicioUsuario fueraServicioUsuario = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			log.info("idUsuario : " + idUsuario);
			log.info("idListaEvento : " + idListaEvento);

			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuario);
			Key k2 = KeyFactory.createKey(k1, ListaEventoLN.class
					.getSimpleName(), idListaEvento);
			evento = pm.getObjectById(ListaEventoLN.class, k2);
			log.info("evento1 : " + evento.getEstado());

			log.info("idUsuarioResponsable : " + idUsuarioResponsable);
			log.info("idListaEventoUsuarioResponsable : "
					+ idListaEventoUsuarioResponsable);
			Key k11 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuarioResponsable);
			Key k22 = KeyFactory.createKey(k11, ListaEventoLN.class
					.getSimpleName(), idListaEventoUsuarioResponsable);
			evento1 = pm.getObjectById(ListaEventoLN.class, k22);

			log.info("evento2 : " + evento1.getEstado());
			SolucionLN solucion = evento1.getSolucion();
			
			FallaLN falla=evento.getFalla();
			
			String problema="";
			if(falla!=null){
				problema=falla.getProblema();
			}
			Long equipo=null;
			String equipoTxt="";
			
			if(falla!=null){
				equipo=falla.getEquipo();
			}
			if(equipo!=null){
				equipoTxt=String.valueOf(equipo);
			}

			if (solucion != null) {
				Date fechaCompromiso = solucion.getFechaCompromiso();
				Date fechaSolucion = solucion.getFechaSolucion();
				String fechaCompromisoTxt = "";
				String fechaSolucionTxt = "";
				if (fechaCompromiso != null) {
					fechaCompromisoTxt = sdf.format(fechaCompromiso);
				}
				if (fechaSolucion != null) {
					fechaSolucionTxt = sdf.format(fechaSolucion);
				}

				fueraServicioUsuario = new FueraServicioUsuario(sdf
						.format(evento.getFecha()), problema, String.valueOf(evento
						.getIdResponsable()), equipoTxt, evento.getEstado(), fechaCompromisoTxt,
						fechaSolucionTxt, solucion.getComentario());
			} else {
				fueraServicioUsuario = new FueraServicioUsuario(sdf
						.format(evento.getFecha()), problema, String.valueOf(evento
						.getIdResponsable()), equipoTxt, evento.getEstado(), "", "", "");
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return fueraServicioUsuario;

	}

	public static FallaLN getFallaLNbyId(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		FallaLN falla, detached = null;
		try {
			falla = pm.getObjectById(FallaLN.class, id);
			detached = pm.detachCopy(falla);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return detached;

	}

	public static ListaEventoLN getPadre(Long idUsuario, Long idListaEvento) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ListaEventoLN evento, detached = null;

		try {
			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuario);
			Key k2 = KeyFactory.createKey(k1, ListaEventoLN.class
					.getSimpleName(), idListaEvento);
			evento = pm.getObjectById(ListaEventoLN.class, k2);
			detached = pm.detachCopy(evento);

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return detached;
	}

	public static void cambiarEstado(Long idEventoPadre, Long idUsuarioPadre,
			Long idEventoHijo, Long idUsuarioHijo) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = null;
		com.google.appengine.api.datastore.Transaction tx = null;

		try {
			datastore = DatastoreServiceFactory.getDatastoreService();

			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuarioPadre);
			Key k2 = KeyFactory.createKey(k1, ListaEventoLN.class
					.getSimpleName(), idEventoPadre);
			Key k3 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuarioHijo);
			Key k4 = KeyFactory.createKey(k3, ListaEventoLN.class
					.getSimpleName(), idEventoHijo);

			tx = datastore.beginTransaction();

			ListaEventoLN eventoPadre = pm.getObjectById(ListaEventoLN.class,
					k2);
			eventoPadre.setEstado("T");
			tx.commit();

			tx = datastore.beginTransaction();
			ListaEventoLN eventoHijo = pm
					.getObjectById(ListaEventoLN.class, k4);
			eventoHijo.setEstado("T");
			tx.commit();

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			if (!pm.isClosed()) {
				pm.close();
			}
		}

	}

	public static void guardar(UsuarioLN usuario, ListaEventoLN evento,
			Date fecha_ingreso, UsuarioLN responsable) {

		Transaction tx = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			tx = pm.currentTransaction();
			tx.begin();
			pm.makePersistent(usuario);
			pm.makePersistent(evento);
			long id = evento.getKey().getId();
			long idUsuarioPapa = usuario.getId();

			tx.commit();

			tx.begin();
			ListaEventoLN evento2 = new ListaEventoLN(fecha_ingreso, id,
					idUsuarioPapa, "P", responsable.getId());
			List<ListaEventoLN> eventos = new ArrayList<ListaEventoLN>();
			eventos.add(evento2);
			responsable.setEventos(eventos);
			pm.makePersistent(responsable);
			pm.makePersistent(evento2);
			tx.commit();
		} catch (Exception e) {
			log.error("insertar " + e.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			if (!pm.isClosed()) {
				pm.close();
			}
		}

	}

	public static void guardarUsuario(String fechacompromiso,
			String fechasolucion, String idUsuario, String idListaEvento,
			String comentario) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ListaEventoLN evento = null;
		Date fecha_compromiso = null;
		Date fecha_solucion = null;
		DateFormat formato1 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (fechacompromiso != null) {
				fecha_compromiso = (Date) formato1.parse(fechacompromiso);
			}

			if (fechasolucion != null) {
				fecha_solucion = (Date) formato1.parse(fechasolucion);
			}

			UsuarioLN usuario = UsuarioLNDAO.getUsuarioLNbyId(Long
					.valueOf(idUsuario));

			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					usuario.getId());

			Key k2 = KeyFactory.createKey(k1, ListaEventoLN.class
					.getSimpleName(), Long.valueOf(idListaEvento));

			evento = pm.getObjectById(ListaEventoLN.class, k2);

			SolucionLN solucion = evento.getSolucion();

			if (solucion == null) {
				SolucionLN solu = new SolucionLN(fecha_compromiso,
						fecha_solucion, comentario);
				evento.setSolucion(solu);

				pm.makePersistent(evento);
			} else {
				solucion.setComentario(comentario);
				solucion.setFechaCompromiso(fecha_compromiso);
				solucion.setFechaSolucion(fecha_solucion);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}

	}

	public static List<FallaLN> obtenerFallas(Date fecha_ini, Date fecha_ter,
			Long idEquipo) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<FallaLN> detached = null;
		Query query = null;
		String queryStr = null;
		try {
			Map<String, Object> pars = new HashMap<String, Object>();

			if (idEquipo.intValue() > 0) {
				queryStr = "fecha >= :fechaini && fecha <= :fechater && equipo == :idequipo";
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);
				pars.put("idequipo", idEquipo);
			} else if (idEquipo.intValue() == 0) {
				queryStr = "fecha >= :fechaini && fecha <= :fechater";
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);
			}

			query = pm.newQuery(FallaLN.class);
			query.setFilter(queryStr);

			detached = (List<FallaLN>) pm.detachCopyAll((List<FallaLN>) (query
					.executeWithMap(pars)));

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return detached;
	}
}
