package ar.edu.iua.project.parcial.web.services;


import ar.edu.iua.project.parcial.business.ITareaBusiness;
import ar.edu.iua.project.parcial.exceptions.*;
import ar.edu.iua.project.parcial.model.TareaSprint;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
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
            return new ResponseEntity<TareaSprint>(t, responseHeaders, HttpStatus.CREATED);
        } catch (ListaNotFoundException be) {
            log.error("La lista backlog no esta creada. Para agregar una tarea necesita crearla.");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }catch (InvalidPrioridadException be) {
            log.error("La prioridad debe ser alta, media o baja");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_ACCEPTABLE);
        }catch (TareaExisteException ex){
            log.error("Ya existe una tarea con el mismo nombre");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_ACCEPTABLE);
        }catch (FechaCreacionNulaException exe){
            log.error("La Fecha de creacion no puede ser nula");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_ACCEPTABLE);
        }catch (ValorInvalidoEstimationException v){
            log.error("La estimacion no puede ser nula");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @RequestMapping(value = { "/{nombre}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TareaSprint> getTareaByNombre(@PathVariable("nombre") String nombre) {
        try {
            log.debug("Get tarea: " + nombre);
            return new ResponseEntity<TareaSprint>(tareaBusiness.getUnaTareaPorNombre(nombre), HttpStatus.OK);
        } catch (BusinessException e) {
            log.error("Error nombre " +nombre);
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error("NO SE ENCONTRO UNA TAREA CON ESE NOMBRE");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TareaSprint>> listadoTarea(
            @RequestParam(required = false, value = "buscar", defaultValue = "*") String buscar,
            @RequestParam(required = false, value = "ordenar", defaultValue = "*") String orden) {
        try {
			return new ResponseEntity<List<TareaSprint>>(tareaBusiness.getByListaYOrdenacion(buscar, orden), HttpStatus.OK);
		} catch (InvalidSortException e) {
            log.error("TareasRESTController.listadoTareas() - Incorrecta la busqueda ");
            return new ResponseEntity<List<TareaSprint>>(HttpStatus.NOT_ACCEPTABLE);
		}catch (NotFoundException e) {
            log.error("TareasRESTController.listadoTareas() - NO SE ENCONTRO LA LISTA/ NO HAY TAREAS EN ESA LISTA ");
            return new ResponseEntity<List<TareaSprint>>(HttpStatus.NOT_FOUND);
		}

    }


    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<TareaSprint> update(@PathVariable("id") Integer id, @RequestBody TareaSprint tarea, HttpServletRequest request) {
        try {
            tarea.setId(id);
            TareaSprint t = tareaBusiness.update(tarea, request.isUserInRole("ROLE_LIDER"));
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", "/tareas" + t.getId());
            return new ResponseEntity<TareaSprint>(responseHeaders, HttpStatus.OK);

        } catch (ListaNulaException e) {
            log.error("TareasRESTController.update() - ListaNulaException ");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        } catch (NotFoundException e) {
            log.error("TareasRESTController.update() - NotFoundException ");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        } catch (ValorInvalidoEstimationException e) {
            log.error("TareasRESTController.update() - IValorInvalidoEstimationException ");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_ACCEPTABLE);
        } catch (ListaDestinoInvalidaException e) {
            log.error("TareasRESTController.update() - ListaDestinoInvalidaException");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_ACCEPTABLE);
        } catch (BusinessException e) {
            log.error("TareasRESTController.update() - BusinessException ");
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = {"/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
    @PreAuthorize("hasRole('ROLE_LIDER')")
    public ResponseEntity<TareaSprint> delete(@PathVariable("id") int id){

        try {
        	log.info("id a eliminar: " + id);
            TareaSprint st = new TareaSprint();
            if (id!=0) {
                st.setId(id);
                tareaBusiness.delete(st);
            }
            return new ResponseEntity<TareaSprint>(HttpStatus.OK);
        } catch (BusinessException e) {
        	log.error("Error de id :" +id+ HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<TareaSprint>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
        	log.error("id de tarea: "+id+" no encontrado ");
            return new ResponseEntity<TareaSprint>(HttpStatus.NOT_FOUND);
        }
    }
}
