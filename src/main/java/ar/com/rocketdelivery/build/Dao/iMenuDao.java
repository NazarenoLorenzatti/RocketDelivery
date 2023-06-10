package ar.com.rocketdelivery.build.Dao;

import ar.com.rocketdelivery.build.domain.inventario.IngredienteEnMenu;
import ar.com.rocketdelivery.build.domain.inventario.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iMenuDao extends JpaRepository<Menu, Long>{
    
    public Menu findByNombreMenu(String nombreMenu);
    
    public List<Menu> findByIngredientesEnMenu(IngredienteEnMenu ingredientes);

}
