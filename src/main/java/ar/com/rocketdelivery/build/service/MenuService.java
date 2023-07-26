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
    private iIngredientesEnMenuDao ingredienteEnMenuDao;

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
        for (String id : listaId) {
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

    public void eliminarMenu(Long idMenu) {
        menuDao.delete(menuDao.findById(idMenu).get());
    }

    public String crearMenu(Menu menu, List<IngredienteEnMenu> ingredientesEnMenu) {

        if (menuDao.findByNombreMenu(menu.getNombreMenu()) == null) {
            Menu m = new Menu(menu.getNombreMenu(), menu.getDescripcion_menu(), menu.getPrecio(), menu.getImagen_menu());
            m.setIngredientesEnMenu(menu.getIngredientesEnMenu());
            for (IngredienteEnMenu iM : menu.getIngredientesEnMenu()) {
                IngredienteStock iStock = ingredienteStockDao.findById(iM.getIngredienteEnStock().getIdIngredienteStock()).get();
                if (iM.getCantidad() > iStock.getCantidadStock()) {
                    m.setDisponible(false);
                }
            }
            menuDao.save(m);
            return "MENU :" + m.getNombreMenu() + " CREADO";
        } else {

            return " El MENU :" + menu.getNombreMenu() + " YA EXISTE";
        }

    }

    public void actualizarMenu(Menu menu) {
        Optional<Menu> m = menuDao.findById(menu.getIdMenu());
        Menu menuActualizar = m.get();
        menuActualizar.setNombreMenu(menu.getNombreMenu());
        menuActualizar.setDescripcion_menu(menu.getDescripcion_menu());
        menuActualizar.setPrecio(menu.getPrecio());
        menuActualizar.setImagen_menu(menu.getImagen_menu());

        List<IngredienteEnMenu> ingredientesViejos = menuActualizar.getIngredientesEnMenu();
        ingredientesViejos.retainAll(menu.getIngredientesEnMenu());

        for (IngredienteEnMenu iem : menu.getIngredientesEnMenu()) {
            if (!ingredientesViejos.contains(iem)) {
                iem.setMenu(menuActualizar);
                ingredientesViejos.add(iem);
            }
        }
        menuActualizar.setIngredientesEnMenu(ingredientesViejos);
        menuDao.save(menuActualizar);
    }

    // Actualiza la disponibilidad de cada Menu de a cuerdo a los ingredientes en Stock
    public void actualizarDisponibilidad() {
        List<Menu> menusCargados = menuDao.findAll();
        for (Menu m : menusCargados) {
            for (IngredienteEnMenu i : m.getIngredientesEnMenu()) {
                if (i.getCantidad() >= i.getIngredienteEnStock().getCantidadStock()) {
                    m.setDisponible(false);
                    menuDao.save(m);
                    break;
                }
                if (i.getCantidad() <= i.getIngredienteEnStock().getCantidadStock()) {
                    m.setDisponible(true);
                }
            }
            menuDao.save(m);
        }
    }

}
