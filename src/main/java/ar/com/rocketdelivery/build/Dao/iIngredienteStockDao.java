package ar.com.rocketdelivery.build.Dao;

import ar.com.rocketdelivery.build.domain.inventario.IngredienteEnMenu;
import ar.com.rocketdelivery.build.domain.inventario.IngredienteStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iIngredienteStockDao extends JpaRepository<IngredienteStock, Long> {
    
//    public IngredienteStock findByIngrediente(IngredienteEnMenu ingrediente);
    
    public IngredienteStock findByNombreIngrediente(String nombreIngrediente);
}
