package ar.edu.iua.project.parcial.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Comparator;
import java.util.Date;


@Entity
@Table(name = "tarea_sprint")
public class TareaSprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarea_id", nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "fechacreacion")
    private Date fechacreacion;
    @Column(name = "fechamodificacion")
    private Date fechamodificacion;
    @Column(name = "prioridad")
    private String prioridad;


    @ManyToOne
    @JoinColumn(name = "lista_id")
    @JsonIgnoreProperties("tarea")
    private ListaSprint nombreLista;
    @Column(name = "estimacion")
    private Integer estimacion;

   

    public TareaSprint(Integer id, String nombre, Date fechacreacion, Date fechamodificacion, String prioridad,
					   ListaSprint nombreLista, Integer estimacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechacreacion = fechacreacion;
		this.fechamodificacion = fechamodificacion;
		this.prioridad = prioridad;
		this.nombreLista = nombreLista;
		this.estimacion = estimacion;
	}




	public TareaSprint(){
        super();
    }




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public Date getFechacreacion() {
		return fechacreacion;
	}




	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}




	public Date getFechamodificacion() {
		return fechamodificacion;
	}




	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}




	public String getPrioridad() {
		return prioridad;
	}




	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}




	public ListaSprint getNombreLista() {
		return nombreLista;
	}




	public void setNombreLista(ListaSprint nombreLista) {
		this.nombreLista = nombreLista;
	}




	public Integer getEstimacion() {
		return estimacion;
	}




	public void setEstimacion(Integer estimacion) {
		this.estimacion = estimacion;
	}




}

