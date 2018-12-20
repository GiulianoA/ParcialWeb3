package ar.edu.iua.project.parcial;

import ar.edu.iua.project.parcial.repository.UsuariosRepository;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class ParcialApplication implements CommandLineRunner {

	final static Logger log = Logger.getLogger("ParcialApplication.class");

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(ParcialApplication.class, args);
	}

	@Autowired
	private PasswordEncoder pe;
	@Autowired
	private UsuariosRepository uDAO;

	@Override
	public void run(String... args) throws Exception {
		log.debug("Datasource actual = " + dataSource);
		//uDAO.setPassword(pe.encode("giuliano"), "giuliano", "giuliano@gmail.com");
		//uDAO.setPassword(pe.encode("facundo"), "facu", "facu@gmail.com");
	}

}

