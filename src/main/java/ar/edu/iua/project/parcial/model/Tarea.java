package ar.edu.iua.project.parcial.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tareabd")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarea_id", nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_creacion")
    private String fechaCreacion;
    @Column(name = "fecha_modificacion")
    private String fecha_modificacion;
    @Column(name = "prioridad")
    private String prioridad;

    @ManyToOne
    @JoinColumn(name = "lista_id", nullable = false)
    //@JsonIgnoreProperties("tarea")
    private Lista listaNombre;
    @Column(name = "estimacion")
    private Integer estimacion;


    public Tarea(String nombre, String fechaCreacion, String fecha_modificacion, String prioridad, Lista listaNombre, Integer estimacion) {
        super();
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fecha_modificacion = fecha_modificacion;
        this.prioridad = prioridad;
        this.listaNombre = listaNombre;
        this.estimacion = estimacion;
    }

    public Tarea(){
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Lista getLista() {
        return listaNombre;
    }

    public void setLista(Lista lista) {
        this.listaNombre = lista;
    }

    public Integer getEstimacion() {
        return estimacion;
    }

    public void setEstimacion(Integer estimacion) {
        this.estimacion = estimacion;
    }
}
