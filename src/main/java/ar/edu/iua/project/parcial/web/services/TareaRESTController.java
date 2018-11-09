package ar.edu.iua.project.parcial.web.services;

import ar.edu.iua.project.parcial.dao.FactoryDAO;
import ar.edu.iua.project.parcial.dao.TareaImplementationDAO;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.model.Tarea;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.URL_TAREA)
public class TareaRESTController {

    //@Autowired
    //private ITareaBusiness tareaBusiness;
    private FactoryDAO factoryDAO;

    @RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> add(@RequestBody Tarea tarea) {
        int idTarea = FactoryDAO.getTareaDAO().save(tarea);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("location", "/tareas/" + idTarea);
        return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Tarea>> tarea(
            @RequestParam(required = false, value = "q", defaultValue = "*") String q) {
        //try {
            if (q.equals("*") || q.trim().length() == 0) {
                //return new ResponseEntity<List<Tarea>>(FactoryDAO.getTareaDAO().getAll(), HttpStatus.OK);
                return new ResponseEntity<List<Tarea>>(factoryDAO.getInstance().getTareaDAO().getAll(), HttpStatus.OK);
            }else
                return null;
        //}catch (Exception e){
          //  return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
        //}
    }

}
