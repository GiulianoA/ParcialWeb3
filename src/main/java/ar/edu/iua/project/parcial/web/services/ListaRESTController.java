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
    final static Logger log = Logger.getLogger("ListaRESTController.class");


    @RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ListaSprint> add(@RequestBody ListaSprint lista) {
        try {
            ListaSprint l = listaBusiness.add(lista);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/listas/" + lista.getId());
              log.info("Agrega la lista: \n" + l);
            return new ResponseEntity<ListaSprint>(l, responseHeaders, HttpStatus.CREATED);
        } catch (ListaNotFoundException iln) {
             log.error("Error nombre de lista");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_ACCEPTABLE);
        } catch (BusinessException be) {
             log.error("Http status:" + HttpStatus.NOT_FOUND + " en add()");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        } catch (NotFoundException nfe) {
              log.error("Http status:" + HttpStatus.NOT_FOUND + " en add()");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "/{nombre}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ListaSprint> getListbyNombre(@PathVariable("nombre") String nombre) {
        try {
            log.info("Get lista: " + nombre);
            return new ResponseEntity<ListaSprint>(listaBusiness.getOne(nombre), HttpStatus.OK);
        } catch (BusinessException e) {
            log.error("Error lista");
            return new ResponseEntity<ListaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error("Lista no encontrada");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ListaSprint>> getAll() {
        try {
            log.info("Obteniendo Todas las listas");
            return new ResponseEntity<List<ListaSprint>>(listaBusiness.getAll(), HttpStatus.OK);
        } catch (BusinessException e) {
            log.error("Error al obtener todas las listas.");
            return new ResponseEntity<List<ListaSprint>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<ListaSprint> update(@PathVariable("id") int id, @RequestBody ListaSprint sprintLista) {
        try {
        	log.info("id a modificar: " + id);
            sprintLista.setId(id);
            listaBusiness.update(sprintLista);
            return new ResponseEntity<ListaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
        	log.error("Error de id :" +id+ HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<ListaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
        	log.error("id: "+id+" no encontrado " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        }
    }


}
