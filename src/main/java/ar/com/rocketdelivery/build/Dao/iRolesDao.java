package ar.com.rocketdelivery.build.Dao;

import ar.com.rocketdelivery.build.domain.usuario.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iRolesDao extends JpaRepository<Rol, Long> {
    
    public Rol findByNombreRol(String nombreRol);
}
