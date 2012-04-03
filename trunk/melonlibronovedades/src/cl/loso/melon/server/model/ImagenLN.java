package cl.loso.melon.server.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class ImagenLN {
	
	public ImagenLN(Date fecha, Blob foto, Long idUsuario, Long idEquipo) {
		this.fecha = fecha;
		this.foto = foto;
		this.idUsuario = idUsuario;
		this.idEquipo = idEquipo;
	}

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long key;
 
    @Persistent
    private Date fecha; 
    
    @Persistent
    private Blob foto;
    
    @Persistent
    private Long idUsuario;     
    
    @Persistent
    private Long idEquipo;   
    
    @Persistent
    private Long idNovedad;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public Long getIdNovedad() {
		return idNovedad;
	}

	public void setIdNovedad(Long idNovedad) {
		this.idNovedad = idNovedad;
	}

	public Long getKey() {
		return key;
	}

}
