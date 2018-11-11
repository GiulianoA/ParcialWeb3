package ar.edu.iua.project.parcial.web.services;

import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.URL_LISTA)
public class ListaRESTController {

    @Autowired
    private IListaBusiness listaBusiness;
   // final static Logger logger = Logger.getLogger("TaskRESTController.class");


    @RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Lista> add(@RequestBody Lista lista) {
        try {
            Lista sl = listaBusiness.add(lista);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/listas/" + lista.getId());
            //  logger.debug("Adding the following list: \n" + sl);
            return new ResponseEntity<Lista>(sl, responseHeaders, HttpStatus.CREATED);
        } catch (ListaNotFoundException iln) {
            // logger.error("Http status:" + HttpStatus.NOT_ACCEPTABLE + " in add(), the list name is invalid");
            return new ResponseEntity<Lista>(HttpStatus.NOT_ACCEPTABLE);
        } catch (BusinessException be) {
            // logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
            return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
        } catch (NotFoundException nfe) {
            //  logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
            return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "/{name}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Lista> getId(@PathVariable("name") String name) {
        try {
            // logger.debug("Trying to get the following list: " + name);
            return new ResponseEntity<Lista>(listaBusiness.getOne(name), HttpStatus.OK);
        } catch (BusinessException e) {
            // logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getId()");
            return new ResponseEntity<Lista>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            //  logger.error("Http status:" + HttpStatus.NOT_FOUND + " in getId()");
            return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Lista>> getAll() {
        try {
           // logger.trace("Getting all task lists");
            return new ResponseEntity<List<Lista>>(listaBusiness.getAll(), HttpStatus.OK);
        } catch (BusinessException e) {
            //logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getAll()");
            return new ResponseEntity<List<Lista>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Lista> update(@PathVariable("id") int id, @RequestBody Lista sprintList) {
        try {
            sprintList.setId(id);
            listaBusiness.update(sprintList);
            return new ResponseEntity<Lista>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<Lista>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
        }
    }


}
