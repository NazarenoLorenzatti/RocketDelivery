package ar.com.rocketdelivery.build.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.com.rocketdelivery.build.domain.usuario.Usuario;

// No es necesario crear una implementacion de esta interface ya que spring la crea automaticamente
public interface iUsuarioDao extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);

	public boolean existsByUsername(String username);

	@Query(value = "SELECT u.* FROM usuario u left join contacto c on u.id_usuario = c.usuario_id_usuario where c.usuario_id_usuario is null", nativeQuery = true)
	public List<Usuario> getUsuariosSinContacto();

}
