package ar.edu.iua.project.parcial.dao;

import ar.edu.iua.project.parcial.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

//public interface IGenericDAO<T, ID extends Serializable> extends JpaRepository<T, Integer> {
public interface IGenericDAO<T, ID extends Serializable> {
    int save (T Object);
    List<T> getAll();

}
