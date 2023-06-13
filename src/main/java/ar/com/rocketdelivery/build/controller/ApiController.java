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

    @GetMapping("/hello")
    public String hello() {
        return "Hola, mundo!";
    }

    @GetMapping("/crearUsuario")
    public void crearUsuario(String username, String password) {
        //Reemplazar argumentos como corresponde
        usuarioService.crearUsuario("user", "123");
    }

    @GetMapping("/crearContacto")
    public void crearContacto(String nombre, String apellido, String email, String telefono, String direccion, Long idUsuario) {
        // Reemplazar argumentos como corresponde 
        contactoService.crearContacto("Nazareno", "Lorenzatti", "nl.loragro@gmail.com",
                "3466404290", "Lisandro de la torre 124", usuarioService.buscarUsuario(Long.valueOf(2)));
    }

    @GetMapping("/crearIngrediente")
    public void crearIngrediente(String nombreIngrediente, String descripcion, String link, double cantidadStock) {
        // ESTO CREA EL INGREDIENTE Y ESTABLECE EL STOCK DEL MISMO
        // Reemplazar argumentos como corresponde 
        ingredienteService.crearIngrediente("Harina", "Harina por Kg", "Link a la imagen de papas", 100);
    }

    // ----------------- PRUEBAS -------------------- Ignorar de momento o probar como prefieran
    @GetMapping("/pruebaCliente")
    public String prueba() {

        List<IngredienteStock> listaDeIngredientesSeleccionados = new ArrayList();
        List<IngredienteEnMenu> ingredientesDelMenu = new ArrayList();

        listaDeIngredientesSeleccionados.add(ingredienteService.buscarIngredientePorNombre("Papa"));
        listaDeIngredientesSeleccionados.add(ingredienteService.buscarIngredientePorNombre("Harina"));

        for (IngredienteStock iS : listaDeIngredientesSeleccionados) {
            ingredientesDelMenu.add(ingredienteService.ingredienteMenu(15.0, iS));
        }

        System.out.println(menuService.crearMenu("Ñoquis", "ñoquis de papa", 1000.0, ingredientesDelMenu, "Link a la imagen del menu papas fritas"));

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
     @GetMapping("/listar-menus")
        public List<Menu>  getMenus(){
            return  menuService.listaMenus();
     }
     

    @GetMapping("/listar-contactos")
    public List<Contacto> getContactos() {
        return contactoService.listarContactos();
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
}
