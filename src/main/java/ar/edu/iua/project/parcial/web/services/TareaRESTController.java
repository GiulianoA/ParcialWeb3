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
    final static Logger logger = Logger.getLogger("TareaRESTController.class");

    @RequestMapping(value = {"","/"}, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<TareaSprint> add(@RequestBody TareaSprint tarea){
        try {
            TareaSprint t = tareaBusiness.add(tarea);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/tareas/" + tarea.getId());
            logger.debug("Agrega la tarea: \n" + t);
            return new ResponseEntity<TareaSprint>(t, responseHeaders, HttpStatus.CREATED);
        } catch (BusinessException be) {
            logger.error("Http status:" + HttpStatus.NOT_FOUND + " en add()");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = { "/{nombre}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TareaSprint> getId(@PathVariable("nombre") String nombre) {
        try {
            logger.debug("Get tarea: " + nombre);
            return new ResponseEntity<TareaSprint>(tareaBusiness.getOne(nombre), HttpStatus.OK);
        } catch (BusinessException e) {
            logger.error("Error nombre");
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            logger.error("Http status:" + HttpStatus.NOT_FOUND + " en getId()");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<TareaSprint> update(@PathVariable("id") int id, @RequestBody TareaSprint tarea) {
        try {
            tarea.setId(id);
            tareaBusiness.update(tarea);
            logger.debug("Actualiza tarea: \n" + tarea);
            return new ResponseEntity<TareaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
            logger.error("Error id");
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            logger.error("Http status:" + HttpStatus.NOT_FOUND + " en update()");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TareaSprint>> listadoTareaPorNombre(
            @RequestParam(required = false, value = "q", defaultValue = "*") String q ){
        try {
            if (q.equals("*") || q.trim().length() == 0) {
                return new ResponseEntity<List<TareaSprint>>(tareaBusiness.getAll(), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<TareaSprint>>(tareaBusiness.search(q), HttpStatus.OK);
            }
        } catch (BusinessException e) {
            return new ResponseEntity<List<TareaSprint>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = { "", "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<TareaSprint> delete(@PathVariable("id") int id){

        try {
            TareaSprint st = new TareaSprint();
            if (id!=0) {
                st.setId(id);
                tareaBusiness.delete(st);
            }else {
                return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<TareaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }
    }
}
