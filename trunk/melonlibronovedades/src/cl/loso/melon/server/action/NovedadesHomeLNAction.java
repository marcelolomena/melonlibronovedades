package cl.loso.melon.server.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.persistencia.BitacoraLNDAO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NovedadesHomeLNAction extends ActionSupport {
	private static Log log = LogFactory.getLog(NovedadesHomeLNAction.class);
	private static final long serialVersionUID = 1L;
	public List<BitacoraLN> novedades;
	public String reporteHome() {
		try {
			Long idNegocio=(Long)ActionContext.getContext().getSession().get("IdNegocio");
			Date today = new Date(); 
			Calendar cal = new GregorianCalendar(); 
			cal.setTime(today); 
			cal.add(Calendar.DAY_OF_MONTH, -30); 
			Date today30 = cal.getTime(); 
			
			novedades=BitacoraLNDAO.obtenerNovedadHome2(idNegocio, today30, today);
			if(novedades.isEmpty())log.info("no hay novedades!!!");
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	public List<BitacoraLN> getNovedades() {
		return novedades;
	}
	public void setNovedades(List<BitacoraLN> novedades) {
		this.novedades = novedades;
	}
	

}
