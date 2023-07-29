package ar.com.rocketdelivery.build.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.rocketdelivery.build.domain.inventario.IngredienteStock;

public interface iIngredienteStockDao extends JpaRepository<IngredienteStock, Long> {

	public IngredienteStock findByidIngredienteStock(Long idIngredienteStock);

	public IngredienteStock findByNombreIngrediente(String nombreIngrediente);

}
