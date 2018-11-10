package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.InvalidListNameException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;

import java.util.List;

public interface IListaBusiness {
    public List<Lista> getAll() throws BusinessException;
    public Lista add (Lista lista) throws BusinessException, NotFoundException, InvalidListNameException;
    public Lista getOne(String nombre) throws BusinessException, NotFoundException;
    public Lista update(Lista lista) throws BusinessException, NotFoundException;
    public Lista getOneId(int id) throws BusinessException, NotFoundException;


}
