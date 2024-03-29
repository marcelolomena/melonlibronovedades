package cl.loso.melon.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.model.EquipoLN;
import cl.loso.melon.server.model.FallaLN;
import cl.loso.melon.server.model.UsuarioLN;
import cl.loso.melon.server.negocio.EquipoLNBO;
import cl.loso.melon.server.negocio.ReporteLNBO;
import cl.loso.melon.server.negocio.UsuarioLNBO;
import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReporteLNAction extends ActionSupport {
	private static Log log = LogFactory.getLog(ReporteLNAction.class);
	private static final long serialVersionUID = 1L;
	private String fechaini;
	private String fechater;
	private String texto;
	private String idUsuario;
	private List<FallaLN> fallaList;
	private List<BitacoraLN> novedadList;
	private List<EquipoLN> equipoList;
	private InputStream inputStream;
	private String tipo;
	private String idEquipo;

	public String reporteFallas() {
		try {
			if (texto.trim().length() > 0) {
				fallaList = ReporteLNBO.reporteFallas(fechaini, fechater,
						texto, idEquipo);
			} else {
				fallaList = ReporteLNBO.reporteFallasSinTexto(fechaini,
						fechater, idEquipo);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public String obtenerEquipos() {
		try {
			UsuarioLN usr = UsuarioLNBO.editarUsuarioLN(idUsuario);
			equipoList = EquipoLNBO.obtenerEquipoLN(String.valueOf(usr
					.getIdNegocio()));
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public String reporteFallasPDF() {
		try {

			PdfPTable tabla = null;
			Document document = new Document();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			Map<String, Object> session = ActionContext.getContext().getSession();

			HeaderFooter event = new HeaderFooter((String)session.get("txtNegocio"));
			writer.setPageEvent(event);

			document.open();

			if (tipo.equals("0")) {// falla
				if (texto.trim().length() > 0) {
					fallaList = ReporteLNBO.reporteFallas(fechaini, fechater,
							texto, idEquipo);
				} else {
					fallaList = ReporteLNBO.reporteFallasSinTexto(fechaini,
							fechater, idEquipo);
				}
				Iterator<FallaLN> iteFallas = fallaList.iterator();
				String[] headers = new String[] { "Fecha", "Equipo", "Problema" };
				tabla = new PdfPTable(headers.length);
				tabla.setWidthPercentage(100);
				
				float[] widths = new float[] { 1f, 3f, 4f };
				
				tabla.setWidths(widths);
				
				for (int i = 0; i < headers.length; i++) {
					String header = headers[i];
					PdfPCell cell = new PdfPCell();
					cell.setGrayFill(0.9f);
					cell.setPhrase(new Phrase(header.toUpperCase(), new Font(
							FontFamily.HELVETICA, 10, Font.BOLD)));
					tabla.addCell(cell);
				}
				tabla.completeRow();

				while (iteFallas.hasNext()) {
					FallaLN falla = iteFallas.next();
					PdfPCell cell0 = new PdfPCell();
					PdfPCell cell1 = new PdfPCell();
					PdfPCell cell2 = new PdfPCell();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String fecha = sdf.format(falla.getFecha());
					cell0.setPhrase(new Phrase(fecha, new Font(
							FontFamily.HELVETICA, 8, Font.NORMAL)));
					cell1.setPhrase(new Phrase(falla.getEquipoNombre(),
							new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
					cell2.setPhrase(new Phrase(falla.getProblema(), new Font(
							FontFamily.HELVETICA, 8, Font.NORMAL)));
					tabla.addCell(cell0);
					tabla.addCell(cell1);
					tabla.addCell(cell2);
					tabla.completeRow();
				}
			} else if (tipo.equals("1")) {// novedad
				if (texto.trim().length() > 0) {
					novedadList = ReporteLNBO.reporteNovedades(fechaini,
							fechater, texto, idEquipo);
				} else {
					novedadList = ReporteLNBO.reporteNovedadesSinTexto(
							fechaini, fechater, idEquipo);
				}
				Iterator<BitacoraLN> iteNovedadesfallas = novedadList
						.iterator();
				String[] headers = new String[] { "Fecha", "Usuario", "Turno",
						"Equipo", "Novedad" };
				tabla = new PdfPTable(headers.length);
				tabla.setWidthPercentage(100);
				
				
				float[] widths = new float[] { 2f, 2f, 1f,2f, 5f };
				
				tabla.setWidths(widths);
				
				for (int i = 0; i < headers.length; i++) {
					String header = headers[i];
					PdfPCell cell = new PdfPCell();
					cell.setGrayFill(0.9f);
					cell.setPhrase(new Phrase(header.toUpperCase(), new Font(
							FontFamily.HELVETICA, 10, Font.BOLD)));
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
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String fecha = sdf.format(bitacora.getFecha());
					cell0.setPhrase(new Phrase(fecha, new Font(
							FontFamily.HELVETICA, 8, Font.NORMAL)));
					if (bitacora.getUsuarioNombre() != null) {
						cell1.setPhrase(new Phrase(bitacora.getUsuarioNombre(),
								new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
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
			}

			document.add(tabla);

			document.close();
			inputStream = new ByteArrayInputStream(baos.toByteArray());

		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public String reporteNovedades() {
		try {
			if (texto.trim().length() > 0) {
				novedadList = ReporteLNBO.reporteNovedades(fechaini, fechater,
						texto, idEquipo);
			} else {
				novedadList = ReporteLNBO.reporteNovedadesSinTexto(fechaini,
						fechater, idEquipo);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}

	public String getFechaini() {
		return fechaini;
	}

	public void setFechaini(String fechaini) {
		this.fechaini = fechaini;
	}

	public String getFechater() {
		return fechater;
	}

	public void setFechater(String fechater) {
		this.fechater = fechater;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<FallaLN> getFallaList() {
		return fallaList;
	}

	public void setFallaList(List<FallaLN> fallaList) {
		this.fallaList = fallaList;
	}

	public List<BitacoraLN> getNovedadList() {
		return novedadList;
	}

	public void setNovedadList(List<BitacoraLN> novedadList) {
		this.novedadList = novedadList;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<EquipoLN> getEquipoList() {
		return equipoList;
	}

	public void setEquipoList(List<EquipoLN> equipoList) {
		this.equipoList = equipoList;
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	class HeaderFooter extends PdfPageEventHelper {
		private String negocio;
		
		public HeaderFooter(String negocio){
			this.negocio=negocio;
		}

        public void onStartPage(PdfWriter writer, Document document) {

            try {
				
                Rectangle page = document.getPageSize();

                PdfPCell imageCell = null;
                PdfPCell textCell = null;

                PdfPTable head = new PdfPTable(2);
                float[] widths = new float[] { 1f, 4f };
                head.setWidths(widths);

	        	ServletContext context = ServletActionContext.getServletContext();
				String relativeWebPath = "/images/logo_melon.gif";
				String absoluteDiskPath = context.getRealPath(relativeWebPath);
				Image headImage = Image.getInstance(absoluteDiskPath);
				headImage.scaleToFit(75, 75);
				
                imageCell = new PdfPCell(headImage);
                imageCell.setBorder(Rectangle.NO_BORDER);
                head.addCell(imageCell);

                Font catFont = new Font(Font.FontFamily.HELVETICA, 16,
            			Font.BOLD);
                textCell = new PdfPCell(new Phrase("Reporte de Novedades: " + negocio,catFont));
                textCell.setBorder(Rectangle.NO_BORDER);
                head.addCell(textCell);

                head.setTotalWidth(page.getWidth() - document.leftMargin()
                                - document.rightMargin());

                head.writeSelectedRows(0, -1, document.leftMargin(), page
                                .getHeight()
                                - document.topMargin() + head.getTotalHeight(), writer
                                .getDirectContent());	
                document.add(new Paragraph("\n"));

            }
            catch(Exception de) {
                throw new ExceptionConverter(de);
            }
        }

	

	}
}
