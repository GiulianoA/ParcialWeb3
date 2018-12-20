package ar.edu.iua.project.parcial.business.implementation;
import ar.edu.iua.project.parcial.business.IListaBusiness;
import ar.edu.iua.project.parcial.exceptions.*;
import ar.edu.iua.project.parcial.model.ListaSprint;
import ar.edu.iua.project.parcial.model.TareaSprint;
import ar.edu.iua.project.parcial.repository.ListaRepository;
import ar.edu.iua.project.parcial.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class ListaBusiness implements IListaBusiness {

    @Autowired
    private ListaRepository listaDAO;

    @Autowired
    private TareaRepository tareaDAO;

    @Override
    public ListaSprint getUnaListaPorNombre(String nombre) throws  NotFoundException {
 
            ListaSprint lista = listaDAO.getOneListaByNombre(nombre);

            if(lista == null){
                throw new NotFoundException();
            }else
                return lista;
       
    }

    @Override
    public ListaSprint add(ListaSprint listaA) throws InvalidNombreAddLista, ListaYaExisteException {
        String nombre = listaA.getNombre();
        if(nombre.equalsIgnoreCase("backlog") || nombre.equalsIgnoreCase("todo")
                || nombre.equalsIgnoreCase("in progress") || nombre.equalsIgnoreCase("waiting")
                || nombre.equalsIgnoreCase("done")) {

           ListaSprint l = listaDAO.getOneListaByNombre(nombre);

            if(l == null) {
                ListaSprint s = (ListaSprint) listaDAO.save(listaA);
                return s;
            }else{
                throw new ListaYaExisteException("");
            }
        }else{
            throw new InvalidNombreAddLista();
        }
    }

    @Override
    public List<ListaSprint> getAll() throws BusinessException {
        try{
            return listaDAO.findAll();
        }catch (Exception e){
            throw new BusinessException();
        }
    }


        @Override
        public ListaSprint getOneListaById(int id) throws BusinessException, NotFoundException {
        ListaSprint l = null;
        l = listaDAO.getOne(id);
            if (l == null){
                throw new NotFoundException();
            }else
                return l;

        }

    @Override
    public ListaSprint encontrarListaId(int id) throws BusinessException, NotFoundException {
        return listaDAO.findByIdContains(id);
    }

    @Override
    public void delete(ListaSprint lista) throws BusinessException, NotFoundException {
        try {

            Optional<ListaSprint> l = listaDAO.findById(lista.getId());
            if(l.isPresent()){
                for (TareaSprint st : l.get().getTarea()) {
                    System.out.println("se borra la tarea "+st.getNombre());
                    tareaDAO.delete(st);
                }

                System.out.println("Estoy por borrar la LISTA");
                listaDAO.delete(l.get());
            }else{
                throw new NotFoundException();
            }
        } catch(NotFoundException nfe) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }


}
