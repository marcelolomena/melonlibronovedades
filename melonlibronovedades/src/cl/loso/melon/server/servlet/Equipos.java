package cl.loso.melon.server.servlet;

public class Equipos {
	public Equipos(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Long id;
	public String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
