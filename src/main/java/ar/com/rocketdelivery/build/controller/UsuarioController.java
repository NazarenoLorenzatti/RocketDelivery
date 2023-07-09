package ar.com.rocketdelivery.build.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.service.ContactoService;
import ar.com.rocketdelivery.build.service.UsuarioService;
//
//import java.util.HashSet;
//import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContactoService contactoService;

    @PostMapping("/guardar-usuario")
    public String crearUsuario(@RequestBody Usuario usuario) throws Exception {
        return usuarioService.crearUsuario(usuario);
    }
}
