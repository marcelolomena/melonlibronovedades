package cl.loso.melon.server.servlet;

public class Turnos {
	public Turnos(Long id, String turno) {
		super();
		this.id = id;
		Turno = turno;
	}

	public Long id;
	public String Turno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTurno() {
		return Turno;
	}

	public void setTurno(String turno) {
		Turno = turno;
	}
}
