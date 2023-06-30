package ar.com.rocketdelivery.build.controller;

import ar.com.rocketdelivery.build.domain.inventario.*;
import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.reportes.Pedido;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.service.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
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

    @PostMapping("/editar-contacto")
    public void editarContact(Contacto contacto, String nombre, String apellido, String email, String telefono, String direccion) throws Exception {
        contactoService.actualizarContacto(contacto, nombre, apellido, email, telefono, direccion);
    }

    @PostMapping("/cambiar-contraseña")
    public void cambiarContraseña(String username, String password) throws Exception {
        usuarioService.cambiarContraseña(username, password);
    }

    @GetMapping("/buscar-ususario/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username) {
        return usuarioService.buscarUsuario(username);
    }

    @DeleteMapping("/eliminar-ususario/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
        usuarioService.eliminarUsuario(usuarioId);
    }

//    @PostMapping("/crearIngrediente")
//    public void crearIngrediente(String nombreIngrediente, String descripcion, String link, double cantidadStock) {
//        ingredienteService.crearIngredienteStock(nombreIngrediente, descripcion, link, cantidadStock);
//    }
    @PostMapping("/crearIngrediente")
    public void crearIngrediente(IngredienteStock ingredienteEnStock) {
        ingredienteService.crearIngredienteStock(ingredienteEnStock.getNombreIngrediente(),
                ingredienteEnStock.getDescripcionIngrediente(), ingredienteEnStock.getImagenIngrediente(),
                ingredienteEnStock.getCantidadStock());
    }

//    @PostMapping("/editarIngrediente")
//    public void editarIngrediente(String idIngrediente, String nombreIngrediente, String descripcion, String link) {
//        ingredienteService.actualizarIngredienteStock(idIngrediente, nombreIngrediente, descripcion, link);
//    }
    @PostMapping("/editarIngrediente")
    public void editarIngrediente(IngredienteStock ingredienteEnStock) {
        ingredienteService.actualizarIngredienteStock(ingredienteEnStock.getIdIngredienteStock(),
                ingredienteEnStock.getNombreIngrediente(), ingredienteEnStock.getDescripcionIngrediente(),
                ingredienteEnStock.getImagenIngrediente());
    }

    @PostMapping("/actualizar-cantidad")
    public void actualizarCantidad(IngredienteStock ingredienteEnStock, double cantidad) {
        ingredienteService.actualizarCantidadStock(ingredienteEnStock.getIdIngredienteStock(), cantidad);
    }

//    @PostMapping("/crear-menu")
//    public void crearMenu(String nombreMenu, String descripcion_menu, double precio, String[][] ingredientesSeleccionados, String linkImagen) {
//        menuService.crearMenu(nombreMenu, descripcion_menu, precio, ingredientesSeleccionados, linkImagen);
//    }
    @PostMapping("/crear-menu")
    public void crearMenu(Menu menu, String[][] ingredientesSeleccionados) {
        menuService.crearMenu(menu.getNombreMenu(), menu.getDescripcion_menu(), menu.getPrecio(), ingredientesSeleccionados, menu.getImagen_menu());
    }

    @GetMapping("/listar-contactos")
    public List<Contacto> getContactos() {
        return contactoService.listarContactos();
    }

    @GetMapping("/listar-menus")
    public List<Menu> getMenus() {
        return menuService.listarMenus();
    }

//    @PostMapping("/crear-pedido")
//    public void crearPedido(List<String> listaId, String idContacto) {
//        pedidoService.crearPedido(menuService.listarMenusPorId(listaId), contactoService.buscarPorId(idContacto));
//    }
    @PostMapping("/crear-pedido")
    public void crearPedido(List<Menu> menus, Contacto contacto) {
        pedidoService.crearPedido(menus, contacto);
    }

    @GetMapping("/listar-pedidos")
    public List<Pedido> getPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/listar-stock")
    public List<IngredienteStock> getStock() {
        return ingredienteService.listarIngredientesEnStock();
    }

    @GetMapping("/actualizar-disponibilidad")
    public void actualizarStock() {
        menuService.actualizarDisponibilidad();
    }

    // ----------------- PRUEBAS -------------------- Ignorar de momento o probar como prefieran
    @GetMapping("/pruebaCliente")
    public String prueba() {

        List<Menu> menusDelPedido = new ArrayList();
        menusDelPedido.add(menuService.buscarMenuPorNombre("Papas Fritas"));
        menusDelPedido.add(menuService.buscarMenuPorNombre("Ñoquis"));

        Long nroPedido = pedidoService.crearPedido(menusDelPedido, contactoService.buscarPorNombre("Nazareno"));

        pedidoService.establecerEnProgreso(pedidoService.buscarPorId(nroPedido), pedidoService.buscarPorId(nroPedido).getMenus());

        pedidoService.establecerListoParaEntregar(pedidoService.buscarPorId(nroPedido));

        pedidoService.establecerEntregado(pedidoService.buscarPorId(nroPedido));

        return "Hola, mundo!";
    }
    // ------------------------  //

}
