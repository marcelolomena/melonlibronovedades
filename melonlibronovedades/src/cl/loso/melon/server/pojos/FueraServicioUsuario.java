package cl.loso.melon.server.pojos;

public class FueraServicioUsuario {
	public FueraServicioUsuario(String fecha, String falla, String responsable,
			String equipo, String estado,String fechacompromiso, String fechasolucion,
			String comentario) {
		this.fecha = fecha;
		this.falla = falla;
		this.responsable = responsable;
		this.equipo = equipo;
		this.estado = estado;
		this.fechacompromiso = fechacompromiso;
		this.fechasolucion = fechasolucion;
		this.comentario = comentario;
	}

	private String fecha;

	private String falla;

	private String responsable;

	private String equipo;
	
	private String estado;
	
	private String fechacompromiso;
	
	private String fechasolucion;
	
	private String comentario;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFalla() {
		return falla;
	}

	public void setFalla(String falla) {
		this.falla = falla;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getFechacompromiso() {
		return fechacompromiso;
	}

	public void setFechacompromiso(String fechacompromiso) {
		this.fechacompromiso = fechacompromiso;
	}

	public String getFechasolucion() {
		return fechasolucion;
	}

	public void setFechasolucion(String fechasolucion) {
		this.fechasolucion = fechasolucion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
