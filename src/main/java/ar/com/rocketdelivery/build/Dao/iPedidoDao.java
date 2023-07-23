package ar.com.rocketdelivery.build.Dao;

import ar.com.rocketdelivery.build.domain.reportes.Estado;
import ar.com.rocketdelivery.build.domain.reportes.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iPedidoDao extends JpaRepository<Pedido, Long> {

    public List<Pedido> findByEstado(Estado estado);
    
    public List<Pedido> findAllByOrderByIdPedidoDesc();
    
}
