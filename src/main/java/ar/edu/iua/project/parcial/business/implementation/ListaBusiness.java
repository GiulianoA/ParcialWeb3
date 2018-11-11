package ar.edu.iua.project.parcial.business.implementation;

import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;
import ar.edu.iua.project.parcial.persistence.dao.FactoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaBusiness implements IListaBusiness {

    @Autowired
    private ListaBusiness listaBusiness;

    @Override
    public Lista getOneId(int id) throws BusinessException, NotFoundException {
        try {
            Lista l = (Lista) FactoryDAO.getInstance().getListaDAO().getOneId(id);
            return l;
        } catch (NotFoundException nfe) {
            throw new NotFoundException();
        } catch (BusinessException be) {
            throw new BusinessException();
        }
    }

    @Override
    public Lista getOne(String nombre) throws BusinessException, NotFoundException {
        try {
            Lista l = (Lista) FactoryDAO.getInstance().getListaDAO().getOne(nombre);
            return l;
        } catch (NotFoundException nfe) {
            throw new NotFoundException();
        }
    }

    @Override
    public Lista add(Lista lista) throws BusinessException, NotFoundException, ListaNotFoundException {
        String nombre = lista.getNombre();
        //(backlog, TO-DO, in progress, waiting, done)
        if(nombre.equalsIgnoreCase("backlog") || nombre.equalsIgnoreCase("todo")
                || nombre.equalsIgnoreCase("in progress") || nombre.equalsIgnoreCase("waiting")
                || nombre.equalsIgnoreCase("done")) {

            Lista l = (Lista) FactoryDAO.getInstance().getListaDAO().getOne(nombre);

            if(l == null) {
                Lista s = (Lista) FactoryDAO.getInstance().getListaDAO().save(lista);
                return s;
            }else{
                throw new ListaNotFoundException();
            }
        }else{
            throw new ListaNotFoundException();
        }
    }

    @Override
    public List<Lista> getAll() throws BusinessException {
        try{
            return FactoryDAO.getInstance().getListaDAO().getAll();
        }catch (Exception e){
            throw new BusinessException();
        }
    }

    @Override
    public Lista update(Lista lista) throws BusinessException, NotFoundException {
        try {
            if (getOneId(lista.getId())!=null) {
                return (Lista) FactoryDAO.getInstance().getListaDAO().update(lista);
            }else{
                throw new NotFoundException();
            }
        }catch (NotFoundException nfe) {
            throw new NotFoundException(nfe);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
