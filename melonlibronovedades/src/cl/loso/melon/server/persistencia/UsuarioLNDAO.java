package cl.loso.melon.server.persistencia;

import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.gae.PMF;
import cl.loso.melon.server.model.UsuarioLN;
/*
public List<Flight> flightPaginationByDest(PersistenceManager pm, Key dest1, Key dest2) {
      String queryStr = "select from " + Flight.class.getName() + 
          " where dest > :p1 && dest <= :p2";
      return (List<Flight>) pm.newQuery(queryStr).execute(dest1, dest2);
}
 */
public class UsuarioLNDAO {
	private static Log log = LogFactory.getLog(UsuarioLNDAO.class);
	
	@SuppressWarnings("unchecked")
	public static List<UsuarioLN> obtener() {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    List<UsuarioLN> usuarioLst=null;
	    Query query=null;
	    try {
	    	query = pm.newQuery(UsuarioLN.class);
	    	query.setOrdering("nombres ASC");
	    	usuarioLst = (List<UsuarioLN>)pm.detachCopyAll((List<UsuarioLN>)query.execute());
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }
	    return usuarioLst; 
	}	
	
	public static void insertar(String nombres,
			 String apepa,
			 String apema,
			 String email,
			 char perfil,
			 String cargo,
			 Long idNegocio,
			 String correo) {

	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    UsuarioLN usuario=null;
	    try {
	    	if(correo!=null)
	    		usuario=new UsuarioLN(nombres,apepa,apema,email,perfil,cargo,idNegocio,new Boolean(true));
	    	else
	    		usuario=new UsuarioLN(nombres,apepa,apema,email,perfil,cargo,idNegocio,new Boolean(false));	    		
	    	pm.makePersistent(usuario);
	    } catch (Exception e) {
	    	log.error(e.getMessage());
	    } finally {
	        pm.close();
	    }			
	}	
	
	public static UsuarioLN getUsuarioLNbyId(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    UsuarioLN usuario, detached = null;
	    try {
	    	usuario = pm.getObjectById(UsuarioLN.class, id);
	        detached = pm.detachCopy(usuario);
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		

	}	
	
	public static void actualizar(Long id,
			 String nombres,
			 String apepa,
			 String apema,
			 String email,
			 char perfil,
			 String cargo,
			 Long idNegocio,
			 String correo) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	UsuarioLN usuario=pm.getObjectById(UsuarioLN.class, id);
	    	usuario.setNombres(nombres);
	    	usuario.setApepa(apepa);
	    	usuario.setApema(apema);
	    	usuario.setEmail(email);
	    	usuario.setPerfil(perfil);
	    	usuario.setCargo(cargo);
	    	usuario.setIdNegocio(idNegocio);
	    	if(correo!=null)
	    		usuario.setCorreo(new Boolean(true));
	    	else
	    		usuario.setCorreo(new Boolean(false));
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }		

	}	
	
	public static void borrar(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	pm.deletePersistent(pm.getObjectById(UsuarioLN.class, id));
	    } catch (Exception e) {
	    	log.error(e.getMessage());	    	
	    } finally {
	        pm.close();
	    }		
		
	}
	public static List<UsuarioLN> buscarUsuarioPorEmail(String pemail) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    List<UsuarioLN> detached = null;

	    try {
	    	Query query = pm.newQuery(UsuarioLN.class,"email == pemail");
	    	query.declareParameters("String pemail");

	    	detached = (List<UsuarioLN>)pm.detachCopyAll((List<UsuarioLN>) query.execute(pemail));
  
	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		

	}
	
	public static List<UsuarioLN> buscarUsuarioPorNegocio(String pnegocio) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    List<UsuarioLN> detached = null;
	    try {
	    	Query query = pm.newQuery(UsuarioLN.class,"idNegocio == pnegocio");
	    	query.declareParameters("Long pnegocio");
	    	detached = (List<UsuarioLN>)pm.detachCopyAll((List<UsuarioLN>) query.execute(Long.valueOf(pnegocio)));

	    } catch (Exception e) {
	    	log.error(e.getMessage());	        
	    } finally {
	        pm.close();
	    }
	    return detached;		

	}
	
}
