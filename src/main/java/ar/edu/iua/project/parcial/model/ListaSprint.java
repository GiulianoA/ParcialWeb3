package ar.edu.iua.project.parcial.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "lista_sprint")
public class ListaSprint {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lista_id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nombre_sprint")
    private String nombreSprint;

    @OneToMany(mappedBy = "nombreLista", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("nombreLista")
    private List<TareaSprint> tarea;

    public ListaSprint() {
        super();
    }



	public ListaSprint(Integer id, String nombre, String nombreSprint, List<TareaSprint> tarea) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreSprint = nombreSprint;
		this.tarea = tarea;
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



	public String getNombreSprint() {
		return nombreSprint;
	}



	public void setNombreSprint(String nombreSprint) {
		this.nombreSprint = nombreSprint;
	}



	public List<TareaSprint> getTarea() {
		return tarea;
	}



	public void setTarea(List<TareaSprint> tarea) {
		this.tarea = tarea;
	}

	
    
}
