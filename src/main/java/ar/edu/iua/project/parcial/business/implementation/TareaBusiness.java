package ar.edu.iua.project.parcial.business.implementation;

import ar.edu.iua.project.parcial.business.ITareaBusiness;
import ar.edu.iua.project.parcial.exceptions.*;
import ar.edu.iua.project.parcial.model.ListaSprint;
import ar.edu.iua.project.parcial.model.TareaSprint;
import ar.edu.iua.project.parcial.repository.ListaRepository;
import ar.edu.iua.project.parcial.repository.TareaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class TareaBusiness implements ITareaBusiness {

    TareaSprint t;

    
    @Autowired
    private TareaRepository tareaDAO;

    @Autowired
    private ListaRepository listaDAO;

    ListaBusiness lb = new ListaBusiness();

    final static Logger log = Logger.getLogger("TareaBusiness.class");

    @Override
    public TareaSprint update(TareaSprint tarea, boolean isAdmin) throws BusinessException, NotFoundException, ListaNulaException, ValorInvalidoEstimationException, ListaDestinoInvalidaException {

        Optional<TareaSprint> tareaOrigen = null;
        String listaDestino = null;


        Optional<TareaSprint> tar = tareaDAO.findById(tarea.getId());
        if (!tar.isPresent()){
            throw new NotFoundException();
        }


        try {
            listaDestino = listaDAO.getOne(tarea.getNombreLista().getId()).getNombre().toLowerCase();
        } catch (EntityNotFoundException e){
            throw new NotFoundException();
        } catch (NullPointerException e){
        throw new ListaNulaException();
    }

        try {
            tareaOrigen = findById(tarea.getId());
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        } catch (BusinessException e) {
            throw new BusinessException(e);
        }


       // if (tareaOrigen.get().getEstimacion() <= 0){
       //     throw new ValorInvalidoEstimationException();
       // }

        String listaOrigen = tareaOrigen.get().getNombreLista().getNombre().toLowerCase();

        HashMap<String, String[]> origenDestino = new HashMap<String, String[]>();
        System.out.println("isAdmin: " + isAdmin);
        if (isAdmin) {
            origenDestino.put("backlog", new String[]{"todo"});
        } else {
            origenDestino.put("backlog", new String[]{});
        }
        origenDestino.put("todo", new String[]{"in progress", "waiting", "done"});
        origenDestino.put("in progress", new String[]{"waiting", "todo", "done"});
        origenDestino.put("waiting", new String[]{"in progress", "todo", "done"});
        origenDestino.put("done", new String[]{});

        if (!Arrays.asList(origenDestino.get(listaOrigen)).contains(listaDestino)){
            throw new ListaDestinoInvalidaException();
        }

        return tareaDAO.save(tarea);

    }

    public Optional<TareaSprint> findById(Integer id) throws BusinessException, NotFoundException {
        try {
            return tareaDAO.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<TareaSprint> getTareasDeUnaLista() {


        return tareaDAO.findAllByOrderByFechacreacion();
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
    public List<TareaSprint> order(String o) throws BusinessException {
        try {
            if(o.equals("fecha")){
            return tareaDAO.findAllByOrderByFechacreacionAsc();
            }else if(o.equals("prioridad")){
                return tareaDAO.findAllByOrderByPrioridadAsc();
            }else{
                throw new BusinessException();
            }


        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public TareaSprint getUnaTareaPorNombre(String nombre) throws BusinessException, NotFoundException {

        TareaSprint tarea = tareaDAO.getOneTareaByNombre(nombre);

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
    public List<TareaSprint> getAllTareas() throws BusinessException {
        try {
            return tareaDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public TareaSprint add(TareaSprint tarea) throws FechaCreacionNulaException,ListaNotFoundException,InvalidPrioridadException,TareaExisteException {


            TareaSprint tareaEnbdd = tareaDAO.getOneTareaByNombre(tarea.getNombre());
            if(tareaEnbdd!=null){
                System.out.println("ya hay una tearea");
                throw new TareaExisteException();
            }else {


                ListaSprint lista = listaDAO.getOneListaByNombre("backlog");

                if (tarea.getPrioridad().equalsIgnoreCase("alta")) {
                    tarea.setPrioridad("alta");
                } else if (tarea.getPrioridad().equalsIgnoreCase("media")) {
                    tarea.setPrioridad("media");
                } else if (tarea.getPrioridad().equalsIgnoreCase("baja")) {
                    tarea.setPrioridad("baja");
                }else {
                    throw new InvalidPrioridadException();
                }

                if(tarea.getFechacreacion()==null){
                    throw new FechaCreacionNulaException();
                }


                if (lista != null) {
                    tarea.setNombreLista(lista);         //quiere decir que la lista backlog esta creada
                    return tareaDAO.save(tarea);

                } else {
                    throw new ListaNotFoundException();
                }
            }
    }

    @Override
    public List<TareaSprint> getEstimacion(Integer estimacion){
        /*System.out.println("aca se rompe");
        int id;
        List<ListaSprint> listas = listaDAO.findAll();

        for(ListaSprint l : listas){
            if(l.getNombre()==nombreLista){
                id=l.getId();
                break;
            }
        }



        //List<TareaSprint> tareasOrdenadas = tareaDAO.findAllByNombreListaOrderByFechacreacion(nombreLista);
        return tareaDAO.findAllByNombreListaOrderByFechacreacion(nombreLista);*/
        return tareaDAO.getAllByEstimacion(estimacion);
    }

    @Override
    public List<TareaSprint> getByListaYFechaCreacion(String nombreLista){	
        return tareaDAO.getAllByNombreListaOrderByFechacreacion(listaDAO.getOneListaByNombre(nombreLista));
    }

    @Override
    public List<TareaSprint> getByListaYOrdenacion( String nombreLista, String tipoOrden) throws NotFoundException, InvalidSortException{	

    	
    	if(tipoOrden.equalsIgnoreCase("fecha")){
    		return tareaDAO.getAllByNombreListaOrderByFechacreacion(listaDAO.getOneListaByNombre(nombreLista));
        }else if(tipoOrden.equalsIgnoreCase("prioridad")){
        	return tareaDAO.getAllByNombreListaOrderByPrioridad(listaDAO.getOneListaByNombre(nombreLista));
        }else if (tipoOrden.equals("*") && !nombreLista.equals("*")){
        	if(tareaDAO.getAllByNombreLista(listaDAO.getOneListaByNombre(nombreLista)).size()==0) {
        		//throw new NotFoundException(); //NO HAY TAREAS EN ESA LISTA
        		List<TareaSprint> l = new ArrayList<TareaSprint>();
        		return l;
        	}else {
        	return tareaDAO.getAllByNombreLista(listaDAO.getOneListaByNombre(nombreLista));
        	}
        }else if(tipoOrden.equals("*") && nombreLista.equals("*")){
        	return tareaDAO.findAll();
        }else {
        	throw new InvalidSortException();
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
