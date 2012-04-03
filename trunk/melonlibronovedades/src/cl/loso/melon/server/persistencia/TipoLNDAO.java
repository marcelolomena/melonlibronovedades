package cl.loso.melon.server.persistencia;

import java.util.Date;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cl.loso.melon.server.gae.PMF;
import cl.loso.melon.server.model.TipoLN;

public class TipoLNDAO {

	private static Log log = LogFactory.getLog(TipoLNDAO.class);

	/**
	 * Almacenamiento de un item LVS
	 * 
	 * @param autor
	 *            Marcelo Lomeña
	 */
	public static void insertar(TipoLN itemlvs) {
		//log.info("persistencia guardando item " + itemlvs.getDescripcion());
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	pm.makePersistent(itemlvs);
	    } catch (Exception e) {
	    	log.error(e.getMessage());
	    } finally {
	        pm.close();
	    }			
	}

	/**
	 * Almacenamiento de un item LVS
	 * 
	 * @param autor
	 *            Marcelo Lomeña
	 */
	public static void borrar(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	pm.deletePersistent(pm.getObjectById(TipoLN.class, id));
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }		
		
	}
	
	public static void actualizar(Long id,String newItem,Date horainicio,Date horatermino,Integer orden) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	TipoLN item=pm.getObjectById(TipoLN.class, id);
	    	item.setDescripcion(newItem.toLowerCase());
	    	item.setHorainicio(horainicio);
	    	item.setHoratermino(horatermino);
	    	item.setOrden(orden);
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }		

	}	

	/**
	 * @return una lista items
	 */
	@SuppressWarnings("unchecked")
	public static List<TipoLN> obtener() {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    List<TipoLN> itemLst=null;
	    Query query=null;
	    try {
	    	query = pm.newQuery(TipoLN.class);
	    	query.setOrdering("orden ASC");
	    	itemLst = (List<TipoLN>)pm.detachCopyAll((List<TipoLN>)query.execute());
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }
	    return itemLst; 
	}

	public static TipoLN getTipoLVSbyId(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    TipoLN item, detached = null;
	    try {
	    	item = pm.getObjectById(TipoLN.class, id);
	        detached = pm.detachCopy(item);
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		

	}
	
}
