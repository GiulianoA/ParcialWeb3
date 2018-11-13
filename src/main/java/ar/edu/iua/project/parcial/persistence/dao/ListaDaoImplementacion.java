package ar.edu.iua.project.parcial.persistence.dao;

import ar.edu.iua.project.parcial.business.ITareaBusiness;
import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.ListaSprint;
import ar.edu.iua.project.parcial.model.TareaSprint;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Component
public class ListaDaoImplementacion implements IGenericDAO<ListaSprint, Serializable> {

    private static ListaDaoImplementacion instance;

    @Autowired
    ITareaBusiness tareaBusiness;

    @Autowired
    private EntityManagerFactory emf;

    public ListaDaoImplementacion(){}


    @Bean
    public static ListaDaoImplementacion getInstance() {
        if (instance == null) {
            instance = new ListaDaoImplementacion();
        }
        return instance;
    }

    @Override
    public ListaSprint save(ListaSprint lista) {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(lista);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null)
                tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return lista;
    }

    @Override
    public ListaSprint getOneId(int id) throws BusinessException, NotFoundException {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            ListaSprint lista = session.get(ListaSprint.class, id);
            //lista.setTarea(session.get(Tarea.class,lista.getId()));

            tx.commit();

            return lista;

        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    @Override
    public ListaSprint getOne(String nombre) throws BusinessException, NotFoundException {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;
        TareaSprint tarea;
        try {
            tx = session.beginTransaction();

            session.flush();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<ListaSprint> query = builder.createQuery(ListaSprint.class);
            Root<ListaSprint> from = query.from(ListaSprint.class);

            query.select(from).where(builder.equal(from.get("nombre"), nombre));

            List<ListaSprint> listas = session.createQuery(query).getResultList();

            tx.commit();
            if (listas.isEmpty()) {
                return null;
            }
            return listas.get(0);

        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<ListaSprint> getAll() {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.flush();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<ListaSprint> query = builder.createQuery(ListaSprint.class);
            Root<ListaSprint> from = query.from(ListaSprint.class);

            query.select(from);
            //query.orderBy(builder.asc(x))

            List<ListaSprint> listas = session.createQuery(query).getResultList();

            for(ListaSprint li : listas){
                li.getNombre().toLowerCase();
            }
            tx.commit();

            return listas;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    @Override
    public ListaSprint update(ListaSprint lista) {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(lista);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null)
                tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return lista;
    }

    @Override
    public void delete(ListaSprint lista) throws NotFoundException {
    }
}
