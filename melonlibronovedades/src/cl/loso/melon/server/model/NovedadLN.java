package cl.loso.melon.server.model;

import java.util.Date;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class NovedadLN {

	public NovedadLN(Date fecha, Long negocio, String negocioNombre,
			UsuarioLN usuario, Long turno, String turnoNombre, String empleado,Integer orden) {
		this.fecha = fecha;
		this.negocio = negocio;
		this.negocioNombre = negocioNombre;
		this.usuario = usuario;
		this.turno = turno;
		this.turnoNombre = turnoNombre;
		this.empleado = empleado;
		this.orden = orden;
	}

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private Date fecha;    
    
	@Persistent
	private Long negocio;
	
	@Persistent
	private String negocioNombre;	
	
    @Persistent
    private UsuarioLN usuario;
     
    @Persistent
    private Long turno;   
	
	@Persistent
	private String turnoNombre;    
    
    @Persistent
    private String empleado;  
    
    @Persistent
    private Integer orden;
    
	@Persistent(mappedBy = "novedad")
	private List<BitacoraLN> comentarios;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getNegocio() {
		return negocio;
	}

	public void setNegocio(Long negocio) {
		this.negocio = negocio;
	}

	public UsuarioLN getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLN usuario) {
		this.usuario = usuario;
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

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public List<BitacoraLN> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<BitacoraLN> comentarios) {
		this.comentarios = comentarios;
	}

	public Key getKey() {
		return key;
	}

	public String getNegocioNombre() {
		return negocioNombre;
	}

	public void setNegocioNombre(String negocioNombre) {
		this.negocioNombre = negocioNombre;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
