package ar.edu.iua.project.parcial.persistence.repository;

import ar.edu.iua.project.parcial.model.TareaSprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<TareaSprint, Integer> {

    public List<TareaSprint> findByNombreContaining(String nombre);
    public List<TareaSprint> findByEstimacionOrderByEstimacionDesc(String estimacion);
}
