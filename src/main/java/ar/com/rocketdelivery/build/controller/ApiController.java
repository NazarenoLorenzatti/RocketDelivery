package ar.com.rocketdelivery.build.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.rocketdelivery.build.domain.inventario.IngredienteStock;
import ar.com.rocketdelivery.build.domain.inventario.Menu;
import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.reportes.Pedido;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.service.ContactoService;
import ar.com.rocketdelivery.build.service.IngredienteService;
import ar.com.rocketdelivery.build.service.MenuService;
import ar.com.rocketdelivery.build.service.PedidoService;
import ar.com.rocketdelivery.build.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class ApiController {

	// -------------------------------- SERVICIOS
	// ----------------------------------------//
	@Autowired
	private MenuService menuService;
	@Autowired
	private IngredienteService ingredienteService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ContactoService contactoService;
	@Autowired
	private UsuarioService usuarioService;
	// -----------------------------------------------------------------------------------//

	@PostMapping("/crear-contacto")
	public void crearContacto(@RequestBody Contacto contacto) throws Exception {
		contactoService.crearContacto(contacto.getNombre(), contacto.getApellido(), contacto.getEmail(),
				contacto.getTelefono(), contacto.getDireccion(),
				usuarioService.buscarUsuario(contacto.getUsuario().getIdUsuario()));
	}

	@PostMapping("/crear-ingrediente")
	public void crearIngrediente(@RequestBody IngredienteStock ingredienteEnStock) {
		ingredienteService.crearIngredienteStock(ingredienteEnStock.getNombreIngrediente(),
				ingredienteEnStock.getDescripcionIngrediente(), ingredienteEnStock.getImagenIngrediente(),
				ingredienteEnStock.getCantidadStock());
		actualizarDisponibilidad();
	}

	@PostMapping("/crear-menu")
	public void crearMenu(@RequestBody Menu menu) {
		menuService.crearMenu(menu, menu.getIngredientesEnMenu());
		actualizarDisponibilidad();
	}

	@PostMapping("/crear-pedido")
	public void crearPedido(@RequestBody Pedido pedido) {
		pedidoService.crearPedido(pedido.getMenus(), contactoService.buscarPorId(pedido.getContacto().getIdContacto()));
		actualizarDisponibilidad();
	}

	// ENDPOINTS PARA EDITAR
	@PutMapping("/editar-ingrediente")
	public void editarIngrediente(@RequestBody IngredienteStock ingredienteEnStock) {
		ingredienteService.actualizarIngredienteStock(ingredienteEnStock);
		actualizarDisponibilidad();
	}

	@PutMapping("/editar-contacto")
	public void editarContact(@RequestBody Contacto contacto) throws Exception {
		contactoService.actualizarContacto(contacto);
	}

	@PostMapping("/cambiar-contraseña")
	public void cambiarContraseña(@RequestBody Usuario usuario) throws Exception {
		usuarioService.cambiarContraseña(usuario.getUsername(), usuario.getPassword());
	}

	// ACTUALIZAR CANTIDAD DE INGREDIENTES EN STOCK ----------------------------
	@PostMapping("/actualizar-cantidad")
	public void actualizarCantidad(@RequestBody IngredienteStock ingredienteEnStock) {
		ingredienteService.actualizarCantidadStock(ingredienteEnStock.getIdIngredienteStock(),
				ingredienteEnStock.getCantidadStock());
	}

	// --------------------------------------------------------------------------
	// ESTADOS DEL PEDIDO
	@GetMapping("/en-progreso/{id}")
	public void establecerEnProgreso(@PathVariable("id") Long id) {
		pedidoService.establecerEnProgreso(id);
	}

	@GetMapping("/listo-para-entrega/{id}")
	public void listoParaEntregar(@PathVariable("id") Long id) {
		pedidoService.establecerListoParaEntregar(id);
	}

	@GetMapping("/entregado/{id}")
	public void establecerEntregado(@PathVariable("id") Long id) {
		pedidoService.establecerEntregado(id);
	}

	@GetMapping("/cancelado/{id}")
	public void establecerCancelado(@PathVariable("id") Long id) {
		pedidoService.establecerCancelado(id);
		actualizarDisponibilidad();
	}

	// --------------------------------------------------------------------------
	// ENPOINTS PARA LISTAR
	@GetMapping("/listar-contactos")
	public List<Contacto> getContactos() {
		return contactoService.listarContactos();
	}

	@GetMapping("/listar-menus")
	public List<Menu> getMenus() {
		return menuService.listarMenus();
	}

	@GetMapping("/listar-pedidos")
	public List<Pedido> getPedidos() {
		return pedidoService.listarPedidos();
	}

	@GetMapping("/listar-stock")
	public List<IngredienteStock> getStock() {
		return ingredienteService.listarIngredientesEnStock();
	}

	@GetMapping("/listar-usuarios")
	public List<Usuario> getUsuarios() {
		return usuarioService.listaUsuarios();
	}

	// ACTUALIZAR DISPONIBILIDAD DE MENUS --------------------------
	@GetMapping("/actualizar-disponibilidad")
	public void actualizarDisponibilidad() {
		menuService.actualizarDisponibilidad();
	}

	// BUSCAR USUARIO
	@GetMapping("/buscar-ususario/{username}")
	public Usuario buscarUsuario(@PathVariable("username") String username) {
		return usuarioService.buscarUsuario(username);
	}

	@GetMapping("/buscar-contacto/username/{username}")
	public Contacto buscarContactoByUsername(@PathVariable("username") String username) {
		return contactoService.buscarContactoByUsername(username);
	}

	// ELIMINAR MENU
	@DeleteMapping("/eliminar-menu/{id}")
	public void eliminarMenu(@PathVariable("id") Long id) {
		menuService.eliminarMenu(id);
	}

	@DeleteMapping("/eliminar-ususario/{usuarioId}")
	public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
		usuarioService.eliminarUsuario(usuarioId);
	}

	// EDITAR MENU
	@PutMapping("/editar-menu")
	public void editarMenu(@RequestBody Menu menu) {
		menuService.actualizarMenu(menu);
		actualizarDisponibilidad();
	}

	@GetMapping("/buscar-ingrediente/{id}")
	public ResponseEntity<IngredienteStock> findByidIngredienteStock(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(ingredienteService.findByidIngredienteStock(id));
	}

	// ELIMINAR INGREDIENTE
	@DeleteMapping("/eliminar-ingrediente/{id}")
	public void eliminarIngrediente(@PathVariable("id") Long id) {
		ingredienteService.eliminarIngredienteStock(id);
	}

	// IMPRESION DE REPORTES
	@GetMapping("/descargar-reporte")
	public ResponseEntity<byte[]> descargarReporte() {

		ByteArrayOutputStream outputStream = pedidoService.reportePedidos();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte-Ventas.xlsx");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(outputStream.toByteArray());
	}

	@GetMapping("/descargar-cartera")
	public ResponseEntity<byte[]> descargarCartera() {

		ByteArrayOutputStream outputStream = contactoService.reporteClientes();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cartera-Clientes.xlsx");
		System.out.println("SALIDA");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(outputStream.toByteArray());
	}

	// FILTRAR PEDIDO POR ESTADO
	@GetMapping("/filtrar-por/{estado}")
	public List<Pedido> buscarPorEstado(@PathVariable("estado") String estado) {
		return pedidoService.buscarPorEstado(estado);
	}

	// ARCHIVAR PEDIDO
	@GetMapping("/archivar-pedido/{id}")
	public void archivarPedido(@PathVariable("id") Long id) {
		pedidoService.archivarPedido(id);
	}

}
