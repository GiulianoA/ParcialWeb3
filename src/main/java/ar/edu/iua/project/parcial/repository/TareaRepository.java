package ar.edu.iua.project.parcial.repository;

import ar.edu.iua.project.parcial.model.ListaSprint;
import ar.edu.iua.project.parcial.model.TareaSprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<TareaSprint, Integer> {

    public TareaSprint getOneTareaByNombre(String nombre);
    public List<TareaSprint> findByNombreContaining(String nombre);
    public List<TareaSprint> findAllByOrderByPrioridadAsc();
    public List<TareaSprint> findAllByOrderByFechacreacionAsc();
    //public List<TareaSprint> findAllByNombreListaOrderByFechacreacionDesc();
    public List<TareaSprint> findAllByOrderByFechacreacion();
    TareaSprint findAllByIdContaining(Integer id);
    //List<TareaSprint> findAllByNombreListaOrderByFechacreacion(String nombreLista);
    List<TareaSprint> getAllByEstimacion(Integer estimacion);
    List<TareaSprint> getAllByNombreListaOrderByFechacreacion(ListaSprint lista);
    List<TareaSprint> getAllByNombreListaOrderByPrioridad(ListaSprint lista);
    List<TareaSprint> getAllByNombreLista(ListaSprint lista);
    

}
