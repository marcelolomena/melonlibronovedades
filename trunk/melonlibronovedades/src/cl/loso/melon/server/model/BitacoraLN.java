package cl.loso.melon.server.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class BitacoraLN {

	public BitacoraLN(Entity entity) {
		this.key=entity.getKey();
		this.fecha = (Date)entity.getProperty("fecha");
		this.comentario = (String)entity.getProperty("comentario");
		this.equipo = (Long)entity.getProperty("equipo");
		this.equipoNombre = (String)entity.getProperty("equipoNombre");
		this.turno = (Long)entity.getProperty("turno");
		this.turnoNombre = (String)entity.getProperty("turnoNombre");
		this.negocio = (Long)entity.getProperty("negocio");
		this.negocioNombre = (String)entity.getProperty("negocioNombre");
		this.usuarioNombre = (String)entity.getProperty("usuarioNombre");
	}
	
	
	public BitacoraLN(Date fecha, String comentario, Long equipo,
			String equipoNombre, Long turno, String turnoNombre, Long negocio,
			String negocioNombre,String usuarioNombre) {
		this.fecha = fecha;
		this.comentario = comentario;
		this.equipo = equipo;
		this.equipoNombre = equipoNombre;
		this.turno = turno;
		this.turnoNombre = turnoNombre;
		this.negocio = negocio;
		this.negocioNombre = negocioNombre;
		this.usuarioNombre = usuarioNombre;
		this.fts = new HashSet<String>();      
		BusquedaML.actualizarFTSNovedad(this);		
	}

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
 
    @Persistent
    private Date fecha; 
    
    @Persistent
    private String comentario;
    
    @Persistent
    private Text texto;    
    
    @Persistent
    private Long equipo;   
    
    @Persistent
    private String equipoNombre;     
    
    @Persistent
    private NovedadLN novedad;
    
    @Persistent
    private Long turno;   
	
	@Persistent
	private String turnoNombre;
	
	@Persistent
	private Long negocio;
	
	@Persistent
	private String negocioNombre;
	
	@Persistent
	private String usuarioNombre;		
	
    @Persistent
    private Set<String> fts;   	

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Long getEquipo() {
		return equipo;
	}

	public void setEquipo(Long equipo) {
		this.equipo = equipo;
	}

	public String getEquipoNombre() {
		return equipoNombre;
	}

	public void setEquipoNombre(String equipoNombre) {
		this.equipoNombre = equipoNombre;
	}

	public NovedadLN getNovedad() {
		return novedad;
	}

	public void setNovedad(NovedadLN novedad) {
		this.novedad = novedad;
	}

	public Long getTurno() {
		return turno;
	}

	public void setTurno(Long turno) {
		this.turno = turno;
	}

	public String getTurnoNombre() {
		return turnoNombre;
	}

	public void setTurnoNombre(String turnoNombre) {
		this.turnoNombre = turnoNombre;
	}

	public Key getKey() {
		return key;
	}

	public Long getNegocio() {
		return negocio;
	}

	public void setNegocio(Long negocio) {
		this.negocio = negocio;
	}

	public String getNegocioNombre() {
		return negocioNombre;
	}

	public void setNegocioNombre(String negocioNombre) {
		this.negocioNombre = negocioNombre;
	}

	public Set<String> getFts() {
		return fts;
	}

	public void setFts(Set<String> fts) {
		this.fts = fts;
	}


	public String getUsuarioNombre() {
		return usuarioNombre;
	}


	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}


	public Text getTexto() {
		return texto;
	}


	public void setTexto(Text texto) {
		this.texto = texto;
	}     

}
