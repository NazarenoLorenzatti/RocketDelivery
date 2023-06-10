package ar.com.rocketdelivery.build.Dao;

import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iContactoDao extends JpaRepository<Contacto, Long> {
    
    public Contacto findByNombre(String nombre);
    
    public Contacto findByUsuario(Usuario usuario);
}
