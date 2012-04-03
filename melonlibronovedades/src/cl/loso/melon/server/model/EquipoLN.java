package cl.loso.melon.server.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class EquipoLN {

	public EquipoLN() {
	}

	public EquipoLN(NegocioLN negocio, String nombre, String fecha) {
		this.negocio = negocio;
		this.nombre = nombre;
		this.fecha = fecha;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private NegocioLN negocio;
	
    @Persistent
    private String nombre; 	
    
    @Persistent
    private String fecha;


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Key getKey() {
		return key;
	}

	public NegocioLN getNegocio() {
		return negocio;
	}

	public void setNegocio(NegocioLN negocio) {
		this.negocio = negocio;
	}

	public void setKey(Key key) {
		this.key = key;
	}
}
