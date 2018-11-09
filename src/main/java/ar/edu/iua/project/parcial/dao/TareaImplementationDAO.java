package ar.edu.iua.project.parcial.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ar.edu.iua.project.parcial.dao.util.HibernateUtil;
import ar.edu.iua.project.parcial.model.Tarea;
import org.hibernate.*;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Component
public class TareaImplementationDAO implements IGenericDAO<Tarea, Serializable> {

    private static TareaImplementationDAO instance ;



    @Autowired
    private EntityManagerFactory entityManagerFactory;


    public TareaImplementationDAO(){}

    @Bean
    public static TareaImplementationDAO getInstance(){
        if(instance == null){
            instance = new TareaImplementationDAO();
        }
        return instance;
    }

    @Override
    public int save(Tarea tarea) {

        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;
        Integer tareaId = 0;

        try{
            tx = session.beginTransaction();
            tareaId = (Integer) session.save(tarea);
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return tareaId;


        /*
         {
        "id": 33,
        "estimacion": 33,
        "fecha_creacion": "dsa",
        "fecha_modificacion": "sadsa",
        "nombre": "task1",
        "prioridad": "alta1",
        "lista": {
            "id": 1
        }
    }*/

    }

    @Override
    public List<Tarea> getAll(){
        System.out.println("Sesioooon");
        List<Tarea> listaTareas = new ArrayList<>();
        Transaction tx = null;

        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        try {
            tx = session.beginTransaction();

            session.flush();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Tarea> query = builder.createQuery(Tarea.class);
            query.select(query.from(Tarea.class));
            Query<Tarea> q = session.createQuery(query);
            listaTareas = q.getResultList();
            tx.commit();



        }catch(HibernateException e) {
           System.out.println("EXCEPTION");
        }

        return listaTareas;
    }
}
