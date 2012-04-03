package cl.loso.melon.server.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.TipoLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.negocio.EquipoLNBO;
import cl.loso.melon.server.negocio.NegocioLNBO;
import cl.loso.melon.server.negocio.TipoLNBO;
import cl.loso.melon.server.negocio.UsuarioLNBO;

import com.opensymphony.xwork2.ActionSupport;

public class NegocioLNAction extends ActionSupport {
	private static Log log = LogFactory.getLog(TipoLNAction.class);
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private NegocioLN negocioLN;
	private List<NegocioLN> negocioList;
	private List<EquipoLN> equipoList;
	private List<UsuarioLN> usuarioList;
	private List<TipoLN> tipoList;
	
	public String obtenerNegocio() {
		try {
			negocioList = NegocioLNBO.obtenerNegocioLN();
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	public String guardarNegocio() {
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(date);
			NegocioLN negocioLVS = new NegocioLN(nombre,today);
			NegocioLNBO.guardarNegocioLN(negocioLVS);
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}	
	
	public String editarNegocio() {
		try {
			negocioLN=NegocioLNBO.editarNegocioLN(id);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String actualizarNegocio() {
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(date);
			NegocioLNBO.actualizarNegocioLN(new NegocioLN(Long.valueOf(id),nombre,today));
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}	
	
	public String borrarNegocio() {
		try {
			NegocioLNBO.borrarNegocioLN(id);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String obtenerLocalidades() {
		try {
			negocioList = NegocioLNBO.obtenerNegocioLN();
			equipoList = EquipoLNBO.obtenerEquipoLN();
			usuarioList = UsuarioLNBO.obtenerUsuarioLN();
			tipoList=TipoLNBO.obtenerTipoLN();
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public NegocioLN getNegocioLN() {
		return negocioLN;
	}

	public void setNegocioLN(NegocioLN negocioLVS) {
		this.negocioLN = negocioLVS;
	}

	public List<NegocioLN> getNegocioList() {
		return negocioList;
	}

	public void setNegocioList(List<NegocioLN> negocioList) {
		this.negocioList = negocioList;
	}

	public List<EquipoLN> getEquipoList() {
		return equipoList;
	}

	public void setEquipoList(List<EquipoLN> equipoList) {
		this.equipoList = equipoList;
	}

	public List<UsuarioLN> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<UsuarioLN> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public List<TipoLN> getTipoList() {
		return tipoList;
	}

	public void setTipoList(List<TipoLN> tipoList) {
		this.tipoList = tipoList;
	}	
}
