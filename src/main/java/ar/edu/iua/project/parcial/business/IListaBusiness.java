package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;

import java.util.List;

public interface IListaBusiness {

    public Lista add (Lista lista) throws BusinessException, NotFoundException, ListaNotFoundException;
    public List<Lista> getAll() throws BusinessException;
    public Lista getOneId(int id) throws BusinessException, NotFoundException;
    public Lista getOne(String nombre) throws BusinessException, NotFoundException;
    public Lista update(Lista lista) throws BusinessException, NotFoundException;

}
