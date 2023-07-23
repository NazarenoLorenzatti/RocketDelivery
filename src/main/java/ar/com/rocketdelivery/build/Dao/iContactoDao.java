package ar.com.rocketdelivery.build.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;

public interface iContactoDao extends JpaRepository<Contacto, Long> {

	Contacto findByNombre(String nombre);

	Contacto findByUsuario(Usuario usuario);

	Contacto findByUsuarioUsername(String username);

}
