package ar.edu.iua.project.parcial.web.services;


import ar.edu.iua.project.parcial.business.ITareaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Tarea;
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
   // final static Logger logger = Logger.getLogger("TaskRESTController.class");

    @RequestMapping(value = {"","/"}, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Tarea> add(@RequestBody Tarea tarea){
        try {
            Tarea st = tareaBusiness.add(tarea);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/tareas/" + tarea.getId());
            //logger.debug("Adding the following task: \n" + st);
            return new ResponseEntity<Tarea>(st, responseHeaders, HttpStatus.CREATED);
        } catch (BusinessException be) {
            //logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
            return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = { "/{nombre}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Tarea> getId(@PathVariable("nombre") String nombre) {
        try {
            //logger.debug("Trying to get the following task: " + name);
            return new ResponseEntity<Tarea>(tareaBusiness.getOne(nombre), HttpStatus.OK);
        } catch (BusinessException e) {
            //logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getId()");
            return new ResponseEntity<Tarea>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            //logger.error("Http status:" + HttpStatus.NOT_FOUND + " in getId()");
            return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Tarea>> getAll() {
        try {
            //logger.trace("Getting all tasks");
            return new ResponseEntity<List<Tarea>>(tareaBusiness.getAll(), HttpStatus.OK);
        } catch (BusinessException e) {
            //logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getAll()");
            return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Tarea> update(@PathVariable("id") int id, @RequestBody Tarea tarea) {
        try {
            tarea.setId(id);
            tareaBusiness.update(tarea);
            //logger.debug("Updated the following task: \n" + sprintTask);
            return new ResponseEntity<Tarea>(HttpStatus.OK);
        } catch (BusinessException e) {
            //logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in update()");
            return new ResponseEntity<Tarea>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            //logger.error("Http status:" + HttpStatus.NOT_FOUND + " in update()");
            return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = { "", "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Tarea> delete(@PathVariable("id") int id){

        try {
            Tarea st = new Tarea();
            if (id!=0) {
                st.setId(id);
                tareaBusiness.delete(st);
            }else {
                //logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
                return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
            }
            //logger.trace("Task has been deleted");
            return new ResponseEntity<Tarea>(HttpStatus.OK);
        } catch (BusinessException e) {
            //logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in delete()");
            return new ResponseEntity<Tarea>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            //logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
            return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
        }
    }
}
