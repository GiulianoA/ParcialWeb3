package ar.edu.iua.project.parcial.persistence.dao;

import ar.edu.iua.project.parcial.exceptions.BusinessException;
import ar.edu.iua.project.parcial.exceptions.NotFoundException;
import ar.edu.iua.project.parcial.model.Lista;
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
public class ListaDaoImplementacion implements IGenericDAO<Lista, Serializable> {

    private static ListaDaoImplementacion instance;

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
    public Lista save(Lista lista) {
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
    public List<Lista> getAll() {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.flush();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Lista> query = builder.createQuery(Lista.class);
            Root<Lista> from = query.from(Lista.class);

            query.select(from);

            List<Lista> listas = session.createQuery(query).getResultList();

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
    public Lista update(Lista lista) {
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
    public Lista getOne(String nombre) throws BusinessException, NotFoundException {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.flush();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Lista> query = builder.createQuery(Lista.class);
            Root<Lista> from = query.from(Lista.class);

            query.select(from).where(builder.equal(from.get("nombre"), nombre));

            List<Lista> listas = session.createQuery(query).getResultList();

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
    public Lista getOneId(int id) throws BusinessException, NotFoundException {
        Session session = emf.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Lista lista = session.get(Lista.class, id);

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
    public void delete(Lista lista) throws NotFoundException {
    }
}
