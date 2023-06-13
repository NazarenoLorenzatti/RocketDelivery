package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.Dao.iRolesDao;
import ar.com.rocketdelivery.build.Dao.iUsuarioDao;
import ar.com.rocketdelivery.build.domain.usuario.Rol;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String crearUsuario(String username, String password) {
        if (usuarioDao.existsByUsername(username)) {
            return "EL USUARIO YA EXISTE";
        } else {
            List<Rol> roles = new ArrayList();
            roles.add(rolesDao.findById(Long.valueOf(1)).get());

            usuarioDao.save(new Usuario(username, password, roles));
            return "USUARIO CREADO";
        }

    }

    public void cambiarContraseña(String username, String password){
       Usuario u = usuarioDao.findByUsername(username);
       u.setPassword(password);
       usuarioDao.save(u);
    }
}
