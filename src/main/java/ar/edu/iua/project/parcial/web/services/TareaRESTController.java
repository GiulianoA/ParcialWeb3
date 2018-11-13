package ar.edu.iua.project.parcial.web.services;


import ar.edu.iua.project.parcial.business.ITareaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.TareaSprint;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.URL_TAREA)
public class TareaRESTController {

    @Autowired
    private ITareaBusiness tareaBusiness;
    final static Logger log = Logger.getLogger("TareaRESTController.class");

    @RequestMapping(value = {"","/"}, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<TareaSprint> add(@RequestBody TareaSprint tarea){
        try {
            TareaSprint t = tareaBusiness.add(tarea);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/tareas/" + tarea.getId());
            log.debug("Agrega la tarea: \n" + t);
            return new ResponseEntity<TareaSprint>(t, responseHeaders, HttpStatus.CREATED);
        } catch (BusinessException be) {
            log.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " en add()");
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = { "/{nombre}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TareaSprint> getTarea(@PathVariable("nombre") String nombre) {
        try {
            log.debug("Get tarea: " + nombre);
            return new ResponseEntity<TareaSprint>(tareaBusiness.getOne(nombre), HttpStatus.OK);
        } catch (BusinessException e) {
            log.error("Error nombre " +nombre);
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error("Http status:" + HttpStatus.NOT_FOUND + " en getId()");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<TareaSprint> update(@PathVariable("id") int id, @RequestBody TareaSprint tarea) {
        try {
        	log.info("id a modificar: " + id);
            tarea.setId(id);
            tareaBusiness.update(tarea);
            log.debug("Actualiza tarea: \n" + tarea);
            return new ResponseEntity<TareaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
        	log.error("Error de id :" +id+ HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
        	log.error("id: "+id+" no encontrado "+ HttpStatus.NOT_FOUND);
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TareaSprint>> listadoTareaPorNombre(
            @RequestParam(required = false, value = "buscar", defaultValue = "*") String buscar,
            @RequestParam(required = false, value = "o", defaultValue = "*") String o){
        try {
            if (buscar.equals("*") || buscar.trim().length() == 0) {
                log.info("Parametro default, obtengo toda la lista de tareas");
            	return new ResponseEntity<List<TareaSprint>>(tareaBusiness.order(), HttpStatus.OK);
            } else if(!buscar.equals("*") && buscar.trim().length() > 0){
                log.info("Obtengo de la lista lo que coincida con " + buscar );
            	return new ResponseEntity<List<TareaSprint>>(tareaBusiness.search(buscar), HttpStatus.OK);
            }else{
                System.out.println("entre");
                return new ResponseEntity<List<TareaSprint>>(tareaBusiness.order(), HttpStatus.OK);
            }

            
        } catch (BusinessException e) {
            return new ResponseEntity<List<TareaSprint>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = { "", "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<TareaSprint> delete(@PathVariable("id") int id){

        try {
        	log.info("id a eliminar: " + id);
            TareaSprint st = new TareaSprint();
            if (id!=0) {
                st.setId(id);
                tareaBusiness.delete(st);
            }else {
                return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<TareaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
        	log.error("Error de id :" +id+ HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
        	log.error("id: "+id+" no encontrado "+ HttpStatus.NOT_FOUND);
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }
    }
}
