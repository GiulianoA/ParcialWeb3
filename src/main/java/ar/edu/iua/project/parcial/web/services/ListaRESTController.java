package ar.edu.iua.project.parcial.web.services;

import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.*;
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
        } catch (ListaYaExisteException iln) {
             log.error("Ya existe la lista que deseas agregar :" + lista.getNombre());
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_ACCEPTABLE);
        } catch (InvalidNombreAddLista be) {
             log.error("Method add Lista: El nombre "+ lista.getNombre()+" que deseas agrear no esta permitido");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = { "/{nombre}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ListaSprint> getListbyNombre(@PathVariable("nombre") String nombre) {
        try {
            log.info("Get lista: " + nombre);
            return new ResponseEntity<ListaSprint>(listaBusiness.getUnaListaPorNombre(nombre), HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error("Lista no encontrada Method: getListaByNombre "+ nombre);
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

    @RequestMapping(value = {"/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ListaSprint> delete(@PathVariable("id") int id){
        try {
            ListaSprint sl = new ListaSprint();
            if (id != 0) {
                sl.setId(id);
                listaBusiness.delete(sl);
            }else {
                log.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
                return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
            }
            log.trace("List has been deleted");
            return new ResponseEntity<ListaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
            log.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in delete()");
            return new ResponseEntity<ListaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
            return new ResponseEntity<ListaSprint>(HttpStatus.NOT_FOUND);
        }

    }


}
