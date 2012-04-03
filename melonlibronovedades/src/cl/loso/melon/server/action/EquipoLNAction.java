package cl.loso.melon.server.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.negocio.NegocioLNBO;
import cl.loso.melon.server.negocio.EquipoLNBO;
import com.opensymphony.xwork2.ActionSupport;

public class EquipoLNAction extends ActionSupport {
	private static Log log = LogFactory.getLog(TipoLNAction.class);
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String idNegocio;
	private EquipoLN equipoLN;
	private List<EquipoLN> equipoList;
	private String idEquipo;
	private NegocioLN negocioLN;
	
	public String obtenerEquipo() {
		try {
			negocioLN=NegocioLNBO.editarNegocioLN(idNegocio);
			equipoList = EquipoLNBO.obtenerEquipoLN(idNegocio);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	public String guardarEquipo() {
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(date);

			EquipoLNBO.guardarEquipoLN(idNegocio,nombre,today);
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}	
	
	public String borrarEquipo() {
		try {
			EquipoLNBO.borrarEquipoLN(idNegocio,idEquipo);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String editarEquipo() {
		try {
			equipoLN=EquipoLNBO.editarEquipoLN(idNegocio,idEquipo);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}		
	
	public String actualizarEquipo() {
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(date);
		
			EquipoLNBO.actualizarEquipoLN(idNegocio, idEquipo, nombre, today);
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}		

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EquipoLN getEquipoLN() {
		return equipoLN;
	}

	public void setEquipoLN(EquipoLN equipoLN) {
		this.equipoLN = equipoLN;
	}

	public List<EquipoLN> getEquipoList() {
		return equipoList;
	}

	public void setEquipoList(List<EquipoLN> equipoList) {
		this.equipoList = equipoList;
	}

	public String getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	public NegocioLN getNegocioLN() {
		return negocioLN;
	}

	public void setNegocioLN(NegocioLN negocioLN) {
		this.negocioLN = negocioLN;
	}

	
}
