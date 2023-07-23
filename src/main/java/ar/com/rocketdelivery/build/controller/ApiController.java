package ar.com.rocketdelivery.build.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		actualizarStock();
	}

	@PostMapping("/crear-menu")
	public void crearMenu(@RequestBody Menu menu) {
		menuService.crearMenu(menu, menu.getIngredientesEnMenu());
		actualizarStock();
	}

	@PostMapping("/crear-pedido")
	public void crearPedido(@RequestBody Pedido pedido) {
		pedidoService.crearPedido(pedido.getMenus(), contactoService.buscarPorId(pedido.getContacto().getIdContacto()));
		actualizarStock();
	}

	// ENDPOINTS PARA EDITAR
	@PostMapping("/editar-ingrediente")
	public void editarIngrediente(@RequestBody IngredienteStock ingredienteEnStock) {
		ingredienteService.actualizarIngredienteStock(ingredienteEnStock.getIdIngredienteStock(),
				ingredienteEnStock.getNombreIngrediente(), ingredienteEnStock.getDescripcionIngrediente(),
				ingredienteEnStock.getImagenIngrediente());
		actualizarStock();
	}

	@PostMapping("/editar-contacto")
	public void editarContact(@RequestBody Contacto contacto) throws Exception {
		contactoService.actualizarContacto(contacto);
	}

	@PostMapping("/cambiar-contraseña")
	public void cambiarContraseña(@RequestBody Usuario usuario) throws Exception {
		usuarioService.cambiarContraseña(usuario.getUsername(), usuario.getPassword());
	}

	@DeleteMapping("/eliminar-ususario/{usuarioId}")
	public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
		usuarioService.eliminarUsuario(usuarioId);
	}

	// ACTUALIZAR CANTIDAD DE INGREDIENTES EN STOCK ----------------------------
	@PostMapping("/actualizar-cantidad")
	public void actualizarCantidad(@RequestBody IngredienteStock ingredienteEnStock) {
		ingredienteService.actualizarCantidadStock(ingredienteEnStock.getIdIngredienteStock(),
				ingredienteEnStock.getCantidadStock());
	}

	// --------------------------------------------------------------------------
	// ESTADOS DEL PEDIDO
	@PostMapping("/en-progreso")
	public void establecerEnProgreso(@RequestBody Pedido pedido) {
		pedidoService.establecerEnProgreso(pedido);
	}

	@PostMapping("/entregado")
	public void establecerEntregado(@RequestBody Pedido pedido) {
		pedidoService.establecerEntregado(pedido);
	}

	@PostMapping("/cancelado")
	public void establecerCancelado(@RequestBody Pedido pedido) {
		pedidoService.establecerCancelado(pedido);
	}

	@PostMapping("/listo-para-entrega")
	public void listoParaEntregar(@RequestBody Pedido pedido) {
		pedidoService.establecerListoParaEntregar(pedido);
	}

	// --------------------------------------------------------------------------

	// ENPOINTS PARA LISTAR

	@GetMapping("/listar-usuarios")
	public List<Usuario> getUsuarios() {
		return usuarioService.listaUsuarios();
	}

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

	// ACTUALIZAR DISPONIBILIDAD DE MENUS --------------------------
	@GetMapping("/actualizar-disponibilidad")
	public void actualizarStock() {
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

	// IMPRESION DE REPORTES
	@GetMapping("/descargar-reporte")
	public ResponseEntity<byte[]> descargarReporte() {

		ByteArrayOutputStream outputStream = pedidoService.reportePedidos();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file-name.xlsx");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(outputStream.toByteArray());
	}

	@GetMapping("/descargar-cartera")
	public ResponseEntity<byte[]> descargarCartera() {

		ByteArrayOutputStream outputStream = contactoService.reporteClientes();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file-name.xlsx");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(outputStream.toByteArray());
	}
}
