package cl.loso.melon.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cl.loso.melon.server.model.ImagenLN;
import cl.loso.melon.server.persistencia.NovedadLNDAO;


public class FileDownload extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(FileDownload.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String id=null;
		byte[] imagen=null;
		ServletOutputStream out=null;
		try {
			id=req.getParameter("id");
			
			ImagenLN img=NovedadLNDAO.getFoto(Long.valueOf(id));
			imagen=img.getFoto().getBytes();
			res.setContentType("image/jpg");
			out = res.getOutputStream();
			out.write(imagen);
			
		} catch (Exception ex) {
			log.warning(ex.getMessage());
		}finally {
            try {
            	out.close();
            } catch (Exception ignored) {
            }
        }

	}
}