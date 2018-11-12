package ar.edu.iua.project.parcial.web.services;

import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.ListaNotFoundException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.ListaSprint;
import org.apache.log4j.*;
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
    final static Logger logger = Logger.getLogger("ListaRESTController.class");


    @RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ListaSprint> add(@RequestBody ListaSprint lista) {
        try {
            ListaSprint l = listaBusiness.add(lista);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/listas/" + lista.getId());
              logger.debug("Agrega la lista: \n" + l);
            return new ResponseEntity<ListaSprint>(l, responseHeaders, HttpStatus.CREATED);
        } catch (ListaNotFoundException iln) {
             logger.error("Error nombre de lista");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_ACCEPTABLE);
        } catch (BusinessException be) {
             logger.error("Http status:" + HttpStatus.NOT_FOUND + " en add()");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        } catch (NotFoundException nfe) {
              logger.error("Http status:" + HttpStatus.NOT_FOUND + " en add()");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "/{name}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ListaSprint> getListbyName(@PathVariable("name") String name) {
        try {
            logger.debug("Get lista: " + name);
            return new ResponseEntity<ListaSprint>(listaBusiness.getOne(name), HttpStatus.OK);
        } catch (BusinessException e) {
            logger.error("Error id");
            return new ResponseEntity<ListaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            logger.error("Id no encontrado");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ListaSprint>> getAll() {
        try {
            logger.info("Obteniendo Todas las tareas");
            return new ResponseEntity<List<ListaSprint>>(listaBusiness.getAll(), HttpStatus.OK);
        } catch (BusinessException e) {
            logger.error("Error al obtener todas las tareas.");
            return new ResponseEntity<List<ListaSprint>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<ListaSprint> update(@PathVariable("id") int id, @RequestBody ListaSprint sprintList) {
        try {
            sprintList.setId(id);
            listaBusiness.update(sprintList);
            return new ResponseEntity<ListaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<ListaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        }
    }


}
