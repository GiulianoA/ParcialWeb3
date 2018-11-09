package ar.edu.iua.project.parcial.business.implementation;

import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;
import ar.edu.iua.project.parcial.model.persistence.ListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ListaBusiness implements IListaBusiness {

    @Autowired
    private ListaRepository listaDAO;

    @Override
    public List<Lista> getAll() throws BusinessException {
        try{
            return listaDAO.findAll();
        }catch (Exception e){
            throw new BusinessException();
        }
    }

    @Override
    public void delete(Lista lista) throws BusinessException, NotFoundException {
        Optional<Lista> lis = null;

        lis = listaDAO.findById(lista.getId());
        if(!lis.isPresent()){
            throw new NotFoundException();
        }

        try{
            listaDAO.delete(lista);
        }catch (Exception e){
            throw new BusinessException();
        }
    }

    @Override
    public Lista add(Lista lista) throws BusinessException {
        try{
            return listaDAO.save(lista);
        }catch (Exception e){
            throw new BusinessException();
        }

    }

    @Override
    public Lista getOne(int id) throws BusinessException, NotFoundException {
        Optional<Lista> lis = null;
        try{
            lis = listaDAO.findById(id);
        }catch (Exception e){
            throw new BusinessException();
        }
        if(!lis.isPresent()){
            throw new NotFoundException();
        }

        return lis.get();
    }

    @Override
    public Lista update(Lista lista) throws BusinessException, NotFoundException {
        Optional<Lista> lis = null;
        lis = listaDAO.findById(lista.getId());
        if (!lis.isPresent())
            throw new NotFoundException();
        try {

            return listaDAO.save(lista);

        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<Lista> search(String part) throws BusinessException {
        try {
            return listaDAO.findByNombreContaining(part);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

}
