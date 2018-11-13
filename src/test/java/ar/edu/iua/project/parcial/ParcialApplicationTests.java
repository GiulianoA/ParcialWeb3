package ar.edu.iua.project.parcial;

import ar.edu.iua.project.parcial.business.implementation.ListaBusiness;
import ar.edu.iua.project.parcial.business.implementation.TareaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.ListaSprint;
import ar.edu.iua.project.parcial.model.TareaSprint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParcialApplicationTests {

	@Test
	public void getAllListas() throws BusinessException {
		ListaBusiness lb = new ListaBusiness();
		List<ListaSprint> ls = (List<ListaSprint>) lb.getAll();

		assertEquals("backlog", ls.get(0).getNombre());
	}

	@Test
	public void getListaByNombre() throws BusinessException, NotFoundException {
		ListaBusiness lb = new ListaBusiness();
		String nombre = "backlog";
		ListaSprint ls = lb.getOne(nombre);

		assertEquals("backlog" , ls.getNombre());

	}

	@Test
	public void getListaById() throws BusinessException, NotFoundException {
		ListaBusiness lb = new ListaBusiness();
		int id = 1;
		ListaSprint ls = lb.getOneId(id);

		assertEquals(1,ls.getId(),ls.getId());
	}

	/*@Test
	public void getAllTareas() throws BusinessException {
		TareaBusiness tb = new TareaBusiness();
		List<TareaSprint> lts = (List<TareaSprint>) tb.getAll();

		assertEquals("Tarea2", lts.get(1).getNombre());
	}*/

	/*@Test
	public void getOneTarea() throws BusinessException, NotFoundException {
		TareaBusiness tb = new TareaBusiness();
		String nombre = "Tarea2";
		TareaSprint lts =  tb.getOne(nombre);

		assertEquals("Tarea2", lts.getNombre());
	}*/


}
