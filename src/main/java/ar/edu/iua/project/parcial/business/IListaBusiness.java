package ar.edu.iua.project.parcial.business;

import ar.edu.iua.project.parcial.exceptions.*;
import ar.edu.iua.project.parcial.model.ListaSprint;

import java.util.List;

public interface IListaBusiness {

    public ListaSprint add (ListaSprint lista) throws InvalidNombreAddLista, ListaYaExisteException;
    public List<ListaSprint> getAll() throws BusinessException;
    public ListaSprint getUnaListaPorNombre(String nombre) throws NotFoundException;
    public void delete(ListaSprint lista) throws BusinessException, NotFoundException;
    ////
    public ListaSprint getOneListaById(int id) throws BusinessException, NotFoundException;
    public ListaSprint encontrarListaId(int id) throws BusinessException, NotFoundException;

}
