package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.Dao.*;
import ar.com.rocketdelivery.build.domain.usuario.*;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private iUsuarioDao usuarioDao;

    @Autowired
    private iRolesDao rolesDao;

    public Usuario buscarUsuario(Long Id) {
        return usuarioDao.findById(Id).get();
    }

    public Usuario buscarUsuario(String username) {
        return usuarioDao.findByUsername(username);
    }

    public List<Usuario> listaUsuarios(){
        return usuarioDao.findAll();
    }


    public String crearUsuario(Usuario usuario) {
        if (usuarioDao.existsByUsername(usuario.getUsername())) {
            return "EL USUARIO YA EXISTE";
        } else {
            List<Rol> roles = new ArrayList();
            roles.add(rolesDao.findById(Long.valueOf(1)).get());

            usuarioDao.save(new Usuario(usuario.getUsername(), encriptarPassword(usuario.getPassword()), roles));
            return "USUARIO CREADO";
        }

    }

    public String crearUsuario(String username, String password) {
        if (usuarioDao.existsByUsername(username)) {
            return "EL USUARIO YA EXISTE";
        } else {
            List<Rol> roles = new ArrayList();
            roles.add(rolesDao.findById(Long.valueOf(1)).get());

            usuarioDao.save(new Usuario(username, encriptarPassword(password), roles));
            return "USUARIO CREADO";
        }

    }
//    public String crearUsuario(Usuario usuario) {
//        if (usuarioDao.existsByUsername(usuario.getUsername())) {
//            return "EL USUARIO YA EXISTE";
//        } else {
//            usuarioDao.save(usuario);
//            return "USUARIO CREADO";
//        }
//
//    }

    public void cambiarContraseña(String username, String password) {
        Usuario u = usuarioDao.findByUsername(username);
        u.setPassword(password);
        usuarioDao.save(u);
    }
    
    public String eliminarUsuario(Usuario usuario){
        usuarioDao.delete(usuario);
        return "USUARIO " + usuario.getUsername() + " ELIMINADO";
    }
    
    public String eliminarUsuario(Long id){
        usuarioDao.deleteById(id);
        return "USUARIO " + id + " ELIMINADO";
    }
    
        public static String encriptarPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
