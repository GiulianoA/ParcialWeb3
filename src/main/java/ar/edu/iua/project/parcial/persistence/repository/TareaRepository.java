package ar.edu.iua.project.parcial.persistence.repository;

import ar.edu.iua.project.parcial.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

}
