package ar.com.rocketdelivery.build.Dao;

import ar.com.rocketdelivery.build.domain.reportes.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iEstadoDao extends JpaRepository<Estado, Long> {
    
    public Estado findByNombreEstado(String nombreEstado);
}
