package ar.com.rocketdelivery.build.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

import ar.com.rocketdelivery.build.domain.inventario.*;
import ar.com.rocketdelivery.build.domain.reportes.*;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.service.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public void editarContact(@RequestBody Contacto contacto) throws Exception {
        contactoService.actualizarContacto(contacto);
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

    @PostMapping("/crearIngrediente")
    public void crearIngrediente(@RequestBody IngredienteStock ingredienteEnStock) {
        ingredienteService.crearIngredienteStock(ingredienteEnStock.getNombreIngrediente(),
                ingredienteEnStock.getDescripcionIngrediente(), ingredienteEnStock.getImagenIngrediente(),
                ingredienteEnStock.getCantidadStock());
        actualizarStock();
    }

    @PostMapping("/editarIngrediente")
    public void editarIngrediente(@RequestBody IngredienteStock ingredienteEnStock) {
        ingredienteService.actualizarIngredienteStock(ingredienteEnStock.getIdIngredienteStock(),
                ingredienteEnStock.getNombreIngrediente(), ingredienteEnStock.getDescripcionIngrediente(),
                ingredienteEnStock.getImagenIngrediente());
        actualizarStock();
    }

    @PostMapping("/actualizar-cantidad")
    public void actualizarCantidad(@RequestBody IngredienteStock ingredienteEnStock, double cantidad) {
        ingredienteService.actualizarCantidadStock(ingredienteEnStock.getIdIngredienteStock(), cantidad);
    }

    @PostMapping("/crear-menu")
    public void crearMenu(@RequestBody Menu menu, String[][] ingredientesSeleccionados) {
        menuService.crearMenu(menu.getNombreMenu(), menu.getDescripcion_menu(), menu.getPrecio(), ingredientesSeleccionados, menu.getImagen_menu());
        actualizarStock();
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
    public void crearPedido(List<Menu> menus, @RequestBody Contacto contacto) {
        pedidoService.crearPedido(menus, contacto);
        actualizarStock();
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
    
    //--------------------------------------------------------------------------
    
    // ESTADOS DEL PEDIDO
    @GetMapping("/en-progreso")
    public void establecerEnProgreso(@RequestBody Pedido pedido) {
        pedidoService.establecerEnProgreso(pedido);
    }

    @GetMapping("/entregado")
    public void establecerEntregado(@RequestBody Pedido pedido) {
        pedidoService.establecerEntregado(pedido);
    }

    @GetMapping("/cancelado")
    public void establecerCancelado(@RequestBody Pedido pedido) {
        pedidoService.establecerCancelado(pedido);
    }

    @GetMapping("/listo-para-entrega")
    public void listoParaEntregar(@RequestBody Pedido pedido) {
        pedidoService.establecerListoParaEntregar(pedido);
    }

    
    //--------------------------------------------------------------------------
    
    
    // IMPRESION DE REPORTES
    @GetMapping("/descargar-reporte")
    public ResponseEntity<byte[]> descargarReporte() {

        ByteArrayOutputStream outputStream = pedidoService.reportePedidos();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file-name.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }

    @GetMapping("/descargar-cartera")
    public ResponseEntity<byte[]> descargarCartera() {

        ByteArrayOutputStream outputStream = contactoService.reporteClientes();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file-name.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }
}
