package cl.loso.melon.server.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.persistencia.BitacoraLNDAO;
import cl.loso.melon.server.persistencia.UsuarioLNDAO;

public class ReporteNovedadesDiario extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(ReporteNovedadesDiario.class.getName());
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

			PdfPTable tabla = null;
			Document document = new Document();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);

			document.open();

			List<BitacoraLN> bitacoraList = BitacoraLNDAO
					.obtenerNovedadesAyer();

			if (!bitacoraList.isEmpty()) {

				Iterator<BitacoraLN> iteNovedades = bitacoraList.iterator();

				String[] headers = new String[] { "Fecha", "Equipo",
						"Comentario" };
				tabla = new PdfPTable(headers.length);
				for (int i = 0; i < headers.length; i++) {
					String header = headers[i];
					PdfPCell cell = new PdfPCell();
					cell.setGrayFill(0.9f);
					cell.setPhrase(new Phrase(header.toUpperCase(), new Font(
							FontFamily.HELVETICA, 10, Font.BOLD)));
					tabla.addCell(cell);
				}
				tabla.completeRow();

				while (iteNovedades.hasNext()) {
					BitacoraLN falla = iteNovedades.next();
					PdfPCell cell0 = new PdfPCell();
					PdfPCell cell1 = new PdfPCell();
					PdfPCell cell2 = new PdfPCell();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String fecha = sdf.format(falla.getFecha());
					cell0.setPhrase(new Phrase(fecha, new Font(
							FontFamily.HELVETICA, 10, Font.NORMAL)));
					cell1.setPhrase(new Phrase(falla.getEquipoNombre(),
							new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
					cell2.setPhrase(new Phrase(falla.getComentario(), new Font(
							FontFamily.HELVETICA, 10, Font.NORMAL)));
					tabla.addCell(cell0);
					tabla.addCell(cell1);
					tabla.addCell(cell2);
					tabla.completeRow();
				}

				document.add(tabla);

				document.close();
				byte[] pdf = baos.toByteArray();

				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();

				com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
						UsuarioLN.class.getSimpleName());

				q.addFilter("correo", FilterOperator.EQUAL, new Boolean(true));

				FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

				List<Entity> entities = datastore.prepare(q).asList(
						fetchOptions);

				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				Calendar cal = new GregorianCalendar();
				cal.add(Calendar.DATE, -1);
				Date ayer = cal.getTime();

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fechadeayer = sdf.format(ayer);
				

				for (Entity entity : entities) {
					Key llave = (Key) entity.getKey();
					UsuarioLN usuario = UsuarioLNDAO.getUsuarioLNbyId(llave
							.getId());

					// Define message
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(from));
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(usuario.getEmail()));
					message.setSubject("Novedades del " + fechadeayer);
					// Create the message part
					BodyPart messageBodyPart = new MimeBodyPart();
					// Fill the message
					messageBodyPart
							.setText("Estimado usuario, adjuntan las novedades del dia anterior");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(pdf,
							"application/pdf");
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName("novedades.pdf");
					messageBodyPart.setDisposition(Part.ATTACHMENT);
					multipart.addBodyPart(messageBodyPart);
					// Put parts in message
					message.setContent(multipart);
					// Send the message
					Transport.send(message);
				}
			}else{
				log.info("no hay novedades de ayer");
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