package cl.loso.melon.server.action;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.NovedadLN;
import cl.loso.melon.server.model.TipoLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.negocio.BitacoraLNBO;
import cl.loso.melon.server.negocio.EquipoLNBO;
import cl.loso.melon.server.negocio.NovedadLNBO;
import cl.loso.melon.server.negocio.TipoLNBO;
import cl.loso.melon.server.negocio.UsuarioLNBO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.google.appengine.api.datastore.Entity;

public class NovedadLNAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(NovedadLNAction.class);
	private List<NovedadLN> novedadList;
	private UsuarioLN usuarioLN;
	private String idUsuario;
	private String idVisita;
	private NovedadLN novedadLN;
	private List<EquipoLN> equipoList;
	private List<NegocioLN> negocioList;
	private List<UsuarioLN> usuarioList;
	private List<TipoLN> tipoList;
	private List<BitacoraLN> bitacoraList;
	private List<Entity> fotitos;
	private long turno;
	
	public String obtenerNovedad() {
		try {
			novedadList = NovedadLNBO.obtenerNovedadLN(idUsuario);
			usuarioLN =UsuarioLNBO.editarUsuarioLN(idUsuario);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public String guardarNovedad() {
		try {


		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	public String actualizarNovedad() {
		try {

		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String editarNovedad() {
		try {
			novedadLN=NovedadLNBO.editarNovedadLN(idUsuario,idVisita);
			String idNegocio=String.valueOf(novedadLN.getNegocio());
			bitacoraList=BitacoraLNBO.obtenerBitacoraLN(idUsuario,idVisita);
			equipoList = EquipoLNBO.obtenerEquipoLN(idNegocio);
			tipoList=TipoLNBO.obtenerTipoLN();
			turno=TipoLNBO.obtenerTurnoDefault();
			fotitos=NovedadLNBO.obtenerNovedadLN(idUsuario,idVisita);
			
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	
	public String ingresoNovedad() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
	
			usuarioLN=UsuarioLNBO.editarUsuarioLN(idUsuario);

			equipoList = EquipoLNBO.obtenerEquipoLN(String.valueOf(usuarioLN.getIdNegocio()));
			turno=TipoLNBO.obtenerTurnoDefault();
			tipoList=TipoLNBO.obtenerTipoLN();
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	public String borrarNovedad() {
		try {
			NovedadLNBO.borrarNovedadLN(idUsuario,idVisita);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public List<NovedadLN> getNovedadList() {
		return novedadList;
	}

	public void setNovedadList(List<NovedadLN> novedadList) {
		this.novedadList = novedadList;
	}

	public UsuarioLN getUsuarioLN() {
		return usuarioLN;
	}

	public void setUsuarioLN(UsuarioLN usuarioLN) {
		this.usuarioLN = usuarioLN;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdVisita() {
		return idVisita;
	}

	public void setIdVisita(String idVisita) {
		this.idVisita = idVisita;
	}

	public NovedadLN getNovedadLN() {
		return novedadLN;
	}

	public void setNovedadLN(NovedadLN novedadLN) {
		this.novedadLN = novedadLN;
	}

	public List<EquipoLN> getEquipoList() {
		return equipoList;
	}

	public void setEquipoList(List<EquipoLN> equipoList) {
		this.equipoList = equipoList;
	}

	public List<NegocioLN> getNegocioList() {
		return negocioList;
	}

	public void setNegocioList(List<NegocioLN> negocioList) {
		this.negocioList = negocioList;
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

	public List<BitacoraLN> getBitacoraList() {
		return bitacoraList;
	}

	public void setBitacoraList(List<BitacoraLN> bitacoraList) {
		this.bitacoraList = bitacoraList;
	}

	public List<Entity> getFotitos() {
		return fotitos;
	}

	public void setFotitos(List<Entity> fotitos) {
		this.fotitos = fotitos;
	}

	public long getTurno() {
		return turno;
	}

	public void setTurno(long turno) {
		this.turno = turno;
	}
}
