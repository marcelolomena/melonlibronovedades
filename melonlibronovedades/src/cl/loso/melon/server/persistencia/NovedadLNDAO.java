package cl.loso.melon.server.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;

import cl.loso.melon.server.gae.PMF;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.ImagenLN;
import cl.loso.melon.server.model.TipoLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.model.NovedadLN;
import cl.loso.melon.server.negocio.TipoLNBO;
import cl.loso.melon.server.util.EntityToKeyIterable;
import cl.loso.melon.server.util.Util;

public class NovedadLNDAO {
	private static Log log = LogFactory.getLog(NovedadLNDAO.class);

	@SuppressWarnings("unchecked")
	public static List<NovedadLN> obtener(String idUsuario) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UsuarioLN pusuario = null;
		List<NovedadLN> detached = null;

		try {
			pusuario = pm.getObjectById(UsuarioLN.class, Long
					.valueOf(idUsuario));
			
			Map<String, Object> pars = new HashMap<String, Object>();			
			String queryStr = "usuario == :usr && negocio==:neg";
			pars.put("usr", pusuario);
			pars.put("neg", pusuario.getIdNegocio());
			Query query = pm.newQuery(NovedadLN.class);
			query.setFilter(queryStr);
			query.setOrdering("fecha desc");
			detached = (List<NovedadLN>)pm.detachCopyAll((List<NovedadLN>)  query.executeWithMap(pars));			
	    	
	    	
		} catch (Exception e) {
			log.error("DAO " + e.getMessage());
		} finally {
			pm.close();
		}
		return detached;

	}
	
	@SuppressWarnings("unchecked")
	public static void insertar(UsuarioLN usuario, NovedadLN novedad) {
		Transaction tx = null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			tx = pm.currentTransaction();
			tx.begin();
			pm.makePersistent(usuario);
			pm.makePersistent(novedad);
			Long idNovedad=novedad.getKey().getId();
			tx.commit();

			Map<String, Object> pars = new HashMap<String, Object>();			
			String queryStr = "idUsuario == :usr && idNovedad==:nov";
			pars.put("usr", usuario.getId());
			pars.put("nov", null);
			Query query = pm.newQuery(ImagenLN.class);
			query.setFilter(queryStr);
			List<ImagenLN> lstImg = (List<ImagenLN>) query.executeWithMap(pars);
			
			if(!lstImg.isEmpty()){
				Iterator<ImagenLN> ite=lstImg.iterator();
				while(ite.hasNext()){
					ImagenLN img=ite.next();
					//log.info("imagen = " + img.getIdUsuario());
					img.setIdNovedad(idNovedad);
				}
			}
			
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
	
	public static List<Entity> obtenerFoto(String idUsuario,String idNovedad) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Entity> results=null;
		try {
		
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
					ImagenLN.class.getSimpleName()).setKeysOnly();
			q
			.addFilter(
					"idUsuario",
					com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
					Long.valueOf(idUsuario));
			q
			.addFilter(
					"idNovedad",
					com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
					Long.valueOf(idNovedad));
			FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
			PreparedQuery pq = datastore.prepare(q);
			results=pq.asList(fetchOptions);
			
		} catch (Exception e) {
			log.error("DAO " + e.getMessage());
		} finally {
			pm.close();
		}
		return results;

	}	
	
	public static void guardarfoto(ImagenLN imagenln) {
		Transaction tx = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			tx = pm.currentTransaction();
			tx.begin();
			pm.makePersistent(imagenln);
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

	public static void actualizar(Long idUsuario, Long idNovedad, Date fecha,
			Long idTurno,String empleado,Hashtable<Long, String> ht) {
		Transaction tx = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		NovedadLN _novedad = null;
		TipoLN _turno=null;
		try {
			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuario);
			Key k2 = KeyFactory.createKey(k1, NovedadLN.class.getSimpleName(),
					idNovedad);
			_novedad = pm.getObjectById(NovedadLN.class, k2);
			_turno=TipoLNBO.editarTipoLN(String.valueOf(idTurno.longValue()));
			String _turnoNombre=_turno.getDescripcion();
			
			tx = pm.currentTransaction();
			tx.begin();

			_novedad.setFecha(fecha);
			_novedad.setTurno(idTurno);
			_novedad.setTurnoNombre(_turnoNombre);
			_novedad.setEmpleado(empleado);

			List<BitacoraLN> comentarios = _novedad.getComentarios();
			
			Iterator<BitacoraLN> iter = comentarios.iterator();
			while (iter.hasNext()) {
				BitacoraLN it = (BitacoraLN) iter.next();
				it.setFecha(fecha);
				it.setTurno(idTurno);
				it.setTurnoNombre(_turnoNombre);
				it.setComentario(new Text(ht.get(it.getEquipo())));
			}
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
	
	public static ImagenLN getFoto(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ImagenLN foto, detached = null;
		try {
	    	foto = pm.getObjectById(ImagenLN.class, id);
	        detached = pm.detachCopy(foto);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return detached;
	}	

	public static NovedadLN getNovedadLNbyId(Long idUsuario, Long idVisita) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		NovedadLN novedad, detached = null;
		try {
			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuario);
			Key k2 = KeyFactory.createKey(k1, NovedadLN.class.getSimpleName(),
					idVisita);
			novedad = pm.getObjectById(NovedadLN.class, k2);
			detached = pm.detachCopy(novedad);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return detached;
	}


	public static void borrar(Long idUsuario, Long idVisita) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Key k1 = KeyFactory.createKey(UsuarioLN.class.getSimpleName(),
					idUsuario);
			Key k2 = KeyFactory.createKey(k1, NovedadLN.class.getSimpleName(),
					idVisita);
			
			Iterable<Entity> bitacoras=Util.listChildKeys("BitacoraLN", k2);
			EntityToKeyIterable iteKeys = new EntityToKeyIterable(bitacoras);
			Util.deleteEntitys(iteKeys);
			Util.deleteEntity(k2);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<BitacoraLN> obtenerNovedades(Date fecha_ini, Date fecha_ter, Long idEquipo) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    List<BitacoraLN> detached=null;
	    Query query=null;
	    String queryStr=null;
	    try {
			Map<String, Object> pars = new HashMap<String, Object>();
			if(idEquipo.intValue()>0){
				queryStr="fecha >= :fechaini && fecha <= :fechater && equipo == :idequipo";
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);	
				pars.put("idequipo", idEquipo);
			}else if(idEquipo.intValue()==0) {
				queryStr="fecha >= :fechaini && fecha <= :fechater";
				pars.put("fechaini", fecha_ini);
				pars.put("fechater", fecha_ter);	
			}

	    	query = pm.newQuery(BitacoraLN.class);
	    	query.setFilter(queryStr);
   	
	    	detached = (List<BitacoraLN>) pm.detachCopyAll((List<BitacoraLN>) (query.executeWithMap(pars)));
			
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		
	}

	public static List<BitacoraLN> obtenerNovedades2(Date fecha_ini, Date fecha_ter, Long idEquipo) {
		List<BitacoraLN> bitacoraList = new ArrayList<BitacoraLN>();
		try {

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
					BitacoraLN.class.getSimpleName());

			q.addFilter("fecha", FilterOperator.GREATER_THAN_OR_EQUAL,
					fecha_ini);

			q.addFilter("fecha", FilterOperator.LESS_THAN_OR_EQUAL, fecha_ter);

			if(idEquipo.intValue()>0){
				q.addFilter("equipo", FilterOperator.EQUAL, idEquipo);
			}

			q.addSort("fecha", SortDirection.DESCENDING);

			FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

			List<Entity> entities = datastore.prepare(q).asList(fetchOptions);
			for (Entity entity : entities) {
				Text comentario = (Text) entity.getProperty("comentario");
				if (comentario.getValue() != null && comentario.getValue().trim().length()>0) {
					bitacoraList.add(new BitacoraLN(entity));
				}
			}

		} catch (Exception e) {
			log.error("DAO obtenerNovedades2:" + e.getMessage());
		}
		return bitacoraList;
	}
}
