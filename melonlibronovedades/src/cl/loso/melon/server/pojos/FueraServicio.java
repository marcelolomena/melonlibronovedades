package cl.loso.melon.server.pojos;

public class FueraServicio {

	public FueraServicio(String fecha, String falla, String responsable,
			String equipo) {
		this.fecha = fecha;
		this.falla = falla;
		this.responsable = responsable;
		this.equipo = equipo;
	}

	private String fecha;

	private String falla;

	private String responsable;

	private String equipo;

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
}
