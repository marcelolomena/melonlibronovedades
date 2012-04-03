package cl.loso.melon.server.model;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class NegocioLN {

	public NegocioLN(String nombre, String fecha) {
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public NegocioLN(Long id, String nombre, String fecha) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent(mappedBy = "negocio")
	private List<EquipoLN> equipos;
	
    @Persistent
    private String nombre; 	
    
    @Persistent
    private String fecha;

	public Long getId() {
		return id;
	}

	public List<EquipoLN> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<EquipoLN> sitios) {
		this.equipos = sitios;
	}

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

}