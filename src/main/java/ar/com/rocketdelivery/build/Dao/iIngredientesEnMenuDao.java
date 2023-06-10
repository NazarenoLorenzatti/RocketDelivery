package ar.com.rocketdelivery.build.Dao;

import ar.com.rocketdelivery.build.domain.inventario.IngredienteEnMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iIngredientesEnMenuDao extends JpaRepository<IngredienteEnMenu, Long>{
    
//    public Ingrediente findByNombreIngrediente(String nombreIngrediente);
    
}
