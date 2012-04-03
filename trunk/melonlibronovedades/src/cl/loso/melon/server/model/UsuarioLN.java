package cl.loso.melon.server.model;

import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UsuarioLN {

	public UsuarioLN(String nombres, String apepa, String apema, String email,
			char perfil, String cargo, Long idNegocio,Boolean correo) {
		this.nombres = nombres;
		this.apepa = apepa;
		this.apema = apema;
		this.email = email;
		this.perfil = perfil;
		this.cargo = cargo;
		this.idNegocio = idNegocio;
		this.correo=correo;
	}

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String nombres;
    
    @Persistent
    private String apepa;
    
    @Persistent
    private String apema;
    
    @Persistent
    private String email;    
    
    @Persistent
    private char perfil;
    
    @Persistent
    private String cargo;
    
    @Persistent    
    private Long idNegocio;
    
    @Persistent
    private Boolean correo;

	@Persistent(mappedBy = "usuario")
	private List<NovedadLN> novedades;

	@Persistent(mappedBy = "usuario")
	private List<ListaEventoLN> eventos;
	
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApepa() {
		return apepa;
	}

	public void setApepa(String apepa) {
		this.apepa = apepa;
	}

	public String getApema() {
		return apema;
	}

	public void setApema(String apema) {
		this.apema = apema;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getPerfil() {
		return perfil;
	}

	public void setPerfil(char perfil) {
		this.perfil = perfil;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public List<NovedadLN> getNovedades() {
		return novedades;
	}

	public void setNovedades(List<NovedadLN> novedades) {
		this.novedades = novedades;
	}

	public Long getId() {
		return id;
	}

	public Long getIdNegocio() {
		return idNegocio;
	}

	public void setIdNegocio(Long idNegocio) {
		this.idNegocio = idNegocio;
	}

	public List<ListaEventoLN> getEventos() {
		return eventos;
	}

	public void setEventos(List<ListaEventoLN> eventos) {
		this.eventos = eventos;
	}

	public Boolean getCorreo() {
		return correo;
	}

	public void setCorreo(Boolean correo) {
		this.correo = correo;
	}
   
}
