package cl.loso.melon.server.servlet;

import org.apache.commons.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.loso.melon.server.model.ImagenLN;
import cl.loso.melon.server.persistencia.NovedadLNDAO;

import com.google.appengine.api.datastore.Blob;

public class FileUpload extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(FileUpload.class
			.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		InputStream is = null;
		ByteArrayOutputStream fos = null;
		PrintWriter writer = null;
		byte[] imagen=null;
		String idEquipo=null;
		String idUsuario=null;
		
		try {
			idEquipo=req.getParameter("idEquipo");
			idUsuario=req.getParameter("idUsuario");
			//idVisita=req.getParameter("idVisita");
			
			log.info("QueryString : " + req.getQueryString());
			log.info("idEquipo : " + idEquipo);
			log.info("idUsuario : " + idUsuario);
			//log.info("idVisita : " + idVisita);

			
			writer = res.getWriter();
			is = req.getInputStream();
			fos=new ByteArrayOutputStream();
			IOUtils.copy(is, fos);
			log.info("tamaño : " + fos.size());
			imagen=fos.toByteArray();
			Blob blob=new Blob(imagen);
			ImagenLN imagenln=new ImagenLN(new Date(),blob,Long.valueOf(idUsuario),Long.valueOf(idEquipo));
			NovedadLNDAO.guardarfoto(imagenln);
			res.setStatus(res.SC_OK);
			writer.print("{success: true}");
			
			
		} catch (Exception ex) {
			String error=ex.getMessage();
			log.log(Level.SEVERE, error);
			res.setStatus(res.SC_INTERNAL_SERVER_ERROR);
            writer.print("{success:false, errorMessage:'" + error + "'}");
		}finally {
            try {
                fos.close();
                is.close();
            } catch (IOException ignored) {
            }
        }
		writer.flush();
        writer.close();
	}
}