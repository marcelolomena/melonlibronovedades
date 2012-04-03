package cl.loso.melon.server.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.BitacoraLN;
import cl.loso.melon.server.negocio.BitacoraLNBO;
import com.opensymphony.xwork2.ActionSupport;

public class BitacoraLNAction extends ActionSupport {
	private static Log log = LogFactory.getLog(BitacoraLNAction.class);
	private static final long serialVersionUID = 1L;
	private String idNovedad;
	private String idUsuario;
	private String nombre;
	private List<BitacoraLN> bitacoraList;
	
	public String obtenerBitacora() {
		try {
			bitacoraList = BitacoraLNBO.obtenerBitacoraLN(idUsuario, idNovedad);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	/*
	public String guardarBitacora() {
		try {
			BitacoraLNBO.guardarBitacoraLN(idUsuario, idNovedad, nombre, "");
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	*/
	public String getIdNovedad() {
		return idNovedad;
	}

	public void setIdNovedad(String idNovedad) {
		this.idNovedad = idNovedad;
	}

	public List<BitacoraLN> getBitacoraList() {
		return bitacoraList;
	}

	public void setBitacoraList(List<BitacoraLN> bitacoraList) {
		this.bitacoraList = bitacoraList;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
