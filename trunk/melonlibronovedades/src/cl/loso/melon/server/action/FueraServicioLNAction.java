package cl.loso.melon.server.action;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.model.ListaEventoLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.negocio.EquipoLNBO;
import cl.loso.melon.server.negocio.FueraServicioLNBO;
import cl.loso.melon.server.negocio.UsuarioLNBO;
import cl.loso.melon.server.pojos.FueraServicio;
import cl.loso.melon.server.pojos.FueraServicioUsuario;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FueraServicioLNAction extends ActionSupport {
	
	private static Log log = LogFactory.getLog(TipoLNAction.class);
	private static final long serialVersionUID = 1L;
	private List<ListaEventoLN> eventoList;
	private String idUsuario;
	private String idResponsable;
	private String idNegocio;
	private String idEquipo;
	private String idListaEvento;
	private String fecha;
	private String fechacompromiso;
	private String fechasolucion;	
	private String problema;
	private String comentario;
	private List<EquipoLN> equipoList;	
	private List<UsuarioLN> usuarioList;
	private FueraServicio fueraServicio;
	private FueraServicioUsuario fueraServicioUsuario;
	private UsuarioLN usuarioln;
	
	public String obtenerFueraServicio() {
		try {
			eventoList=FueraServicioLNBO.obtenerFueraServicio(idUsuario);
			usuarioln=UsuarioLNBO.editarUsuarioLN(idUsuario);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	public String guardarFueraServicio() {
		try {
			// no usar idUsuario el de la pagina
			Map<String, Object> session = ActionContext.getContext()
			.getSession();
			Long idUsuarioSesion=(Long)session.get("IdUsuario");
			log.info("idUsuarioSesion este es el que vale : " + idUsuarioSesion);
			log.info("idUsuario : " + idUsuario);			
			//UsuarioLN usuario=UsuarioLNBO.editarUsuarioLN(idUsuario);
			UsuarioLN usuario=UsuarioLNBO.editarUsuarioLN(String.valueOf(idUsuarioSesion));
			FueraServicioLNBO.guardarFueraServicio(fecha,String.valueOf(usuario.getId()),
					idResponsable, String.valueOf(usuario.getIdNegocio()), idEquipo, problema);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	public String guardarFueraServicioUsuario() {
		try {

			FueraServicioLNBO.guardarFueraServicioUsuario(fechacompromiso,fechasolucion,idUsuario,
					idListaEvento, comentario);

		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	public String cerrarFueraServicioUsuario() {
		try {
			log.info("idUsuario : " + idUsuario);
			log.info("idListaEvento : " + idListaEvento);
 
			FueraServicioLNBO.cerrarFueraServicioUsuario(idUsuario,
					idListaEvento);

		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String nuevoFueraServicio() {
		try {
			UsuarioLN usuario=UsuarioLNBO.editarUsuarioLN(idUsuario);
			equipoList = EquipoLNBO.obtenerEquipoLN(String.valueOf(usuario.getIdNegocio()));
			usuarioList = UsuarioLNBO.obtenerUsuarioNegocioLN(String.valueOf(usuario.getIdNegocio()));	
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String editarFueraServicio() {
		try {
			UsuarioLN usuario=UsuarioLNBO.editarUsuarioLN(idUsuario);
			equipoList = EquipoLNBO.obtenerEquipoLN(String.valueOf(usuario.getIdNegocio()));
			usuarioList = UsuarioLNBO.obtenerUsuarioNegocioLN(String.valueOf(usuario.getIdNegocio()));
			fueraServicio=FueraServicioLNBO.editarFueraServicio(idUsuario, idListaEvento);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String editarFueraServicioUsuario() {
		try {
			UsuarioLN usuario=UsuarioLNBO.editarUsuarioLN(idUsuario);
			equipoList = EquipoLNBO.obtenerEquipoLN(String.valueOf(usuario.getIdNegocio()));
			usuarioList = UsuarioLNBO.obtenerUsuarioNegocioLN(String.valueOf(usuario.getIdNegocio()));
			ListaEventoLN eventoPadre=FueraServicioLNBO.obtenerUsuarioPadre(idUsuario, idListaEvento);
			
			log.info("idUsuario Hijo" +  idUsuario);
			log.info("idListaEvento Hijo" +  idListaEvento);
			
			log.info("eventoPadre.getIdUsuarioPadre() " +  eventoPadre.getIdUsuarioPadre());
			log.info("eventoPadre.getIdpadre() " + eventoPadre.getIdpadre());
			
			fueraServicioUsuario=FueraServicioLNBO.editarFueraServicioUsuario(
					String.valueOf(eventoPadre.getIdUsuarioPadre()), 
					String.valueOf(eventoPadre.getIdpadre()),
					idUsuario,
					idListaEvento
					);//sacar idPadre
			
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	

	public List<ListaEventoLN> getEventoList() {
		return eventoList;
	}

	public void setEventoList(List<ListaEventoLN> eventoList) {
		this.eventoList = eventoList;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(String idResponsable) {
		this.idResponsable = idResponsable;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
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

	public String getIdListaEvento() {
		return idListaEvento;
	}

	public void setIdListaEvento(String idListaEvento) {
		this.idListaEvento = idListaEvento;
	}

	public FueraServicio getFueraServicio() {
		return fueraServicio;
	}

	public void setFueraServicio(FueraServicio fueraServicio) {
		this.fueraServicio = fueraServicio;
	}

	public String getFechacompromiso() {
		return fechacompromiso;
	}

	public void setFechacompromiso(String fechacompromiso) {
		this.fechacompromiso = fechacompromiso;
	}

	public String getFechasolucion() {
		return fechasolucion;
	}

	public void setFechasolucion(String fechasolucion) {
		this.fechasolucion = fechasolucion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public FueraServicioUsuario getFueraServicioUsuario() {
		return fueraServicioUsuario;
	}

	public void setFueraServicioUsuario(FueraServicioUsuario fueraServicioUsuario) {
		this.fueraServicioUsuario = fueraServicioUsuario;
	}

	public UsuarioLN getUsuarioln() {
		return usuarioln;
	}

	public void setUsuarioln(UsuarioLN usuarioln) {
		this.usuarioln = usuarioln;
	}	

}
