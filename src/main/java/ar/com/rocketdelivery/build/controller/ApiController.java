package ar.com.rocketdelivery.build.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ar.com.rocketdelivery.build.domain.inventario.*;
import ar.com.rocketdelivery.build.domain.reportes.*;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.service.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    // -------------------------------- SERVICIOS ----------------------------------------//
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
                contacto.getTelefono(), contacto.getDireccion(), usuarioService.buscarUsuario(contacto.getUsuario().getIdUsuario()));
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
    @PostMapping("/editar-ingrediente")
    public void editarIngrediente(@RequestBody IngredienteStock ingredienteEnStock) {
        ingredienteService.actualizarIngredienteStock(ingredienteEnStock.getIdIngredienteStock(),
                ingredienteEnStock.getNombreIngrediente(), ingredienteEnStock.getDescripcionIngrediente(),
                ingredienteEnStock.getImagenIngrediente());
        actualizarDisponibilidad();
    }

    @PostMapping("/editar-contacto")
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
        ingredienteService.actualizarCantidadStock(ingredienteEnStock.getIdIngredienteStock(), ingredienteEnStock.getCantidadStock());
    }

    //--------------------------------------------------------------------------
    // ESTADOS DEL PEDIDO
    @GetMapping("/en-progreso/{id}")
    public void establecerEnProgreso(@PathVariable("id") Long id) {
        pedidoService.establecerEnProgreso(id);
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

    @GetMapping("/listo-para-entrega/{id}")
    public void listoParaEntregar(@PathVariable("id") Long id) {
        pedidoService.establecerListoParaEntregar(id);
    }

    //--------------------------------------------------------------------------
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

    //ACTUALIZAR DISPONIBILIDAD DE MENUS --------------------------
    @GetMapping("/actualizar-disponibilidad")
    public void actualizarDisponibilidad() {
        menuService.actualizarDisponibilidad();
    }

    //BUSCAR USUARIO     
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

    //EDITAR MENU
    @PutMapping("/editar-menu")
    public void editarMenu(@RequestBody Menu menu) {
        menuService.actualizarMenu(menu);
        actualizarDisponibilidad();
    }

    //ELIMINAR INGREDIENTE
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

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }

    @GetMapping("/descargar-cartera")
    public ResponseEntity<byte[]> descargarCartera() {

        ByteArrayOutputStream outputStream = contactoService.reporteClientes();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cartera-Clientes.xlsx");
        System.out.println("SALIDA");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }

}

