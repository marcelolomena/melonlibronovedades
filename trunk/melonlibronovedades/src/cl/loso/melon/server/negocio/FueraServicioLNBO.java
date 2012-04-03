package cl.loso.melon.server.negocio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.model.FallaLN;
import cl.loso.melon.server.model.ListaEventoLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.persistencia.FueraServicioLNDAO;
import cl.loso.melon.server.pojos.FueraServicio;
import cl.loso.melon.server.pojos.FueraServicioUsuario;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

public class FueraServicioLNBO {

	private static Log log = LogFactory.getLog(FueraServicioLNBO.class);

	public static List<ListaEventoLN> obtenerFueraServicio(String idUsuario)
			throws Exception {
		return FueraServicioLNDAO.obtener(idUsuario);
	}

	public static void guardarFueraServicio(String fecha, String idUsuario,
			String idResponsable, String idNegocio, String idEquipo,
			String problema) throws Exception {

		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha_ingreso = (Date) formato.parse(fecha);
		log.info("idUsuario:" + idUsuario);
		log.info("idResponsable:" + idResponsable);
		UsuarioLN usuario = UsuarioLNBO.editarUsuarioLN(idUsuario);
		UsuarioLN responsable = UsuarioLNBO.editarUsuarioLN(idResponsable);
		EquipoLN equipo = EquipoLNBO.editarEquipoLN(idNegocio, idEquipo);
		ListaEventoLN evento = new ListaEventoLN(fecha_ingreso, new Long(0),
				new Long(0), "P", responsable.getId());
		List<ListaEventoLN> eventos = new ArrayList<ListaEventoLN>();
		eventos.add(evento);
		usuario.setEventos(eventos);

		FallaLN falla = new FallaLN(fecha_ingreso,problema, equipo.getKey().getId(), equipo
				.getNombre());

		evento.setFalla(falla);

		FueraServicioLNDAO.guardar(usuario, evento, fecha_ingreso, responsable);
		
		enviarCorreo("prueba", usuario.getEmail(),responsable.getEmail());
	}
	
	public static void enviarCorreo(String msgBody,String from,String to) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
	
		try {

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(to));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			msg.setSubject("Fuera de servicio");
			msg.setText(msgBody);
			Transport.send(msg);
			log.info("correo enviado por " + from + " a : " + to);
		} catch (AddressException e) {
			log.error("enviarCorreo",e);
		} catch (MessagingException e) {
			log.error("enviarCorreo",e);
		}

	}	

	public static void guardarFueraServicioUsuario(String fechacompromiso,
			String fechasolucion, String idUsuario, String idListaEvento,
			String comentario) throws Exception {
		FueraServicioLNDAO.guardarUsuario(fechacompromiso, fechasolucion,
				idUsuario, idListaEvento, comentario);
	}
	

	public static void cerrarFueraServicioUsuario(String idUsuario, String idEventoHijo) throws Exception {

		ListaEventoLN eventoPadre=FueraServicioLNBO.obtenerUsuarioPadre(idUsuario, idEventoHijo);

		FueraServicioLNDAO.cambiarEstado(
				eventoPadre.getIdpadre(),
				eventoPadre.getIdUsuarioPadre(),
				Long.valueOf(idEventoHijo),
				Long.valueOf(idUsuario));
		
	}	

	public static FueraServicio editarFueraServicio(String idUsuario,
			String idListaEvento) throws Exception {

		return FueraServicioLNDAO.getListaEventoLNbyId(Long.valueOf(idUsuario),
				Long.valueOf(idListaEvento));
	}

	public static FueraServicioUsuario editarFueraServicioUsuario(
			String idUsuario, String idListaEvento,
			String idUsuarioResponsable, String idListaEventoUsuarioResponsable)
			throws Exception {

		return FueraServicioLNDAO.getFueraServicioUsuario(Long
				.valueOf(idUsuario), Long.valueOf(idListaEvento),
				Long.valueOf(idUsuarioResponsable),
				Long.valueOf(idListaEventoUsuarioResponsable)
				);
	}

	public static ListaEventoLN obtenerUsuarioPadre(String idUsuario,
			String idListaEvento) throws Exception {

		return FueraServicioLNDAO.getPadre(Long.valueOf(idUsuario), Long
				.valueOf(idListaEvento));
	}

}
