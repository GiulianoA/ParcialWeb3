package ar.edu.iua.project.parcial.persistence.dao;

import org.springframework.context.annotation.Bean;

public class FactoryDAO {

    private static FactoryDAO instance;

    private FactoryDAO(){ }

    @Bean
    public static FactoryDAO getInstance(){
        if(instance == null){
            instance = new FactoryDAO();
        }
        return instance;
    }

    private static String dataBaseActive = "MYSQL";

    public static IGenericDAO getListaDAO() {

        if(dataBaseActive == "MYSQL"){
            return ListaDaoImplementacion.getInstance();
        }
    /*    else {

        }
     */
        return null;
    }

}
