package cl.loso.melon.server.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class FallaLN {
	public FallaLN(Date fecha,String problema, Long equipo, String equipoNombre) {
		this.fecha=fecha;
		this.problema = problema;
		this.equipo = equipo;
		this.equipoNombre = equipoNombre;
		this.fts = new HashSet<String>();      
        BusquedaML.actualizaFTSFalla(this);		
	}

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
    @Persistent
    private Date fecha; 	
	
    @Persistent
    private String problema;
    
    @Persistent
    private Long equipo;   
    
    @Persistent
    private Set<String> fts;    
    
    @Persistent
    private String equipoNombre;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
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

	public Set<String> getFts() {
		return fts;
	}

	public void setFts(Set<String> fts) {
		this.fts = fts;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}  	
}
