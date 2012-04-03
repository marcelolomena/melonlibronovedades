package cl.loso.melon.server.persistencia;

import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import cl.loso.melon.server.gae.PMF;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.util.EntityToKeyIterable;
import cl.loso.melon.server.util.Util;

public class NegocioLNDAO {
	private static Log log = LogFactory.getLog(NegocioLNDAO.class);

	/**
	 * @return una lista items
	 */
	@SuppressWarnings("unchecked")
	public static List<NegocioLN> obtener() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<NegocioLN> negocioLst = null;
		Query query = null;
		try {
			query = pm.newQuery(NegocioLN.class);
			// query.setOrdering("fecha DESC");
			negocioLst = (List<NegocioLN>) pm
					.detachCopyAll((List<NegocioLN>) query.execute());
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return negocioLst;
	}

	public static void insertar(NegocioLN negocio) {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(negocio);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
	}

	public static NegocioLN getNegocioLNbyId(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		NegocioLN negocio, detached = null;
		try {
			negocio = pm.getObjectById(NegocioLN.class, id);
			detached = pm.detachCopy(negocio);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}
		return detached;

	}

	public static void actualizar(NegocioLN negocioNew) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			NegocioLN negocio = pm.getObjectById(NegocioLN.class, negocioNew
					.getId());
			negocio.setNombre(negocioNew.getNombre());

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			pm.close();
		}

	}

	public static void borrar(Long id) {
		try {

			Key key = KeyFactory.createKey(NegocioLN.class.getSimpleName(),
					Long.valueOf(id));

			Iterable<Entity> usuarios = Util.listEntitiesLN("UsuarioLN",
					"idNegocio", id);
			EntityToKeyIterable iteKeys = new EntityToKeyIterable(usuarios);
			
			Util.deleteEntity(key);
			Util.deleteEntitys(iteKeys);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
