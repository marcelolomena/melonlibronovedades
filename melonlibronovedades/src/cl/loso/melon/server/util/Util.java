package cl.loso.melon.server.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class Util {
	private static Log logger = LogFactory.getLog(Util.class);
	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private static MemcacheService keycache = MemcacheServiceFactory
			.getMemcacheService();

	/**
	 * 
	 * @param entity
	 *            : entity to be persisted
	 */
	public static void persistEntity(Entity entity) {
		//logger.info("Grabando entidad");
		Transaction txn = null;
		try {
			txn = datastore.beginTransaction();
			datastore.put(entity);
			txn.commit();
		} catch (IllegalArgumentException e) {
			logger.fatal(e.getMessage());
		} catch (ConcurrentModificationException e) {
			logger.fatal(e.getMessage());
		} catch (DatastoreFailureException e) {
			logger.fatal(e.getMessage());
		} catch (IllegalStateException e) {
			logger.fatal(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
	
	public static void deleteEntity(Key key) {
		//logger.info("Grabando entidad");
		Transaction txn = null;
		try {
			txn = datastore.beginTransaction();
			datastore.delete(key);
			txn.commit();
		} catch (IllegalArgumentException e) {
			logger.fatal(e.getMessage());
		} catch (ConcurrentModificationException e) {
			logger.fatal(e.getMessage());
		} catch (DatastoreFailureException e) {
			logger.fatal(e.getMessage());
		} catch (IllegalStateException e) {
			logger.fatal(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}	
	
	public static void deleteEntitys(Iterable<Key> keys) {
		//logger.info("Grabando entidad");
		Transaction txn = null;
		try {
			txn = datastore.beginTransaction();
			datastore.delete(keys);
			txn.commit();
		} catch (IllegalArgumentException e) {
			logger.fatal(e.getMessage());
		} catch (ConcurrentModificationException e) {
			logger.fatal(e.getMessage());
		} catch (DatastoreFailureException e) {
			logger.fatal(e.getMessage());
		} catch (IllegalStateException e) {
			logger.fatal(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
	
	/**
	 * Search and return the entity from the datastore.
	 * 
	 * @param key
	 *            : key to find the entity
	 * @return entity
	 */
	public static Entity findEntity(Key key) {
		try {
			return datastore.get(key);
		} catch (EntityNotFoundException e) {
			logger.fatal("devolviendo un nulo");
			return null;
		}
	}
	
	/***
	 * Search entities based on search criteria
	 * 
	 * @param kind
	 * @param searchBy
	 *            : Searching Criteria (Property)
	 * @param searchFor
	 *            : Searching Value (Property Value)
	 * @return List all entities of a kind from the cache or datastore (if not
	 *         in cache) with the specified properties
	 */
	

	public static Iterable<Entity> listObjectEntities(String kind, String searchBy,
			Object searchFor) {

		Query q = new Query(kind);
		if (searchFor != null) {
			q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
		}
		PreparedQuery pq = datastore.prepare(q);

		return pq.asIterable();
	}
	
	public static Iterable<Entity> listEntities(String kind, String searchBy,
			String searchFor) {

		Query q = new Query(kind);
		if (searchFor != null && !"".equals(searchFor)) {
			q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
		}
		PreparedQuery pq = datastore.prepare(q);
		//logger.info(pq.toString());
		return pq.asIterable();
	}
	
	public static Iterable<Entity> listAllEntities(String kind) {

		Query q = new Query(kind);

		PreparedQuery pq = datastore.prepare(q);

		return pq.asIterable();
	}	

	/**
	 * Get the list of children from a parent key in the entity group
	 * 
	 * @param kind
	 *            : the entity kind of the children that is to be searched for
	 * @param ancestor
	 *            : the parent key of the entity group where we need to search
	 * @return iterable with all children of the parent of the specified kind
	 */
	public static Iterable<Entity> listChildren(String kind, Key ancestor) {

		Query query = new Query(kind);
		query.setAncestor(ancestor);
		query.addFilter(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.GREATER_THAN, ancestor);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}

	public static Iterable<Entity> listNovedadesHome(Date fecini, Date fecter, Long negocio) {

		Query q = new Query("BitacoraLN");
		q.addFilter("fecha", FilterOperator.GREATER_THAN_OR_EQUAL, fecini);
		q.addFilter("fecha", FilterOperator.LESS_THAN_OR_EQUAL, fecter);
		q.addFilter("negocio", FilterOperator.EQUAL, negocio);
		q.addSort("fecha", Query.SortDirection.DESCENDING);
		//logger.info("super query : " + q.toString());
		PreparedQuery pq = datastore.prepare(q);

		return pq.asIterable();
	}

	public static Iterable<Entity> listNovedadesHome(Date fecini, Date fecter) {

		Query q = new Query("NovedadLN");
		q.addFilter("fecha", FilterOperator.GREATER_THAN_OR_EQUAL, fecini);
		q.addFilter("fecha", FilterOperator.LESS_THAN_OR_EQUAL, fecter);
		q.addSort("fecha", Query.SortDirection.DESCENDING);
		//logger.info("query : " + q.toString());
		PreparedQuery pq = datastore.prepare(q);

		return pq.asIterable();
	}
	
	public static Iterable<Entity> listTurnoHome() {
		Query q = new Query("TipoLN");
		q.addSort("orden", Query.SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		//logger.info("query : " + q.toString());
		return pq.asIterable();
	}

	public static Iterable<Entity> listEquipoHome(Long negocio,Long turno, Date fecha) {
		Query query = new Query("BitacoraLN");
		query.addFilter("turno", FilterOperator.EQUAL, turno);
		query.addFilter("fecha", FilterOperator.EQUAL, fecha);
		query.addFilter("comentario", FilterOperator.NOT_EQUAL, "");
		query.addFilter("negocio", FilterOperator.EQUAL, negocio);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}
	
	public static Iterable<Entity> listNovedades(Date fecha) {
		Query query = new Query("BitacoraLN");
		query.addFilter("fecha", FilterOperator.EQUAL, fecha);
		query.addFilter("comentario", FilterOperator.NOT_EQUAL, "");
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}	
	
	public static int existeLaNovedad(Date fecha,Long idEquipo,Long idTurno) {
		Query query = new Query("BitacoraLN");
		query.addFilter("fecha", FilterOperator.EQUAL, fecha);
		query.addFilter("comentario", FilterOperator.NOT_EQUAL, "");
		query.addFilter("equipo", FilterOperator.EQUAL, idEquipo);
		query.addFilter("turno", FilterOperator.EQUAL, idTurno);
		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		PreparedQuery pq = datastore.prepare(query);
		return pq.countEntities(fetchOptions);
	}		
	
	
	public static Iterable<Entity> listNovedades(Date fecha,Long negocio) {
		Query query = new Query("BitacoraLN");
		query.addFilter("fecha", FilterOperator.EQUAL, fecha);
		query.addFilter("comentario", FilterOperator.NOT_EQUAL, "");
		query.addFilter("negocio", FilterOperator.EQUAL, negocio);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}	
	
	public static Iterable<Entity> listNovedadesHome2(Long negocioId,Long equipoId, Date fecha) {
		Query query = new Query("BitacoraLN");
		query.addFilter("fecha", FilterOperator.EQUAL, fecha);
		query.addFilter("equipo", FilterOperator.EQUAL, equipoId);
		query.addFilter("negocio", FilterOperator.EQUAL, negocioId);
		query.addFilter("comentario", FilterOperator.NOT_EQUAL, "");
		//logger.info("la query : " + query.toString());
		PreparedQuery pq = datastore.prepare(query);
		
		return pq.asIterable();
	}	

	/**
	 * Get the list of keys of all children for a given entity kind in a given
	 * entity group represented by the parent key
	 * 
	 * @param kind
	 *            : Entity kind of the children that needs to be searched for
	 * @param ancestor
	 *            : Parent key of the entity group that needs to be searched for
	 * @return an iterable with keys of children
	 */
	public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
		
		Query query = new Query(kind);
		query.setAncestor(ancestor).setKeysOnly();
		query.addFilter(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.GREATER_THAN, ancestor);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}


	public static Iterable<Entity> listEntitiesLN(String kind, String searchBy,
			Long searchFor) {
		Query q = new Query(kind);
		q.setKeysOnly();
		if (searchFor != null) {
			q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
		}
		PreparedQuery pq = datastore.prepare(q);

		return pq.asIterable();
	}	


	/**
	 * List the entities in JSON format
	 * 
	 * @param entities
	 *            entities to return as JSON strings
	 */
	public static String writeJSON(Iterable<Entity> entities) {

		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("{\"data\": [");
		for (Entity result : entities) {
			Map<String, Object> properties = result.getProperties();
			sb.append("{");
			if (result.getKey().getName() == null)
				sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
			else
				sb.append("\"name\" : \"" + result.getKey().getName() + "\",");

			for (String key : properties.keySet()) {
				sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("},");
			i++;
		}
		if (i > 0) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]}");
		return sb.toString();
	}

	/**
	 * Adds the entity to cache
	 * 
	 * @param key
	 *            : key of the entity
	 * @param entity
	 *            : Entity being added
	 */
	public static void addToCache(Key key, Entity entity) {

		keycache.put(key, entity);
	}

	/**
	 * Delete the entity from cache
	 * 
	 * @param key
	 *            : Key of the entity that needs to be deleted
	 */
	public static void deleteFromCache(Key key) {

		keycache.delete(key);
	}

	/**
	 * Delete entities based on a set of keys
	 * 
	 * @param keys
	 *            : list of keys for the entities that are to be deleted
	 */
	public static void deleteFromCache(List<Key> keys) {
		keycache.deleteAll(keys);
	}

	/**
	 * Search for an entity based on key in the cache
	 * 
	 * @param key
	 *            : key of the entity that is searched for
	 * @return the entity
	 */
	public static Entity getFromCache(Key key) {

		return (Entity) keycache.get(key);
	}

	/**
	 * Utility method to send the error back to UI
	 * 
	 * @param data
	 * @param resp
	 * @throws IOException
	 */
	public static String getErrorResponse(Exception ex) throws IOException {
		return "Error:" + ex.toString();
	}

	public static DatastoreService getDatastoreServiceInstance() {
		return datastore;
	}

	public static void Logging(String message) {
		Entity logs = new Entity("log");
		logs.setProperty("log_text", message);
		datastore.put(logs);
	}

	public static Object[] split(String cadena, String separador) {
		StringTokenizer st = new StringTokenizer(cadena, separador);
		Vector<String> v = new Vector<String>();
		while (st.hasMoreTokens()) {
			String token = (String) st.nextToken();
			v.add(token);
			// System.out.println(token);
		}
		return v.toArray();
	}

	public static Date getPrimerDiaDelMes() {
		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.getActualMinimum(Calendar.DAY_OF_MONTH), cal
				.getMinimum(Calendar.HOUR_OF_DAY), cal
				.getMinimum(Calendar.MINUTE), cal.getMinimum(Calendar.SECOND));
		return cal.getTime();
	}
	
	public static Date getUltimoDiaDelMesAnterior() {
		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.getActualMinimum(Calendar.DAY_OF_MONTH), cal
				.getMinimum(Calendar.HOUR_OF_DAY), cal
				.getMinimum(Calendar.MINUTE), cal.getMinimum(Calendar.SECOND));
		
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		return cal.getTime();
	}	

	public static Date getUltimoDiaDelMes() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.getActualMaximum(Calendar.DAY_OF_MONTH), cal
				.getMaximum(Calendar.HOUR_OF_DAY), cal
				.getMaximum(Calendar.MINUTE), cal.getMaximum(Calendar.SECOND));
		return cal.getTime();
	}
	
	public static Date getPrimerDiaDelMesProximo() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.getActualMaximum(Calendar.DAY_OF_MONTH), cal
				.getMaximum(Calendar.HOUR_OF_DAY), cal
				.getMaximum(Calendar.MINUTE), cal.getMaximum(Calendar.SECOND));
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	public static Date tomorow() {
		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), cal
				.getMinimum(Calendar.HOUR_OF_DAY), cal
				.getMinimum(Calendar.MINUTE), cal.getMinimum(Calendar.SECOND));
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}	
	
	public static Date hacetreintadias() {
		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), cal
				.getMinimum(Calendar.HOUR_OF_DAY), cal
				.getMinimum(Calendar.MINUTE), cal.getMinimum(Calendar.SECOND));
		
		cal.add(Calendar.MONTH, -1);
		
		return cal.getTime();
	}
}
