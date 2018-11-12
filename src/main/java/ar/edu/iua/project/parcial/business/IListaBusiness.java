package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.ListaSprint;

import java.util.List;

public interface IListaBusiness {

    public ListaSprint add (ListaSprint lista) throws BusinessException, NotFoundException, ListaNotFoundException;
    public List<ListaSprint> getAll() throws BusinessException;
    public ListaSprint getOneId(int id) throws BusinessException, NotFoundException;
    public ListaSprint getOne(String nombre) throws BusinessException, NotFoundException;
    public ListaSprint update(ListaSprint lista) throws BusinessException, NotFoundException;

}
