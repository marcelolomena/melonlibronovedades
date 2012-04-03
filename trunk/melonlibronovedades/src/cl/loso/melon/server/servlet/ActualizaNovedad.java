package cl.loso.melon.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cl.loso.melon.server.negocio.NovedadLNBO;

public class ActualizaNovedad extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ActualizaNovedad.class.getName());

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter writer = null;
		String idUsuario = null;
		String fecha = null;
		String idTurno = null;
		String empleado = null;
		String accion = null;
		String idNovedad = null;
		writer = res.getWriter();
		res.setContentType("application/json");
		
		try {
			idUsuario = req.getParameter("idUsuario");
			fecha = req.getParameter("fecha");
			idTurno = req.getParameter("tipo");
			empleado = req.getParameter("empleado");
			accion = req.getParameter("accion");
			idNovedad = req.getParameter("idVisita");// idNovedad
			Enumeration<String> enumeration1 = req.getParameterNames();
			
			Hashtable<Long, String> ht = new Hashtable<Long, String>();
			while (enumeration1.hasMoreElements()) {
				String parameterName = enumeration1.nextElement();

				if (parameterName.startsWith("comentario_")) {
					Long idEquipo = Long.valueOf(parameterName
							.substring(parameterName.indexOf("_") + 1));
					String comentario = req.getParameter(parameterName);

					ht.put(idEquipo, comentario);

				}
			}

			if (accion.equals("agregar")) {
				NovedadLNBO.guardarNovedadLN(fecha, idUsuario, idTurno,
						empleado, ht);
			} else if (accion.equals("editar")) {
				NovedadLNBO.actualizarNovedadLN(fecha, idUsuario, idNovedad,
						idTurno, empleado, ht);
			}

			
			res.setStatus(res.SC_OK);
			writer.print("{\"success\": \"true\"}");
		} catch (Exception ex) {
			String error = ex.getMessage();
			log.log(Level.SEVERE, error);
			res.setStatus(res.SC_INTERNAL_SERVER_ERROR);
			writer.print("{\"success\": false,\"error\": \"" +error+ "\" }");
		}finally{
			writer.flush();
			writer.close();			
		}

	}

}
