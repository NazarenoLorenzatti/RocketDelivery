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

    public IngredienteStock buscarIngredientePorId(String idIngrediente) {
        return ingredienteStockDao.findById(Long.parseLong(idIngrediente)).get();
    }

    public IngredienteStock buscarIngredientePorNombre(String nombreIngrediente) {
        return ingredienteStockDao.findByNombreIngrediente(nombreIngrediente);
    }

    public void eliminarIngredienteStock(Long id) {
        ingredienteStockDao.delete(ingredienteStockDao.findById(id).get());
    }

    public void crearIngredienteStock(String nombreIngrediente, String descripcion_ingrediente, String imagen_ingrediente, double cantidadStock) {
        if (ingredienteStockDao.findByNombreIngrediente(nombreIngrediente) == null) {
            IngredienteStock i = new IngredienteStock(cantidadStock, nombreIngrediente, descripcion_ingrediente, imagen_ingrediente);
            ingredienteStockDao.save(i);
        }
    }

    public void actualizarIngredienteStock(String idIngrediente, String nombreIngrediente, String descripcion_ingrediente, String imagen_ingrediente) {
        IngredienteStock i = ingredienteStockDao.findById(Long.parseLong(idIngrediente)).get();
        i.setNombreIngrediente(nombreIngrediente);
        i.setDescripcionIngrediente(descripcion_ingrediente);
        i.setImagenIngrediente(imagen_ingrediente);
        ingredienteStockDao.save(i);
    }
    
        public void actualizarIngredienteStock(Long idIngrediente, String nombreIngrediente, String descripcion_ingrediente, String imagen_ingrediente) {
        IngredienteStock i = ingredienteStockDao.findById(idIngrediente).get();
        i.setNombreIngrediente(nombreIngrediente);
        i.setDescripcionIngrediente(descripcion_ingrediente);
        i.setImagenIngrediente(imagen_ingrediente);
        ingredienteStockDao.save(i);
    }

    public void actualizarCantidadStock(Long idIngrediente, double cantidadNueva) {
        IngredienteStock i = ingredienteStockDao.findById(idIngrediente).get();
        i.setCantidadStock(cantidadNueva);
        ingredienteStockDao.save(i);
    }

    public IngredienteEnMenu ingredienteMenu(double cantidad, IngredienteStock ingredienteEnStock) {
        IngredienteEnMenu i = new IngredienteEnMenu();
        i.setCantidad(cantidad);
        i.setIngredienteEnStock(ingredienteEnStock);
        return i;
    }
}
