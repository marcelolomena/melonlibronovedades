package cl.loso.melon.server.persistencia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.gae.PMF;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.NovedadLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.util.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;

public class BitacoraLNDAO {

	private static Log log = LogFactory.getLog(BitacoraLNDAO.class);

	@SuppressWarnings("unchecked")
	public static List<BitacoraLN> obtener(String idUsuario, String idNovedad) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		NovedadLN novedad = null;
		List<BitacoraLN> detached = null;
		Query query = null;
		try {
			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					Long.valueOf(idUsuario));
			Key k2 = KeyFactory.createKey(k1, NovedadLN.class.getSimpleName(),
					Long.valueOf(idNovedad));
			novedad = pm.getObjectById(NovedadLN.class, k2);
			query = pm.newQuery(BitacoraLN.class);
			query.setFilter("novedad == pnovedad");
			query.setOrdering("equipo ASC");
			query.declareParameters("NovedadLN pnovedad");
			detached = (List<BitacoraLN>) pm
					.detachCopyAll((List<BitacoraLN>) query.execute(novedad));

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return detached;

	}

	@SuppressWarnings("unchecked")
	public static List<BitacoraLN> obtenerNovedadHome(Long idNegocio,
			Date fecha_ini, Date fecha_ter) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<BitacoraLN> bitacoraList = null;
		Query query = null;

		try {
			Map<String, Object> pars = new HashMap<String, Object>();
			String queryStr = "fecha >= :fechaini && fecha <= :fechater && negocio == :negocio";
			pars.put("fechaini", fecha_ini);
			pars.put("fechater", fecha_ter);
			pars.put("negocio", idNegocio);
			query = pm.newQuery(BitacoraLN.class);
			query.setFilter(queryStr);
			query.setOrdering("fecha DESC");// DESC
			bitacoraList = (List<BitacoraLN>) pm
					.detachCopyAll((List<BitacoraLN>) query
							.executeWithMap(pars));

		} catch (Exception e) {
			log.error("DAO " + e.getMessage());
		} finally {
			pm.close();

		}
		return bitacoraList;

	}

	public static List<BitacoraLN> obtenerNovedadHome2(Long idNegocio,
			Date fecha_ini, Date fecha_ter) throws Exception {
		List<BitacoraLN> bitacoraList = new ArrayList<BitacoraLN>();
		try {

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
					BitacoraLN.class.getSimpleName());

			q.addFilter("fecha", FilterOperator.GREATER_THAN_OR_EQUAL,
					fecha_ini);

			q.addFilter("fecha", FilterOperator.LESS_THAN_OR_EQUAL, fecha_ter);

			q.addFilter("negocio", FilterOperator.EQUAL, idNegocio);

			q.addSort("fecha", SortDirection.DESCENDING);
			q.addSort("equipoNombre", SortDirection.ASCENDING);

			FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

			List<Entity> entities = datastore.prepare(q).asList(fetchOptions);
			for (Entity entity : entities) {
				Text comentario = (Text) entity.getProperty("comentario");
				if (comentario.getValue() != null
						&& comentario.getValue().trim().length() > 0) {
					bitacoraList.add(new BitacoraLN(entity));
				}

			}

		} catch (Exception e) {
			log.error("DAO obtenerNovedadHome2:" + e.getMessage());
			throw e;
		}
		return bitacoraList;
	}

	public static List<BitacoraLN> obtenerNovedadesAyer() {

		List<BitacoraLN> bitacoraList = new ArrayList<BitacoraLN>();
		try {

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
					BitacoraLN.class.getSimpleName());

			/*
			Calendar cal1 = new GregorianCalendar();
			cal1.add(Calendar.DATE, -2);
			cal1.set(Calendar.HOUR_OF_DAY, 23);
			cal1.set(Calendar.MINUTE, 59);
			cal1.set(Calendar.SECOND, 59);
			cal1.set(Calendar.MILLISECOND, 999);
			Date ayer1 = cal1.getTime();

			Calendar cal2 = new GregorianCalendar();
			cal2.add(Calendar.DATE, -1);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);
			Date ayer2 = cal2.getTime();
			*/
			Calendar cal1 = new GregorianCalendar();
			cal1.add(Calendar.DATE, -2);
			cal1.set(Calendar.HOUR_OF_DAY, 8);
			cal1.set(Calendar.MINUTE, 00);
			cal1.set(Calendar.SECOND, 00);
			cal1.set(Calendar.MILLISECOND, 000);
			Date ayer1 = cal1.getTime();

			Calendar cal2 = new GregorianCalendar();
			//cal2.add(Calendar.DATE, -1);
			cal2.set(Calendar.HOUR_OF_DAY, 8);
			cal2.set(Calendar.MINUTE, 00);
			cal2.set(Calendar.SECOND, 00);
			cal2.set(Calendar.MILLISECOND, 000);
			Date ayer2 = cal2.getTime();			

			log.info("reporte novedades diario ayer : " + ayer1);
			log.info("reporte novedades diario hoy : " + ayer2);

			q.addFilter("fecha", FilterOperator.GREATER_THAN_OR_EQUAL, ayer1);
			q.addFilter("fecha", FilterOperator.LESS_THAN_OR_EQUAL, ayer2);
			// Falta el maldito negocio!!!
			// q.addFilter("negocio", FilterOperator.EQUAL, idNegocio);
			q.addSort("fecha", SortDirection.DESCENDING);
			q.addSort("equipoNombre", SortDirection.ASCENDING);

			FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
			// log.info("query : " + q.toString());

			List<Entity> entities = datastore.prepare(q).asList(fetchOptions);
			for (Entity entity : entities) {
				Text comentario = (Text) entity.getProperty("comentario");
				if (comentario.getValue() != null
						&& comentario.getValue().trim().length() > 0) {
					bitacoraList.add(new BitacoraLN(entity));
				}
			}
			if (bitacoraList.isEmpty())
				log.info("no hay novedades");
		} catch (Exception e) {
			log.error("DAO obtenerNovedadesAyer:" + e.getMessage());
		}
		return bitacoraList;
	}

	public static List<BitacoraLN> obtenerNovedadesAyer(Long idNegocio) {

		List<BitacoraLN> bitacoraList = new ArrayList<BitacoraLN>();
		try {

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
					BitacoraLN.class.getSimpleName());
/*
			Calendar cal1 = new GregorianCalendar();
			cal1.add(Calendar.DATE, -2);
			cal1.set(Calendar.HOUR_OF_DAY, 23);
			cal1.set(Calendar.MINUTE, 59);
			cal1.set(Calendar.SECOND, 59);
			cal1.set(Calendar.MILLISECOND, 999);

			Date ayer1 = cal1.getTime();

			Calendar cal2 = new GregorianCalendar();
			cal2.add(Calendar.DATE, -1);
			cal2.set(Calendar.HOUR_OF_DAY, 23);
			cal2.set(Calendar.MINUTE, 59);
			cal2.set(Calendar.SECOND, 59);
			cal2.set(Calendar.MILLISECOND, 999);

			Date ayer2 = cal2.getTime();
*/
			Calendar cal1 = new GregorianCalendar();
			cal1.add(Calendar.DATE, -2);
			cal1.set(Calendar.HOUR_OF_DAY, 8);
			cal1.set(Calendar.MINUTE, 00);
			cal1.set(Calendar.SECOND, 00);
			cal1.set(Calendar.MILLISECOND, 000);
			Date ayer1 = cal1.getTime();

			Calendar cal2 = new GregorianCalendar();
			//cal2.add(Calendar.DATE, -1);
			cal2.set(Calendar.HOUR_OF_DAY, 8);
			cal2.set(Calendar.MINUTE, 00);
			cal2.set(Calendar.SECOND, 00);
			cal2.set(Calendar.MILLISECOND, 000);
			Date ayer2 = cal2.getTime();				

			//log.info("reporte diario de novedades ayer : " + ayer1);
			//log.info("reporte diario de novedades hoy  : " + ayer2);

			q.addFilter("fecha", FilterOperator.GREATER_THAN_OR_EQUAL, ayer1);
			q.addFilter("fecha", FilterOperator.LESS_THAN_OR_EQUAL, ayer2);
			q.addFilter("negocio", FilterOperator.EQUAL, idNegocio);
			q.addSort("fecha", SortDirection.DESCENDING);
			q.addSort("equipoNombre", SortDirection.ASCENDING);

			FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

			List<Entity> entities = datastore.prepare(q).asList(fetchOptions);

			for (Entity entity : entities) {
				Text comentario = (Text) entity.getProperty("comentario");
				if (comentario.getValue() != null
						&& comentario.getValue().trim().length() > 0) {
					bitacoraList.add(new BitacoraLN(entity));
				}
			}
			if (bitacoraList.isEmpty())
				log.info("no hay novedades para el negocio " + idNegocio);
		} catch (Exception e) {
			log.error("DAO obtenerNovedadesAyer:" + e.getMessage());
		}
		return bitacoraList;
	}
	
	public static List<BitacoraLN> obtenerNovedadesParaCorreo(Long idNegocio) {

		List<BitacoraLN> bitacoraList = new ArrayList<BitacoraLN>();
		try {

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
					NovedadLN.class.getSimpleName());

			Calendar cal1 = Calendar.getInstance();

			cal1.add(Calendar.DATE, -1);
			cal1.set(Calendar.HOUR_OF_DAY, 8);
			cal1.set(Calendar.MINUTE, 00);
			Date ayer1 = cal1.getTime();

			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.HOUR_OF_DAY, 8);
			cal2.set(Calendar.MINUTE, 00);
			Date ayer2 = cal2.getTime();				

			log.info("nuevo reporte diario de novedades ayer : " + ayer1);
			log.info("nuevo reporte diario de novedades hoy  : " + ayer2);

			q.addFilter("fecha", FilterOperator.GREATER_THAN_OR_EQUAL, ayer1);
			q.addFilter("fecha", FilterOperator.LESS_THAN_OR_EQUAL, ayer2);
			q.addFilter("negocio", FilterOperator.EQUAL, idNegocio);
			q.addSort("fecha", SortDirection.DESCENDING);
			//q.addSort("equipoNombre", SortDirection.ASCENDING);

			FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

			List<Entity> entities = datastore.prepare(q).asList(fetchOptions);

			for (Entity entity : entities) {
				Key key=entity.getKey();
				Iterable<Entity> entidades=Util.listChildren("BitacoraLN",key);
				for (Entity entidad : entidades) {
					Text comentario = (Text) entidad.getProperty("comentario");
					if (comentario.getValue() != null
							&& comentario.getValue().trim().length() > 0) {
						bitacoraList.add(new BitacoraLN(entity));
					}				
				}
				
			}
			if (bitacoraList.isEmpty())
				log.info("no hay novedades para el negocio " + idNegocio);
		} catch (Exception e) {
			log.error("DAO obtenerNovedadesAyer:" + e.getMessage());
		}
		return bitacoraList;
	}	
}
