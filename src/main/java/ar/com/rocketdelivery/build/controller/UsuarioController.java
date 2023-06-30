package ar.com.rocketdelivery.build.controller;

import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.service.ContactoService;
import ar.com.rocketdelivery.build.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//
//import java.util.HashSet;
//import java.util.Set;

@RestController
@RequestMapping("/usuarios")
//@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContactoService contactoService;


//    @PostMapping("/guardar-usuario")
//    public String crearUsuario(@RequestBody Usuario usuario) throws Exception {
//        return usuarioService.crearUsuario(usuario.getUsername(), usuario.getPassword());
//    }
    @PostMapping("/guardar-usuario")
    public String crearUsuario(@RequestBody Usuario usuario) throws Exception {
        return usuarioService.crearUsuario(usuario);
    }

    @PostMapping("/crearContacto")
    public void crearContacto(Contacto contacto, Usuario usuario) throws Exception {
        contactoService.crearContacto(contacto.getNombre(), contacto.getApellido(), contacto.getEmail(),
                contacto.getTelefono(), contacto.getDireccion(), usuario);
    }



}
