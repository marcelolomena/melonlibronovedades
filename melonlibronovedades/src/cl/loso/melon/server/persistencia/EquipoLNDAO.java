package cl.loso.melon.server.persistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import cl.loso.melon.server.gae.PMF;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.model.NovedadLN;

public class EquipoLNDAO {
	private static Log log = LogFactory.getLog(EquipoLNDAO.class);
	
	public static List<EquipoLN> obtener(NegocioLN negociolvs) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    NegocioLN negocio = null;
	    List<EquipoLN> detached=null;

	    try {
	    	negocio = pm.getObjectById(NegocioLN.class, negociolvs.getId());
	    	detached = (List<EquipoLN>)pm.detachCopyAll(negocio.getEquipos());	
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		

	}
	
	@SuppressWarnings("unchecked")
	public static List<EquipoLN> obtener() {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    List<EquipoLN> detached=null;
	    Query query=null;
	    try {
	    	query = pm.newQuery(EquipoLN.class);
	    	detached = (List<EquipoLN>)pm.detachCopyAll((List<EquipoLN>)query.execute());
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		
	}
	

	public static void insertar(String idNegocio,String nombre, String fecha) {
	    Transaction tx=null;
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {

	    	NegocioLN negocio=NegocioLNDAO.getNegocioLNbyId(Long.valueOf(idNegocio));
	    	
	    	EquipoLN sl=new EquipoLN();
		   	sl.setNegocio(negocio);
		   	sl.setNombre(nombre);
		   	sl.setFecha(fecha);	
		   	
		   	List<EquipoLN> lst=new ArrayList<EquipoLN>();
		   	lst.add(sl);
		   	
		   	negocio.setEquipos(lst);
	    	tx = pm.currentTransaction();
	        tx.begin();

	    	pm.makePersistent(negocio);
	    	
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
	
	public static EquipoLN getEquipoLNbyId(Long idNegocio, Long idSitio) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    EquipoLN sitio, detached = null;
	    try {
	    	Key k1 = KeyFactory.createKey(NegocioLN.class.getSimpleName(), idNegocio);
	    	Key k2 = KeyFactory.createKey(k1,EquipoLN.class.getSimpleName(), idSitio);
	    	sitio = pm.getObjectById(EquipoLN.class, k2);
	        detached = pm.detachCopy(sitio);
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		

	}	
	
	public static EquipoLN getEquipoLNbyId2(Key key) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    EquipoLN equipo, detached = null;
	    try {
	    	equipo = pm.getObjectById(EquipoLN.class, key);
	        detached = pm.detachCopy(equipo);
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		

	}	
	
	public static void actualizar(Long idNegocio, Long idSitio, String nombre, String fecha) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    EquipoLN sitio = null;
	    try {
	    	Key k1 = KeyFactory.createKey(NegocioLN.class.getSimpleName(), idNegocio);
	    	Key k2 = KeyFactory.createKey(k1,EquipoLN.class.getSimpleName(), idSitio);
	    	sitio = pm.getObjectById(EquipoLN.class, k2);
	    	sitio.setNombre(nombre);
	    	sitio.setFecha(fecha);
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }		

	}	
	
	@SuppressWarnings("unchecked")
	public static void borrar(Long idNegocio,Long idEquipo) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	Key k1 = KeyFactory.createKey(NegocioLN.class.getSimpleName(), idNegocio);
	    	Key k2 = KeyFactory.createKey(k1,EquipoLN.class.getSimpleName(), idEquipo);
	    	pm.deletePersistent(pm.getObjectById(EquipoLN.class, k2));

			Map<String, Object> pars = new HashMap<String, Object>();			
			String queryStr = "negocio == :neg && equipo==:equ";
			pars.put("neg", idNegocio);
			pars.put("equ", idEquipo);
			Query query = pm.newQuery(BitacoraLN.class);
			query.setFilter(queryStr);
			List<BitacoraLN> lstBit=(List<BitacoraLN>)  query.executeWithMap(pars);	
			for(BitacoraLN bitacora:lstBit){
				NovedadLN novedad=bitacora.getNovedad();
				pm.deletePersistent(novedad);		
			}
    	
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }		
		
	}	
}
