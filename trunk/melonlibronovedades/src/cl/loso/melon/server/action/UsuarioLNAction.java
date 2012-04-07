package cl.loso.melon.server.action;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.negocio.NegocioLNBO;
import cl.loso.melon.server.negocio.UsuarioLNBO;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UsuarioLNAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(UsuarioLNAction.class);
	private List<UsuarioLN> usuarioList;
	private UsuarioLN usuarioLN;
	private String id;
	private String nombres;
	private String apepa;
	private String apema;
	private String email;
	private String cargo;
	private String perfil;
	private String idNegocio;
	private String correo;
	private List<NegocioLN> negocioList;

	public String login() {

		String retorno = null;
		try {
			ServletContext context = ServletActionContext.getServletContext();
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			UserService userService = UserServiceFactory.getUserService();
			Map<String, Object> session = ActionContext.getContext().getSession();
			User user = userService.getCurrentUser();
			
			if(user==null){
				response.sendRedirect("/");
			}
			
			String mailusu=user.getEmail().toLowerCase();
			log.info("mail del usuario google: " + mailusu);

			usuarioList = UsuarioLNBO
					.obtenerUsuarioPorEmailLVS(user.getEmail());
			
			if(usuarioList==null) throw new Exception("La lista de usuarios esta vacia");

			if (!usuarioList.isEmpty() ) {
				
				usuarioLN = usuarioList.get(0);

				if (user.getEmail().equals(usuarioLN.getEmail())) {

					request.setAttribute("uid", String.valueOf(usuarioLN
							.getId()));
					request.setAttribute("perfil", String.valueOf(usuarioLN
							.getPerfil()));
					
					session.put("logueado", "true");
					session.put("IdNegocio", usuarioLN.getIdNegocio());
					session.put("perfil", usuarioLN.getPerfil());
					session.put("IdUsuario", usuarioLN.getId());
					/*
					String txtNegocio=((NegocioLN)NegocioLNBO.editarNegocioLN(String.valueOf(usuarioLN.getId()))).getNombre();
					if(txtNegocio!=null){
						session.put("txtNegocio", txtNegocio);
					}else{
						session.put("txtNegocio", "sin negocio");
					}
					*/
					
					
					log.info("usuarioLN.getPerfil(): " + usuarioLN.getPerfil());
					retorno = ActionSupport.SUCCESS;
				} else {
					log.info("no coinciden los usuarios!!");
					throw new Exception("no coinciden los usuarios!!");
				}
			} else {

				log.info("no existe el usuario " + mailusu + " en la base de datos!!");

				String usuario_nombres = context
						.getInitParameter("usuario_nombres");
				String usuario_apepa = context
						.getInitParameter("usuario_apepa");
				String usuario_apema = context
						.getInitParameter("usuario_apema");
				String usuario_mail = context
						.getInitParameter("usuario_mail");
				String usuario_cargo = context
						.getInitParameter("usuario_cargo");
				String usuario_perfil = context
				.getInitParameter("usuario_perfil");

				log.info("usuario_mail:" + usuario_mail);
				log.info("mailusu:" + mailusu);

				if (mailusu.equals(usuario_mail)) {

					UsuarioLNBO.guardarUsuarioLN(usuario_nombres,
							usuario_apepa, usuario_apema, usuario_mail,
							usuario_perfil.charAt(0), usuario_cargo, "0", "0");
					
					UsuarioLN usr=((UsuarioLN)UsuarioLNBO.obtenerUsuarioPorEmailLVS(user
							.getEmail()).get(0));

					request.setAttribute("uid", usr.getId());
					request.setAttribute("perfil", Character
							.valueOf(usr.getPerfil()));
					
					retorno = ActionSupport.SUCCESS;
				} else {
					log.error("no es el usuario adm el que intenta ingresar por primera vez");
					addActionError("el sistema no tiene datos" );
					addActionError("solo el usuario administrador (bernardo.palomo@melon.cl) puede ingresar por primera vez al sistema" );
					retorno = ActionSupport.ERROR;
				}
				
				
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			addActionError(e.getMessage());
			retorno = ActionSupport.ERROR;
		}

		return retorno;
	}

	public String logout() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (session.containsKey("IdNegocio")) {
			log.info("todavia hay IdNegocio");
		}
		session.remove("logueado");
		session.remove("IdNegocio");
		session.remove("IdUsuario");
		session.remove("perfil");	

		if (!session.containsKey("IdNegocio")) {
			log.info("no hay IdNegocio");
		}
		return ActionSupport.SUCCESS;
	}

	public String obtenerUsuario() {
		try {
			usuarioList = UsuarioLNBO.obtenerUsuarioLN();
			negocioList = NegocioLNBO.obtenerNegocioLN();
			if(usuarioList.isEmpty()) log.info("no hay usuarios"); else
			log.info("hay usuarios");
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	public String nuevoUsuario() {
		try {
			negocioList = NegocioLNBO.obtenerNegocioLN();
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	

	public String guardarUsuario() {
		try {

			UsuarioLNBO.guardarUsuarioLN(nombres, apepa, apema, email, perfil
					.charAt(0), cargo, idNegocio,correo);
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}

	public String editarUsuario() {
		try {
			usuarioLN = UsuarioLNBO.editarUsuarioLN(id);
			negocioList = NegocioLNBO.obtenerNegocioLN();
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public String actualizarUsuario() {
		try {
			UsuarioLNBO.actualizarUsuarioLN(id, nombres, apepa, apema, email,
					perfil.charAt(0), cargo, idNegocio,correo);
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}

	public String borrarUsuario() {
		try {
			UsuarioLNBO.borrarUsuarioLN(id);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public List<UsuarioLN> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<UsuarioLN> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public UsuarioLN getUsuarioLN() {
		return usuarioLN;
	}

	public void setUsuarioLN(UsuarioLN usuarioLN) {
		this.usuarioLN = usuarioLN;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApepa() {
		return apepa;
	}

	public void setApepa(String apepa) {
		this.apepa = apepa;
	}

	public String getApema() {
		return apema;
	}

	public void setApema(String apema) {
		this.apema = apema;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}

	public List<NegocioLN> getNegocioList() {
		return negocioList;
	}

	public void setNegocioList(List<NegocioLN> negocioList) {
		this.negocioList = negocioList;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
