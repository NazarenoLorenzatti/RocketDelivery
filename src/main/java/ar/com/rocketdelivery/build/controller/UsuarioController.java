package ar.com.rocketdelivery.build.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.service.ContactoService;
import ar.com.rocketdelivery.build.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ContactoService contactoService;

	@PostMapping("/guardar-usuario")
	public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.ok().body(usuarioService.crearUsuario(usuario));
	}

	@GetMapping("/listar-usuarios-sin-contacto")
	public List<Usuario> getUsuariosSinContacto() {
		return usuarioService.getUsuariosSinContacto();
	}

}
