package ar.edu.iua.project.parcial.repository;

import ar.edu.iua.project.parcial.model.ListaSprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListaRepository extends JpaRepository<ListaSprint, Integer> {

    //@Query("SELECT new ar.edu.iua.project.parcial.model.ListaSprint(l.id, l.nombre,l.nombreSprint, l.tarea) from ListaSprint l inner join TareaSprint ON l.id=TareaSprint.id where l.nombre ='backlog' order by TareaSprint.fechaCreacion")

    public ListaSprint findByIdContains(int id);
    public ListaSprint getOneListaByNombre(String nombre);

}
