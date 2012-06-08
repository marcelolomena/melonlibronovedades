package cl.loso.melon.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.negocio.NegocioLNBO;
import cl.loso.melon.server.util.Util;

public class HomeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(HomeServlet.class);
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		res.setContentType("text/html");
		PrintWriter writer = res.getWriter();
    	HttpSession session = req.getSession(true);
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfdiv=new SimpleDateFormat("dd_MM_yyyy");
		Long elNegocio=(Long)session.getAttribute("IdNegocio");

		//logger.info("elNegocio : " + elNegocio);		
		try {
			String txtNegocio=((NegocioLN)NegocioLNBO.editarNegocioLN(String.valueOf(elNegocio.longValue() ) ) ).getNombre();
			StringBuilder div = new StringBuilder("");		
			
			Date fechaIni=Util.hacetreintadias();
			Date fechaFin=Util.tomorow();
			//logger.info("fechaIni : " + fechaIni);
			//logger.info("fechaFin : " + fechaFin);
		
			Iterable<Entity> novedades=Util.listNovedadesHome(fechaIni,fechaFin,elNegocio);

			ArrayList<Date> listaFechas=new ArrayList<Date>();
			for(Entity novedad:novedades){
				Date fechaNovedad=(Date)novedad.getProperty("fecha");
				if(!listaFechas.contains(fechaNovedad)){
					//logger.info("agregando fechaNovedad : " + fechaNovedad);	
					listaFechas.add(fechaNovedad);
				}			
			}
			
			
			ArrayList<Turnos> listaTurnos=new ArrayList<Turnos>();
			Iterable<Entity> turnos=Util.listTurnoHome();
			for (Entity turno:turnos){
				String desc=(String)turno.getProperty("descripcion");
				Long id=Long.valueOf(turno.getKey().getId());
				listaTurnos.add(new Turnos(id,desc));
			}
			
			div.append("<div id=\"novedades\">");
			int i=1;
			ListIterator<Date> fechas=listaFechas.listIterator();
			while(fechas.hasNext()){
				Date lafecha=fechas.next();
				String fechaTxt = sdf.format(lafecha);
				String fechadiv = sdfdiv.format(lafecha);
				div.append("<h3><a href=\"#\">Novedad " + txtNegocio + " dia : " +fechaTxt+ "</a></h3>");
				div.append("<div>");
				div.append("<div id=\"print_" + fechadiv + "\"><a href=\"#\" onclick=\"imprimir('div#print_" + fechadiv + "');\" ><img src=\"../images/print.gif\" alt=\"imprimir\" width=\"16\" height=\"16\" border=\"0\" /></A>");
				
				div.append("<table class=\"table-home\" id=\"dnd-example\">");
				div.append("<thead>");
				div.append("<tr>");
				div.append("<th>Turno</th>");
				div.append("<th>Descripción</th>");
				div.append("<th>Usuario</th>");
				div.append("</tr>");
				div.append("</thead>");
				div.append("<tbody>");
	
				ListIterator<Turnos> enu=listaTurnos.listIterator();

				int j=1;
				while(enu.hasNext()){
					Turnos turno=enu.next();
					long key=turno.getId().longValue();
					String descripcion=turno.getTurno();
					
					Iterable<Entity> equipos=Util.listEquipoHome(elNegocio,key,lafecha);
						
						
						div.append("<tr id=\"node-"+String.valueOf(i)+String.valueOf(j)+"\">");
						div.append("<td><span class=\"folder\">" + descripcion + "</span></td>");
						div.append("<td>--</td>");
						div.append("<td>--</td>");
						div.append("</tr>");
						int k=1;
						//String usuarioAnterior="";
						for(Entity equipo:equipos){
							Text comentario=(Text)equipo.getProperty("comentario");
							String usuarioNombre=(String)equipo.getProperty("usuarioNombre");
							String equipoNombre=(String)equipo.getProperty("equipoNombre");
							/*
							if(usuarioNombre.equals(usuarioAnterior)){
								usuarioNombre="";
							}
							*/
							if(comentario.getValue()!=null && comentario.getValue().trim().length()>0) {
							
								div.append("<tr id=\"node-" + String.valueOf(i)+String.valueOf(j) + String.valueOf(k)+ "\" class=\"child-of-node-" + String.valueOf(i)+String.valueOf(j)+ "\">");
								div.append("<td nowrap><span class=\"file\">"+equipoNombre+"</span></td>");
								div.append("<td>" + comentario.getValue() + "</td>");
								div.append("<td>" + usuarioNombre + "</td>");
								div.append("</tr>");
							
							}
							
							//usuarioAnterior=usuarioNombre;
						}
				j++;
				}

				div.append("</tbody>");
				div.append("</table>");
				div.append("</div>");//el que imprime
				div.append("</div>");
				i++;
			}
			div.append("</div>");
			//logger.info(div.toString());
			writer.println(div.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			writer.flush();
			writer.close();
		}			

	}
}
