package ar.edu.iua.project.parcial.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.iua.project.parcial.model.User;
import ar.edu.iua.project.parcial.repository.UsuariosRepository;

@Service
public class PersistenceUserDetailService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> r = usuarioDAO.findByUsernameOrEmail(username, username);
        if (r.size() == 0)
            throw new UsernameNotFoundException("No se encuentra " + username);
        return r.get(0);

    }

}

