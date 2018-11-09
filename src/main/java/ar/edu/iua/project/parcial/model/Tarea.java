package ar.edu.iua.project.parcial.model;


import javax.persistence.*;


//@Table(name = "tarea")
@Entity
@Table(name = "tarea")
public class Tarea {

    public Tarea() {

    }



    public Tarea(int id,int estimacion , String fecha_creacion, String fecha_modificacion, String nombre, String prioridad,Lista lista
                 ) {
        super();
        this.id = id;
        this.estimacion = estimacion;
        this.fecha_creacion = fecha_creacion;
        this.fecha_modificacion = fecha_modificacion;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.lista = lista;
    }




    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    //@Column(name = "id", nullable = false)
    @Id
    @GeneratedValue
    private int id;

    //@Column(name = "estimacion")
    private int estimacion;

    //@Column(name = "fecha_creacion", length = 50)
    private String fecha_creacion;

    //@Column(name = "fecha_modificacion" , length = 50)
    private String fecha_modificacion;

   // @Column(name = "nombre" ,  length = 50)
    private String nombre;


   // @Column(name="prioridad", length = 50)
    private String prioridad;


    @ManyToOne //lazy
    @JoinColumn(name="id_lista",nullable = true)
    private Lista lista;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public int getEstimacion() {
        return estimacion;
    }

    public void setEstimacion(int estimacion) {
        this.estimacion = estimacion;
    }
}
