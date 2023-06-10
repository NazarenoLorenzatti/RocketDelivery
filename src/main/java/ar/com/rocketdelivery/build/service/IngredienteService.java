package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.domain.inventario.IngredienteStock;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.rocketdelivery.build.Dao.iIngredientesEnMenuDao;
import ar.com.rocketdelivery.build.Dao.iIngredienteStockDao;
import ar.com.rocketdelivery.build.domain.inventario.IngredienteEnMenu;

@Service
@Data
public class IngredienteService {

    @Autowired
    private iIngredientesEnMenuDao ingredienteEnMenuDao;

    @Autowired
    private iIngredienteStockDao ingredienteStockDao;

    public List<IngredienteStock> listarIngredientesEnStock() {
        return ingredienteStockDao.findAll();
    }

    public IngredienteStock buscarIngredientePorNombre(String nombreIngrediente) {
        return ingredienteStockDao.findByNombreIngrediente(nombreIngrediente);
    }

    public void eliminarIngredienteStock(IngredienteStock ingredienteStock) {
        ingredienteStockDao.delete(ingredienteStock);
    }

    public void crearIngrediente(String nombreIngrediente, String descripcion_ingrediente, String imagen_ingrediente, double cantidadStock) {
        if (ingredienteStockDao.findByNombreIngrediente(nombreIngrediente) == null) {
            IngredienteStock i = new IngredienteStock(cantidadStock, nombreIngrediente, descripcion_ingrediente, imagen_ingrediente);
            ingredienteStockDao.save(i);
        }
    }

    public void actualizarIngrediente(IngredienteStock ingredienteEnStock, String nombreIngrediente, String descripcion_ingrediente, String imagen_ingrediente) {
        ingredienteEnStock.setNombreIngrediente(nombreIngrediente);
        ingredienteEnStock.setDescripcionIngrediente(descripcion_ingrediente);
        ingredienteEnStock.setImagenIngrediente(imagen_ingrediente);
        ingredienteStockDao.save(ingredienteEnStock);
    }
    
    public IngredienteEnMenu ingredienteMenu(double cantidad, IngredienteStock ingredienteEnStock){
        IngredienteEnMenu i = new IngredienteEnMenu();
        i.setCantidad(cantidad);
        i.setIngredienteEnStock(ingredienteEnStock);
        return i;
    }
}
