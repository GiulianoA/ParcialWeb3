package ar.edu.iua.project.parcial.model.persistence;

import ar.edu.iua.project.parcial.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer> {


    public List<Lista> findByNombreContaining(String nombre);

}
