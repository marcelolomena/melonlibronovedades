package cl.loso.melon.server.action;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cl.loso.melon.server.model.TipoLN;
import cl.loso.melon.server.negocio.TipoLNBO;
import com.opensymphony.xwork2.ActionSupport;

public class TipoLNAction extends ActionSupport {
	private static Log log = LogFactory.getLog(TipoLNAction.class);
	private static final long serialVersionUID = 1783114341654699134L;
	private List<TipoLN> tipoList;
	private String tipo;
	private String id;
	private TipoLN tipoLN;
	private String horini;
	private String horter;
	private String orden;
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String item) {
		this.tipo = item;
	}

	public String guardarTipo() {
		try {
			TipoLNBO.guardarTipoLN(tipo,horini,horter,orden);
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}
	
	public String actualizarTipo() {
		try {
			TipoLNBO.actualizarTipoLN(id,tipo,horini,horter,orden);
		} catch (Exception e) {
			log.error(e);
		}

		return ActionSupport.SUCCESS;
	}	

	public String obtenerTipo() {
		try {
			tipoList = TipoLNBO.obtenerTipoLN();
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}
	
	public String borrarTipo() {
		try {
			TipoLNBO.borrarTipoLN(id);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	
	
	public String editarTipo() {
		try {
			tipoLN=TipoLNBO.editarTipoLN(id);
		} catch (Exception e) {
			log.error(e);
		}
		return ActionSupport.SUCCESS;
	}	

	public List<TipoLN> getTipoList() {
		return tipoList;
	}

	public void setTipoList(List<TipoLN> itemList) {
		this.tipoList = itemList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipoLN getTipoLN() {
		return tipoLN;
	}

	public void setTipoLN(TipoLN itemLVS) {
		this.tipoLN = itemLVS;
	}

	public String getHorini() {
		return horini;
	}

	public void setHorini(String horini) {
		this.horini = horini;
	}

	public String getHorter() {
		return horter;
	}

	public void setHorter(String horter) {
		this.horter = horter;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
}
