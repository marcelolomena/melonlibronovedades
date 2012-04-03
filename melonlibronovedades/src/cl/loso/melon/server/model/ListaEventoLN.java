package cl.loso.melon.server.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class ListaEventoLN {


	public ListaEventoLN(Date fecha, Long idpadre, Long idUsuarioPadre,String estado,
			Long idResponsable) {
		this.fecha = fecha;
		this.idpadre = idpadre;
		this.idUsuarioPadre = idUsuarioPadre;
		this.estado = estado;
		this.idResponsable = idResponsable;
	}

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private Date fecha;    
 
    @Persistent
    private UsuarioLN usuario;
    
    @Persistent    
    private Long idpadre;
    
    @Persistent    
    private Long idUsuarioPadre;    
    
    @Persistent
    private String estado; 
    
    @Persistent 
    private FallaLN falla;
    
    @Persistent 
    private SolucionLN solucion;
    
    @Persistent    
    private Long idResponsable;    

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public UsuarioLN getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLN usuario) {
		this.usuario = usuario;
	}

	public Long getIdpadre() {
		return idpadre;
	}

	public void setIdpadre(Long idpadre) {
		this.idpadre = idpadre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public FallaLN getFalla() {
		return falla;
	}

	public void setFalla(FallaLN falla) {
		this.falla = falla;
	}

	public SolucionLN getSolucion() {
		return solucion;
	}

	public void setSolucion(SolucionLN solucion) {
		this.solucion = solucion;
	}

	public Long getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
	}

	public Long getIdUsuarioPadre() {
		return idUsuarioPadre;
	}

	public void setIdUsuarioPadre(Long idUsuarioPadre) {
		this.idUsuarioPadre = idUsuarioPadre;
	}  
    
}
