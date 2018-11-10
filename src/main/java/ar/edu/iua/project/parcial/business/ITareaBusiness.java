package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Tarea;

import java.util.List;

public interface ITareaBusiness {

    public List<Tarea> getAll() throws BusinessException;
    public void delete(Tarea tarea) throws BusinessException, NotFoundException;
    public Tarea add (Tarea tarea) throws BusinessException;
    public Tarea getOne(String nombre) throws BusinessException, NotFoundException;
    public Tarea update(Tarea tarea) throws BusinessException, NotFoundException;
   // public List<Tarea> search(String part) throws BusinessException;

}
