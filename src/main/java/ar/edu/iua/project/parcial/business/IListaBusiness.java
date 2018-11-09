package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;

import java.util.List;

public interface IListaBusiness {
    public List<Lista> getAll() throws BusinessException;
    public void delete(Lista lista) throws BusinessException,NotFoundException;
    public Lista add (Lista lista) throws BusinessException;
    public Lista getOne(int id) throws BusinessException, NotFoundException;
    public Lista update(Lista lista) throws BusinessException, NotFoundException;
    public List<Lista> search(String part) throws BusinessException;

}
