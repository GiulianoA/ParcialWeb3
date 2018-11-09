package ar.edu.iua.project.parcial.dao;

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

    public static TareaImplementationDAO getTareaDAO() {

        if(dataBaseActive == "MYSQL"){
            return TareaImplementationDAO.getInstance();
        }
    /*    else {
            return BillingOracleImpDAO.getInstance();
        }
     */
        return null;
    }

}
