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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.NegocioLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.negocio.NegocioLNBO;
import cl.loso.melon.server.persistencia.BitacoraLNDAO;
import cl.loso.melon.server.persistencia.UsuarioLNDAO;
import cl.loso.melon.server.util.Util;

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
		mandaCorreo(req, res);
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
					cell2.setPhrase(new Phrase(falla.getComentario().getValue(), new Font(
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

				Query q = new Query(UsuarioLN.class.getSimpleName());

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
			} else {
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

	public void mandaCorreo(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		PdfPTable tabla = null;
		List<BitacoraLN> bitacoraList;

		try {

			Iterable<Entity> usuarios = Util.listObjectEntities("UsuarioLN",
					"correo", new Boolean(true));

			for (Entity usuario : usuarios) {
				Long idNegocio = (Long) usuario.getProperty("idNegocio");
				String email = (String) usuario.getProperty("email");
				String nombres =(String) usuario.getProperty("nombres");
				String apepa =(String) usuario.getProperty("apepa");
				
				NegocioLN negocio = NegocioLNBO.editarNegocioLN(String
						.valueOf(idNegocio.longValue()));
				String txtNegocio = negocio.getNombre();
				bitacoraList = BitacoraLNDAO.obtenerNovedadesAyer(idNegocio);

				if (!bitacoraList.isEmpty()) {

					Document document = new Document();

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PdfWriter writer = PdfWriter.getInstance(document, baos);

					Cabecera event = new Cabecera(txtNegocio);
					writer.setPageEvent(event);

					document.open();

					Iterator<BitacoraLN> iteNovedadesfallas = bitacoraList
							.iterator();

					String[] headers = new String[] { "Fecha", "Usuario",
							"Turno", "Equipo", "Novedad" };
					tabla = new PdfPTable(headers.length);
					tabla.setWidthPercentage(100);

					float[] widths = new float[] { 2f, 2f, 1f, 2f, 5f };

					tabla.setWidths(widths);

					for (int i = 0; i < headers.length; i++) {
						String header = headers[i];
						PdfPCell cell = new PdfPCell();
						cell.setGrayFill(0.9f);
						cell.setPhrase(new Phrase(header.toUpperCase(),
								new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
						tabla.addCell(cell);
					}
					tabla.completeRow();
					while (iteNovedadesfallas.hasNext()) {

						BitacoraLN bitacora = iteNovedadesfallas.next();
						PdfPCell cell0 = new PdfPCell();
						PdfPCell cell1 = new PdfPCell();
						PdfPCell cell2 = new PdfPCell();
						PdfPCell cell3 = new PdfPCell();
						PdfPCell cell4 = new PdfPCell();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd/MM/yyyy");
						String fecha = sdf.format(bitacora.getFecha());
						cell0.setPhrase(new Phrase(fecha, new Font(
								FontFamily.HELVETICA, 8, Font.NORMAL)));
						if (bitacora.getUsuarioNombre() != null) {
							cell1.setPhrase(new Phrase(bitacora
									.getUsuarioNombre(), new Font(
									FontFamily.HELVETICA, 8, Font.NORMAL)));
						} else {
							cell1.setPhrase(new Phrase("", new Font(
									FontFamily.HELVETICA, 8, Font.NORMAL)));
						}
						cell2.setPhrase(new Phrase(bitacora.getTurnoNombre(),
								new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
						cell3.setPhrase(new Phrase(bitacora.getEquipoNombre(),
								new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
						cell4.setPhrase(new Phrase(bitacora.getComentario().getValue(),
								new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
						tabla.addCell(cell0);
						tabla.addCell(cell1);
						tabla.addCell(cell2);
						tabla.addCell(cell3);
						tabla.addCell(cell4);
						tabla.completeRow();
					}

					document.add(tabla);

					document.close();

					byte[] pdf = baos.toByteArray();
					Properties props = new Properties();
					Session session = Session.getDefaultInstance(props, null);

					Calendar cal = new GregorianCalendar();
					cal.add(Calendar.DATE, -1);
					Date ayer = cal.getTime();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd_MM_yyyy");
					String fechadeayer = sdf.format(ayer);
					String fechadeayer2 = sdf2.format(ayer);

					// Define message
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(from));
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(email));
					message.setSubject("Novedades del " + fechadeayer);
					// Create the message part
					BodyPart messageBodyPart = new MimeBodyPart();
					// Fill the message
					messageBodyPart
							.setText("Estimado " + nombres + " " + apepa + ", se adjuntan las novedades del dia " + fechadeayer);

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(pdf,
							"application/pdf");
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName("novedades_"+fechadeayer2+".pdf");
					messageBodyPart.setDisposition(Part.ATTACHMENT);
					multipart.addBodyPart(messageBodyPart);
					// Put parts in message
					message.setContent(multipart);
					// Send the message
					Transport.send(message);

				}

			}

		} catch (Exception e) {
			log.severe(e.getMessage());
		}

	}

	class Cabecera extends PdfPageEventHelper {
		private String negocio;

		public Cabecera(String negocio) {
			this.negocio = negocio;
		}

		public void onStartPage(PdfWriter writer, Document document) {

			try {

				Rectangle page = document.getPageSize();

				PdfPCell imageCell = null;
				PdfPCell textCell = null;

				PdfPTable head = new PdfPTable(2);
				float[] widths = new float[] { 1f, 4f };
				head.setWidths(widths);
				ServletContext context = getServletContext();
				
				String relativeWebPath = "/images/logo_melon.gif";
				String absoluteDiskPath = context.getRealPath(relativeWebPath);
				Image headImage = Image.getInstance(absoluteDiskPath);
				headImage.scaleToFit(75, 75);

				imageCell = new PdfPCell(headImage);
				imageCell.setBorder(Rectangle.NO_BORDER);
				head.addCell(imageCell);

				Font catFont = new Font(Font.FontFamily.HELVETICA, 16,
						Font.BOLD);
				textCell = new PdfPCell(new Phrase("Reporte de Novedades: "
						+ negocio, catFont));
				textCell.setBorder(Rectangle.NO_BORDER);
				head.addCell(textCell);

				head.setTotalWidth(page.getWidth() - document.leftMargin()
						- document.rightMargin());

				head.writeSelectedRows(
						0,
						-1,
						document.leftMargin(),
						page.getHeight() - document.topMargin()
								+ head.getTotalHeight(),
						writer.getDirectContent());
				document.add(new Paragraph("\n"));

			} catch (Exception de) {
				log.severe(de.getMessage());
				throw new ExceptionConverter(de);
			}
		}

	}

}