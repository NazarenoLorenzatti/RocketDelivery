package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.Dao.iMenuDao;
import ar.com.rocketdelivery.build.domain.inventario.*;
import java.util.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.rocketdelivery.build.Dao.*;

@Service
@Data
public class MenuService {

    @Autowired
    private iMenuDao menuDao;

    @Autowired
    private iIngredienteStockDao ingredienteStockDao;
    
    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private iIngredientesEnMenuDao ingredientesDao;

    public List<Menu> listarMenus() {
        return menuDao.findAll();
    }
    
    public List<Menu> listarMenusPorId(List<String> listaId) {
        List<Menu> lista = new ArrayList();
        for (String id : listaId){
            lista.add(menuDao.findById(Long.valueOf(id)).get());
        }
        return lista;
    }

    public Menu buscarMenuPorNombre(String nombreMenu) {
        return menuDao.findByNombreMenu(nombreMenu);
    }

    public List<Menu> buscarMenuPorIngrediente(IngredienteEnMenu ingredientes) {
        return menuDao.findByIngredientesEnMenu(ingredientes);
    }

    public void eliminarMenu(Menu menu) {
        menuDao.delete(menu);
    }

    public String crearMenu(String nombreMenu, String descripcion_menu, Double precio, String[][] ingredientesSeleccionados, String imagen_menu) {
        List<IngredienteStock> listaDeIngredientesSeleccionados = new ArrayList();
        List<IngredienteEnMenu> ingredientesDelMenu = new ArrayList();
        
        for (int i=0; i > ingredientesSeleccionados.length ; i++) {
            IngredienteStock ing = ingredienteStockDao.findById(Long.parseLong(ingredientesSeleccionados[i][0])).get();
            listaDeIngredientesSeleccionados.add(ing);
        }

        for (IngredienteStock iS : listaDeIngredientesSeleccionados) {
            int i = 0;
            String cantidadStr = ingredientesSeleccionados[1][i];
            double cantidad = Double.parseDouble(cantidadStr);
            ingredientesDelMenu.add(ingredienteService.ingredienteMenu(cantidad, iS));
            i++;
        }

        if (menuDao.findByNombreMenu(nombreMenu) == null) {
            Menu m = new Menu(nombreMenu, descripcion_menu, precio, imagen_menu);
            m.setIngredientesEnMenu(ingredientesDelMenu);
            for (IngredienteEnMenu iM : ingredientesDelMenu) {

                IngredienteStock iStock = ingredienteStockDao.findByNombreIngrediente(iM.getIngredienteEnStock().getNombreIngrediente());

                if (iM.getCantidad() > iStock.getCantidadStock()) {
                    m.setDisponible(false);
                }
            }
            menuDao.save(m);
            return "MENU :" + m.getNombreMenu() + " CREADO";
        } else {

            return " El MENU :" + nombreMenu + " YA EXISTE";
        }

    }

    public void actualizarMenu(Menu menuId, String nombreMenu, String descripcion_menu, Double precio, List<IngredienteEnMenu> ingredientesEnMenu, String imagen_menu) {
        Optional<Menu> m = menuDao.findById(menuId.getIdMenu());
        Menu menu = m.get();
        menu.setNombreMenu(nombreMenu);
        menu.setDescripcion_menu(descripcion_menu);
        menu.setPrecio(precio);
        menu.setIngredientesEnMenu(ingredientesEnMenu);
        menu.setImagen_menu(imagen_menu);
        menuDao.save(menu);
    }

    // Actualiza la disponibilidad de cada Menu de a cuerdo a los ingredientes en Stock
    public void actualizarDisponibilidad() {
        List<Menu> menusCargados = menuDao.findAll();
        for (Menu m : menusCargados) {
            for (IngredienteEnMenu i : m.getIngredientesEnMenu()) {
                if (i.getCantidad() > i.getIngredienteEnStock().getCantidadStock()) {
                    m.setDisponible(false);
                    menuDao.save(m);
                    break;
                }
                if (i.getCantidad() < i.getIngredienteEnStock().getCantidadStock()) {
                    m.setDisponible(true);
                }
            }
        }
    }

}
