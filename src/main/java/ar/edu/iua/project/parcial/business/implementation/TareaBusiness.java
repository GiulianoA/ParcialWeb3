package ar.edu.iua.project.parcial.business.implementation;

import ar.edu.iua.project.parcial.business.ITareaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;
import ar.edu.iua.project.parcial.model.Tarea;
import ar.edu.iua.project.parcial.persistence.dao.FactoryDAO;
import ar.edu.iua.project.parcial.persistence.dao.ListaDaoImplementacion;
import ar.edu.iua.project.parcial.persistence.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaBusiness implements ITareaBusiness {

    private String initialList = "backlog";
    //final static Logger logger = Logger.getLogger("TaskBusiness.class");

    @Autowired
    private TareaRepository tareaDAO;

    @Autowired
    private ListaDaoImplementacion listaDaoImplementacion;

    @Override
    public List<Tarea> getAll() throws BusinessException {
        try {
            return tareaDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Tarea add(Tarea tarea) throws BusinessException {
        try {
            Lista sl = (Lista) FactoryDAO.getInstance().getListaDAO().getOne(initialList);
            if (sl!=null) {
                tarea.setLista(sl);
                //logger.debug("Task with name: "+ tarea.getName() + " has been created" + " in "+ tarea.getListName());
                return tareaDAO.save(tarea);
            }else {
               // logger.error("Task with name: "+ sprintTask.getName() + " couldn't been created, because the List is null");
                throw new ListaNotFoundException();
            }

        } catch (Exception e) {
            //logger.trace("Business Exception in method 'add' :( ");
            throw new BusinessException(e);
        }
    }

    @Override
    public Tarea getOne(String nombre) throws BusinessException, NotFoundException {
        List<Tarea> stl = tareaDAO.findAll();
        Tarea tarea = null;

        for (Tarea st : stl) {
            if (st.getNombre().equalsIgnoreCase(nombre)) {
                tarea = st;
            }
        }
        if (tarea==null) {
            //logger.error("Task is null, so we can't found the Task required");
            throw new NotFoundException();
        }
        try {
            //logger.info("Task found it! "+ sprintTask.getName());
            return tarea;
        } catch(Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Tarea update(Tarea tarea) throws BusinessException, NotFoundException {
        Optional<Tarea> st = tareaDAO.findById(tarea.getId());
        Lista lis = listaDaoImplementacion.getOneId(tarea.getLista().getId());
        if (!st.isPresent() ) {
            //logger.error("Task with ID: "+ sprintTask.getId()+ " and name: " + sprintTask.getName() + " dindn't found it");
            throw new NotFoundException();
        }else if(st.get().getLista().getNombre().equalsIgnoreCase("done")){  //SI ESTA EN DONE NO SE PEUDE MOVER LOQUITA
            throw new BusinessException();
        }else if(st.get().getLista().getNombre().equalsIgnoreCase("todo") &&   //si la lista esta en tudo y se quiere ir a otro lado que no sea
                !lis.getNombre().equalsIgnoreCase("in progress")&&              // in progress y waiting se queda.
                !lis.getNombre().equalsIgnoreCase("waiting")){
            throw new BusinessException();
        }else if(st.get().getLista().getNombre().equalsIgnoreCase("backlog") &&     //si esta en backlog, solo se puede ir a todo
                !lis.getNombre().equalsIgnoreCase("todo")){
            throw new BusinessException();
        }else if(st.get().getLista().getNombre().equalsIgnoreCase("in progress") &&
                !lis.getNombre().equalsIgnoreCase("todo")&&
                !lis.getNombre().equalsIgnoreCase("waiting")&&
                !lis.getNombre().equalsIgnoreCase("done")){
            throw new BusinessException();
        }else if(st.get().getLista().getNombre().equalsIgnoreCase("waiting") &&
                !lis.getNombre().equalsIgnoreCase("todo")&&
                !lis.getNombre().equalsIgnoreCase("in progress")&&
                !lis.getNombre().equalsIgnoreCase("done")){
            throw new BusinessException();
        }
            try {
                //logger.debug("Task with name: "+ sprintTask.getName() + " has been updated");
                return tareaDAO.save(tarea);
            } catch (Exception e) {
                throw new BusinessException(e);
            }

    }

    @Override
    public void delete(Tarea tarea) throws BusinessException, NotFoundException {
        try {
            if(tarea.getId()!=null) {
                Optional<Tarea> st = tareaDAO.findById(tarea.getId());
                if (st.isPresent()) {
                    tareaDAO.delete(tarea);
                    //logger.debug("Task with ID: "+ sprintTask.getId() + " has been deleted");
                }else {
                    //logger.error("Task with ID: "+ sprintTask.getId() + " hasn't been found it");
                    throw new NotFoundException();
                }
            }else {
                tarea = getOne(tarea.getNombre());
                tareaDAO.delete(tarea);
                //logger.debug("Task with name: "+ sprintTask.getName() + " has been deleted");
            }
        } catch(NotFoundException ne) {
            //logger.error("Task with ID: "+ sprintTask.getId()+ " and name: " + sprintTask.getName() + " can't be deleted");
            throw new NotFoundException(ne);
        } catch(Exception e) {
            throw new BusinessException(e);
        }
    }
}
