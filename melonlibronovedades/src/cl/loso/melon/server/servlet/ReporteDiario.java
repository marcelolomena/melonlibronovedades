package cl.loso.melon.server.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.loso.melon.server.model.ListaEventoLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.negocio.FueraServicioLNBO;
import cl.loso.melon.server.negocio.UsuarioLNBO;
import cl.loso.melon.server.persistencia.FueraServicioLNDAO;

public class ReporteDiario extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ReporteDiario.class
			.getName());
	private static String from = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		from = config.getInitParameter("from");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		try {
			List<ListaEventoLN> eventos=FueraServicioLNDAO.obtenerEventosPendientes();														// pendientes
			Iterator<ListaEventoLN> ite = eventos.iterator();
			while (ite.hasNext()) {
				ListaEventoLN evento = ite.next();
				UsuarioLN usr =UsuarioLNBO.editarUsuarioLN(String.valueOf(evento.getIdResponsable()));
				FueraServicioLNBO.enviarCorreo(
						"tiene un fuera de servicio pendiente", from, usr
								.getEmail());
			}

		} catch (Exception ex) {
			log.warning(ex.getMessage());
		} finally {
			try {

			} catch (Exception ignored) {
			}
		}

	}
}