package ar.edu.iua.project.parcial.persistence.dao;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T, ID extends Serializable> {
    Object save (T Object);
    List<T> getAll();
    public T update(T object);
    public T getOne(String name) throws BusinessException, NotFoundException;
    public T getOneId(int id) throws BusinessException, NotFoundException;
    public void delete(T object) throws NotFoundException;
}
