package ar.com.rocketdelivery.build.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ar.com.rocketdelivery.build.Dao.iIngredienteStockDao;
import ar.com.rocketdelivery.build.Dao.iIngredientesEnMenuDao;
import ar.com.rocketdelivery.build.Dao.iMenuDao;
import ar.com.rocketdelivery.build.Dao.iPedidoDao;
import ar.com.rocketdelivery.build.domain.inventario.IngredienteEnMenu;
import ar.com.rocketdelivery.build.domain.inventario.IngredienteStock;
import ar.com.rocketdelivery.build.domain.inventario.Menu;
import ar.com.rocketdelivery.build.domain.reportes.Pedido;
import lombok.Data;

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

	@Autowired
	private iPedidoDao pedidoDao;

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

	public String eliminarMenu(Long idMenu) {
		boolean eliminar = true;

		Menu m = menuDao.findById(idMenu).get();

		for (Pedido p : m.getPedidos()) {
			if (p.getEstado().getNombreEstado().equals("CANCELADO")
					|| p.getEstado().getNombreEstado().equals("ENTREGADO")
					|| p.getEstado().getNombreEstado().equals("NO ENTREGADO")) {

				eliminar = true;
			} else {
				eliminar = false;
				break;
			}
		}
		if (eliminar) {
			menuDao.delete(m);
			return "Menu " + m.getNombreMenu() + " eliminado con exito!";
		} else {
			return "No se puede eliminar el menu " + m.getNombreMenu()
					+ " ya que esta asociado a un pedido en vigencia";
		}
	}

	public String crearMenu(Menu menu, List<IngredienteEnMenu> ingredientesEnMenu) {
		if (menuDao.findByNombreMenu(menu.getNombreMenu()) == null) {
			Menu m = new Menu(menu.getNombreMenu(), menu.getDescripcion_menu(), menu.getPrecio(),
					menu.getImagen_menu());

			m.setIngredientesEnMenu(menu.getIngredientesEnMenu());

			for (IngredienteEnMenu iM : menu.getIngredientesEnMenu()) {
				IngredienteStock iStock = ingredienteStockDao
						.findById(iM.getIngredienteEnStock().getIdIngredienteStock()).get();

				if (iM.getCantidad() > iStock.getCantidadStock()) {
					m.setDisponible(false);
				}
			}

			menuDao.save(m);
			return "MENU " + m.getNombreMenu() + " CREADO";
		} else {
			return " El MENU " + menu.getNombreMenu() + " YA EXISTE";
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

	// Actualiza la disponibilidad de cada Menu de a cuerdo a los ingredientes en
	// Stock
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

	public Menu getMeunuById(Long id) {
		return menuDao.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu no existe"));
	}

}
