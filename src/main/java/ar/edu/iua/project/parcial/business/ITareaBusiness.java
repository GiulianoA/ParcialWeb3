package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.TareaSprint;

import java.util.List;

public interface ITareaBusiness {

    public TareaSprint add (TareaSprint tarea) throws BusinessException;
    public List<TareaSprint> getAll() throws BusinessException;
    public TareaSprint getOne(String nombre) throws BusinessException, NotFoundException;
    public void delete(TareaSprint tarea) throws BusinessException, NotFoundException;
    public TareaSprint update(TareaSprint tarea) throws BusinessException, NotFoundException;
    public List<TareaSprint> search(String nombre) throws BusinessException;
    public List<TareaSprint> order(String estimacion) throws BusinessException;
}
