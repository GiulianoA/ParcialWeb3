package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.*;
import ar.edu.iua.project.parcial.model.TareaSprint;
import org.aspectj.weaver.ast.Not;

import java.util.List;
import java.util.Optional;

public interface ITareaBusiness {

    public TareaSprint add (TareaSprint tarea) throws FechaCreacionNulaException,ListaNotFoundException,InvalidPrioridadException, TareaExisteException;
    public List<TareaSprint> getAllTareas() throws BusinessException;
    public TareaSprint getUnaTareaPorNombre(String nombre) throws BusinessException, NotFoundException;
    public void delete(TareaSprint tarea) throws BusinessException, NotFoundException;
    public TareaSprint update(TareaSprint tarea) throws BusinessException, NotFoundException,ListaNulaException, ValorInvalidoEstimationException, ListaDestinoInvalidaException;
    public List<TareaSprint> search(String nombre) throws BusinessException;
    public List<TareaSprint> order(String o) throws BusinessException;


    public Optional<TareaSprint> findById(Integer id) throws BusinessException, NotFoundException;

    //List<TareaSprint> getTareasDeUnaLista(String buscar);
    List<TareaSprint> getTareasDeUnaLista();
}
