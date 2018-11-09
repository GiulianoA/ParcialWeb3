package ar.edu.iua.project.parcial.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lista")
public class Lista {

    public Lista() {

    }

    public Lista(int idLista, String nombre, String sprint) {
        super();
        this.idLista = idLista;
        this.nombre = nombre;
        this.sprint = sprint;
    }


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idLista;

    private String nombre;
    private String sprint;

    @OneToMany(mappedBy="lista")   //eager
    @JsonIgnore
    private List<Tarea> tarea;



    public List<Tarea> getTarea() {
        return tarea;
    }

    public void setTarea(List<Tarea> tarea) {
        this.tarea = tarea;
    }

    public int getId() {
        return idLista;
    }

    public void setId(int idLista) {
        this.idLista = idLista;
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


}
