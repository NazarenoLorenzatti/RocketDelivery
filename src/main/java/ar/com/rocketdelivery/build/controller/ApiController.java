package ar.com.rocketdelivery.build.controller;

import ar.com.rocketdelivery.build.domain.inventario.*;
import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.reportes.Pedido;
import ar.com.rocketdelivery.build.service.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/crearUsuario")
    public void crearUsuario(String username, String password) {
        //Reemplazar argumentos como corresponde
        usuarioService.crearUsuario("user", "123");
    }
    
    @PostMapping("/cambiar-contraseña")
    public void cambiarContraseña(String username, String password){
        usuarioService.cambiarContraseña(username, password);
    }
    
    @PostMapping("/crearContacto")
    public void crearContacto(String nombre, String apellido, String email, String telefono, String direccion, Long idUsuario) {
        // Reemplazar argumentos como corresponde 
        contactoService.crearContacto("Nazareno", "Lorenzatti", "nl.loragro@gmail.com",
                "3466404290", "Lisandro de la torre 124", usuarioService.buscarUsuario(Long.valueOf(2)));
    }
    
    @PostMapping("/editar-contacto")
    public void editarContact(String idContacto, String nombre, String apellido, String email, String telefono, String direccion){
        contactoService.actualizarContacto(contactoService.buscarPorId(idContacto), nombre, apellido, email, telefono, direccion);
    }

    @PostMapping("/crearIngrediente")
    public void crearIngrediente(String nombreIngrediente, String descripcion, String link, double cantidadStock) {
        // ESTO CREA EL INGREDIENTE Y ESTABLECE EL STOCK DEL MISMO
        ingredienteService.crearIngredienteStock(nombreIngrediente, descripcion, link, cantidadStock);
    }
    
    @PostMapping("/editarIngrediente")
    public void editarIngrediente(String idIngrediente, String nombreIngrediente, String descripcion, String link) {
        ingredienteService.actualizarIngredienteStock(idIngrediente, nombreIngrediente, descripcion, link);
    }
    
    @PostMapping("/actualizar-cantidad")
    public void actualizarCantidad(String idIngrediente, double cantidad){
        ingredienteService.actualizarCantidadStock(idIngrediente, cantidad);
    }
    
    @PostMapping("/crear-menu")
    public void crearMenu(String nombreMenu, String descripcion_menu, double precio, String [][] ingredientesSeleccionados, String linkImagen){
        menuService.crearMenu(nombreMenu, descripcion_menu, precio, ingredientesSeleccionados, linkImagen);
    }
    

    @GetMapping("/listar-contactos")
    public List<Contacto> getContactos() {
        return contactoService.listarContactos();
    }
    
    @GetMapping("/listar-menus")
    public List<Menu> getMenus(){
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
