package ar.edu.iua.project.parcial.web.services;


import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constantes.URL_LISTA)
public class ListaRESTController {

    @Autowired
    private IListaBusiness listaBusiness;


    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Lista>> lista(
            @RequestParam(required = false, value = "q", defaultValue = "*") String q) {

        try {
            if (q.equals("*") || q.trim().length() == 0) {
                return new ResponseEntity<List<Lista>>(listaBusiness.getAll(), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Lista>>(listaBusiness.search(q), HttpStatus.OK);
            }
        } catch (BusinessException e) {
            return new ResponseEntity<List<Lista>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = { "/{id}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Lista> uno(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<Lista>(listaBusiness.getOne(id), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<Lista>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
        }

    }



    @RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> add(@RequestBody Lista lista) {
        try {
            listaBusiness.add(lista);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/listas/" + lista.getId());
            return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
        } catch (BusinessException e) {
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = { "", "/" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> update(@RequestBody Lista lista) {
        try {
            listaBusiness.update(lista);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Lista> delete(@PathVariable("id") int id) {
        try {
            Lista l = new Lista();
            l.setId(id);
            listaBusiness.delete(l);
            return new ResponseEntity<Lista>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<Lista>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
        }

    }



}
