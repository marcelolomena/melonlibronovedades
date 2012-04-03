package cl.loso.melon.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.negocio.NegocioLNBO;
import cl.loso.melon.server.util.Util;

public class HomeEquipoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(HomeEquipoServlet.class);
	
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
		
			Iterable<Entity> novedades=Util.listNovedadesHome(fechaIni,fechaFin,elNegocio);

			ArrayList<Date> listaFechas=new ArrayList<Date>();
			for(Entity novedad:novedades){
				Date fechaNovedad=(Date)novedad.getProperty("fecha");
				if(!listaFechas.contains(fechaNovedad)){
					listaFechas.add(fechaNovedad);
				}			
			}
			ListIterator<Date> fechas=listaFechas.listIterator();
			
			div.append("<div id=\"novedades\">");
			int i=1;
			
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
				div.append("<th>Equipo</th>");
				div.append("<th>Descripción</th>");
				div.append("<th>Usuario</th>");
				div.append("</tr>");
				div.append("</thead>");
				div.append("<tbody>");
	
				Iterable<Entity> novedades2=Util.listNovedades(lafecha,elNegocio);

				ArrayList<Long> listaDeNovedades=new ArrayList<Long>();
				for(Entity novedad:novedades2){
					Long equipoId=(Long)novedad.getProperty("equipo");//1
					if(!listaDeNovedades.contains(equipoId)){

						listaDeNovedades.add(equipoId);
					}			
				}
				
				Iterator<Long> iteNovedad=listaDeNovedades.iterator();		
			
				int j=1;
				while(iteNovedad.hasNext()){
					Long idEquipo=iteNovedad.next();
					Key llaveNegocio=KeyFactory.createKey("NegocioLN",elNegocio.longValue());
					Key llaveEquipo = KeyFactory.createKey(llaveNegocio, "EquipoLN", idEquipo.longValue());
					
					Entity equipoLN=Util.findEntity(llaveEquipo);//si eliminaron el equipo cago
					String descripcion="borrado";
					
					if(equipoLN!=null){
						descripcion=(String)equipoLN.getProperty("nombre");
					}
					
					//logger.info("llaveNegocio : " + idEquipo + " llaveEquipo " + elNegocio);
					Iterable<Entity> novedadesIter=Util.listNovedadesHome2(elNegocio,idEquipo,lafecha);
						
						div.append("<tr id=\"node-"+String.valueOf(i)+String.valueOf(j)+"\">");
						div.append("<td nowrap><span class=\"folder\">" + descripcion + "</span></td>");
						div.append("<td>--</td>");
						div.append("<td>--</td>");
						div.append("</tr>");
						int k=1;
						//String usuarioAnterior="";
						for(Entity nov:novedadesIter){
							String comentario=(String)nov.getProperty("comentario");
							String usuarioNombre=(String)nov.getProperty("usuarioNombre");
							String turnoNombre=(String)nov.getProperty("turnoNombre");
							/*
							if(usuarioNombre.equals(usuarioAnterior)){
								usuarioNombre="";
							}	
							*/						
							div.append("<tr id=\"node-" + String.valueOf(i)+String.valueOf(j) + String.valueOf(k)+ "\" class=\"child-of-node-" + String.valueOf(i)+String.valueOf(j)+ "\">");
							div.append("<td nowrap><span class=\"file\">" +  turnoNombre + "</span></td>");
							div.append("<td>" + comentario + "</td>");
							div.append("<td>" + usuarioNombre + "</td>");
							div.append("</tr>");
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
