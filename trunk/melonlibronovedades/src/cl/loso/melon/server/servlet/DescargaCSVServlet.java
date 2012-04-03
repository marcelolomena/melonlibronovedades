package cl.loso.melon.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import  java.util.UUID;

@SuppressWarnings("serial")
public class DescargaCSVServlet extends HttpServlet {
	private static Log log = LogFactory.getLog(DescargaCSVServlet.class);	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String value = req.getParameter("dataResultados");
		log.info(value);
		if (value != null) {
			String nombre = "metas_" + UUID.randomUUID().toString() + ".xls";

			resp.setHeader("Content-type","application/vnd.ms-excel" );
			resp.setHeader("Content-Disposition","attachment; filename=\""	+ nombre + "\"");
			
			PrintWriter out = resp.getWriter();
			out.write(value);
			out.close();

		}

	}
}