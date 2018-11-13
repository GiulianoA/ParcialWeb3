package ar.edu.iua.project.parcial.business.implementation;

import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.ListaSprint;
import ar.edu.iua.project.parcial.persistence.dao.FactoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaBusiness implements IListaBusiness {


    @Override
    public ListaSprint getOneId(int id) throws BusinessException, NotFoundException {
        try {
            ListaSprint l = (ListaSprint) FactoryDAO.getInstance().getListaDAO().getOneId(id);
            return l;
        } catch (NotFoundException nfe) {
            throw new NotFoundException();
        } catch (BusinessException be) {
            throw new BusinessException();
        }
    }

    @Override
    public ListaSprint getOne(String nombre) throws BusinessException, NotFoundException {
        try {
            ListaSprint l = (ListaSprint) FactoryDAO.getInstance().getListaDAO().getOne(nombre);
            if(l == null){
                throw new NotFoundException();
            }else
            return l;
        } catch (NotFoundException nfe) {
            throw new NotFoundException();
        }
    }

    @Override
    public ListaSprint add(ListaSprint lista) throws BusinessException, NotFoundException, ListaNotFoundException {
        String nombre = lista.getNombre();
        if(nombre.equalsIgnoreCase("backlog") || nombre.equalsIgnoreCase("todo")
                || nombre.equalsIgnoreCase("in progress") || nombre.equalsIgnoreCase("waiting")
                || nombre.equalsIgnoreCase("done")) {

            ListaSprint l = (ListaSprint) FactoryDAO.getInstance().getListaDAO().getOne(nombre);

            if(l == null) {
                ListaSprint s = (ListaSprint) FactoryDAO.getInstance().getListaDAO().save(lista);
                return s;
            }else{
                throw new ListaNotFoundException();
            }
        }else{
            throw new ListaNotFoundException();
        }
    }

    @Override
    public List<ListaSprint> getAll() throws BusinessException {
        try{
            return FactoryDAO.getInstance().getListaDAO().getAll();
        }catch (Exception e){
            throw new BusinessException();
        }
    }

    @Override
    public ListaSprint update(ListaSprint lista) throws BusinessException, NotFoundException {

        try {
            ListaSprint lUpd = (ListaSprint) FactoryDAO.getInstance().getListaDAO().getOneId(lista.getId());
            if (lUpd!=null) {
                if(lUpd.getNombre() != lista.getNombreSprint() && lista.getNombre()!=null){
                    throw new BusinessException();   //No se puede cambiar el nombre de la lista
                }else {
                    lUpd.setNombreSprint(lista.getNombreSprint());
                    return (ListaSprint) FactoryDAO.getInstance().getListaDAO().update(lUpd);
                }
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
