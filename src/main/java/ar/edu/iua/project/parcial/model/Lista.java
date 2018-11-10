package ar.edu.iua.project.parcial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "listabd")
public class Lista {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lista_id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sprint")
    private String sprint;

    //@OneToMany(mappedBy = "listaNombre", fetch = FetchType.LAZY)
    //@OneToMany(mappedBy = "listaNombre", fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("listaNombre")
    @OneToMany(mappedBy = "listaNombre")
    @JsonIgnore
    private List<Tarea> tarea;

    public Lista() {
        super();
    }

    public Lista(Integer id,String nombre, String sprint, List<Tarea> tarea) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.sprint = sprint;
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

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public List<Tarea> getTarea() {
        return tarea;
    }

    public void setTarea(List<Tarea> tarea) {
        this.tarea = tarea;
    }
}
