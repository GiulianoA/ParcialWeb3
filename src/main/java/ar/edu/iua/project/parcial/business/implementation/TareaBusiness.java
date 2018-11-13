package ar.edu.iua.project.parcial.business.implementation;

import ar.edu.iua.project.parcial.business.ITareaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.InvalidPrioridadException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.ListaSprint;
import ar.edu.iua.project.parcial.model.TareaSprint;
import ar.edu.iua.project.parcial.persistence.dao.FactoryDAO;
import ar.edu.iua.project.parcial.persistence.repository.TareaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaBusiness implements ITareaBusiness {



    @Autowired
    private TareaRepository tareaDAO;

    final static Logger log = Logger.getLogger("TareaBusiness.class");

    @Override
    public TareaSprint update(TareaSprint tarea) throws BusinessException, NotFoundException {
        Optional<TareaSprint> tar = tareaDAO.findById(tarea.getId());
        ListaSprint lis = (ListaSprint) FactoryDAO.getInstance().getListaDAO().getOneId(tarea.getNombreLista().getId());

        if (!tar.isPresent() ) {
            throw new NotFoundException();
        }else if(!tarea.getPrioridad().equalsIgnoreCase("alta") &&
                !tarea.getPrioridad().equalsIgnoreCase("baja") &&
                !tarea.getPrioridad().equalsIgnoreCase("media") ){
            throw new BusinessException();

        }else if(tar.get().getEstimacion() == null && tarea.getEstimacion()==null){
            throw new BusinessException();
        } else if(tar.get().getNombreLista().getNombre().equalsIgnoreCase("done")){  //SI ESTA EN DONE NO SE PEUDE MOVER LOQUITA
            throw new BusinessException();
        }else if(tar.get().getNombreLista().getNombre().equalsIgnoreCase("todo") &&   //si la lista esta en tudo y se quiere ir a otro lado que no sea
                !lis.getNombre().equalsIgnoreCase("in progress")&&              // in progress y waiting se queda.
                !lis.getNombre().equalsIgnoreCase("waiting")){
            throw new BusinessException();
        }else if(tar.get().getNombreLista().getNombre().equalsIgnoreCase("backlog") &&     //si esta en backlog, solo se puede ir a todo
                !lis.getNombre().equalsIgnoreCase("todo")){
            throw new BusinessException();
        }else if(tar.get().getNombreLista().getNombre().equalsIgnoreCase("in progress") &&
                !lis.getNombre().equalsIgnoreCase("todo")&&
                !lis.getNombre().equalsIgnoreCase("waiting")&&
                !lis.getNombre().equalsIgnoreCase("done")){
            throw new BusinessException();
        }else if(tar.get().getNombreLista().getNombre().equalsIgnoreCase("waiting") &&
                !lis.getNombre().equalsIgnoreCase("todo")&&
                !lis.getNombre().equalsIgnoreCase("in progress")&&
                !lis.getNombre().equalsIgnoreCase("done")){
            throw new BusinessException();
        }
        try {
            log.info("la tarea "+ tarea.getNombre() + " ha sido movida a " + tarea.getNombreLista());
            return tareaDAO.save(tarea);
        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }

    @Override
    public List<TareaSprint> search(String nombre) throws BusinessException {
        try {
            return tareaDAO.findByNombreContaining(nombre);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<TareaSprint> order(String estimacion) throws BusinessException {
        try {
            return tareaDAO.findByEstimacionOrderByEstimacionDesc(estimacion);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public TareaSprint getOne(String nombre) throws BusinessException, NotFoundException {
        List<TareaSprint> tareaS = tareaDAO.findAll();

        TareaSprint tarea = null;

        for (TareaSprint tar : tareaS) {
            if (tar.getNombre().equalsIgnoreCase(nombre)) {
                tarea = tar;
            }
        }
        if (tarea==null) {
            throw new NotFoundException();
        }
        try {
            return tarea;
        } catch(Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<TareaSprint> getAll() throws BusinessException {
        try {
            return tareaDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public TareaSprint add(TareaSprint tarea) throws BusinessException {
        try {
            ListaSprint lista = (ListaSprint) FactoryDAO.getInstance().getListaDAO().getOne("backlog");
            
            if(tarea.getPrioridad().equalsIgnoreCase("alta")) { 
            	tarea.setPrioridad("alta");
            }else if(tarea.getPrioridad().equalsIgnoreCase("media")){
            	tarea.setPrioridad("media");
            }else if(tarea.getPrioridad().equalsIgnoreCase("baja")) {
            	tarea.setPrioridad("baja");
            }else {
            	throw new InvalidPrioridadException();
            }
            
            if (lista!=null) {
                tarea.setNombreLista(lista);             	
                	return tareaDAO.save(tarea);
                
            }else {
                throw new ListaNotFoundException();
            }

        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void delete(TareaSprint tarea) throws BusinessException, NotFoundException {
        try {
            if(tarea.getId()!=null) {
                Optional<TareaSprint> tar = tareaDAO.findById(tarea.getId());
                if (tar.isPresent()) {
                    tareaDAO.delete(tarea);
                }else {
                    throw new NotFoundException();
                }
            }
        } catch(NotFoundException ne) {
            throw new NotFoundException(ne);
        } catch(Exception e) {
            throw new BusinessException(e);
        }
    }
}
