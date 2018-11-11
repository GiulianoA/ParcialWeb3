package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Tarea;

import java.util.List;

public interface ITareaBusiness {

    public Tarea add (Tarea tarea) throws BusinessException;
    public List<Tarea> getAll() throws BusinessException;
    public Tarea getOne(String nombre) throws BusinessException, NotFoundException;
    public void delete(Tarea tarea) throws BusinessException, NotFoundException;
    public Tarea update(Tarea tarea) throws BusinessException, NotFoundException;
}
