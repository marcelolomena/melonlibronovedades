package cl.loso.melon.server.model;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TipoLN {

	public TipoLN(String descripcion, Date horainicio, Date horatermino,Integer orden) {
		this.descripcion = descripcion;
		this.horainicio = horainicio;
		this.horatermino = horatermino;
		this.orden=orden;
	}

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String descripcion;

    @Persistent
    private Date horainicio;
    
    private Date horatermino;
    
    private Integer orden;

    public Long getId() {
        return id;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

	public Date getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(Date horainicio) {
		this.horainicio = horainicio;
	}

	public Date getHoratermino() {
		return horatermino;
	}

	public void setHoratermino(Date horatermino) {
		this.horatermino = horatermino;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}