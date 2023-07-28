package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.Dao.*;
import ar.com.rocketdelivery.build.domain.inventario.*;
import ar.com.rocketdelivery.build.domain.reportes.*;
import ar.com.rocketdelivery.build.util.EscritorXLS;
import jakarta.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class PedidoService {

    @Autowired
    private iPedidoDao pedidoDao;

    @Autowired
    private iEstadoDao estadoDao;

    @Autowired
    private iIngredienteStockDao stockDao;

    @Autowired
    private iMenuDao menuDao;

    @Autowired
    private iIngredientesEnMenuDao ingredientesDao;

    public List<Pedido> listarPedidos() {
        return pedidoDao.findAllByOrderByIdPedidoDesc();
    }

    public List<Pedido> buscarPorEstado(String estado) {
        return pedidoDao.findByEstado(estadoDao.findByNombreEstado(estado));
    }

    public Pedido buscarPorId(long id) {
        return pedidoDao.findById(id).get();
    }

    public void eliminarPedido(Pedido pedido) {
        pedidoDao.delete(pedido);
    }

    public Long crearPedido(List<Menu> menus, Contacto contacto) {
        List<Menu> m = new ArrayList();
        for (Menu maux : menus) {
            m.add(menuDao.findById(maux.getIdMenu()).get());
        }
        Pedido p = pedidoDao.save(new Pedido(estadoDao.findByNombreEstado("NUEVO"), m, contacto)); // CREA UN NUEVO PEDIDO CON ESTADO "NUEVO"
        // Recorre La lista de menus del pedido
        for (Menu menu : p.getMenus()) {
            List<IngredienteEnMenu> ingredientes = menu.getIngredientesEnMenu();
            // Por cada Menu Recorre el ingrediente y resta la cantidad al stock
            for (IngredienteEnMenu iM : ingredientes) {
                IngredienteStock iStock = stockDao.findByNombreIngrediente(iM.getIngredienteEnStock().getNombreIngrediente());
                if (iM.getCantidad() < iStock.getCantidadStock()) {
                    iStock.setCantidadStock(iStock.getCantidadStock() - iM.getCantidad());
                    stockDao.save(iStock);
                }
            }
        }
        return p.getIdPedido();
    }

    @Transactional
    public void establecerEnProgreso(Long id) {
        Pedido pedido = pedidoDao.findById(id).get();
        pedido.setEstado(estadoDao.findByNombreEstado("EN PROGRESO"));
        pedidoDao.save(pedido);
    }

    @Transactional
    public void establecerCancelado(Long id) {
        Pedido pedido = pedidoDao.findById(id).get();
        
        if (!pedido.getEstado().getNombreEstado().equals("LISTO PARA ENTREGAR")) {
            pedido.setEstado(estadoDao.findByNombreEstado("CANCELADO"));

            // Recorre La lista de menus del pedido
            for (Menu m : pedido.getMenus()) {
                List<IngredienteEnMenu> ingredientes = m.getIngredientesEnMenu();

                // Por cada Menu Recorre el ingrediente y vuelve a sumar la cantidad al stock 
                for (IngredienteEnMenu iM : ingredientes) {
                    IngredienteStock iStock = stockDao.findByNombreIngrediente(iM.getIngredienteEnStock().getNombreIngrediente());
                    iStock.setCantidadStock(iStock.getCantidadStock() + iM.getCantidad());
                    stockDao.save(iStock);
                }
            }
            pedidoDao.save(pedido);
        }
    }

    public void establecerListoParaEntregar(Long id) {
        Pedido pedido = pedidoDao.findById(id).get();
        if (!pedido.getEstado().getNombreEstado().equals("CANCELADO")) {
            pedido.setEstado(estadoDao.findByNombreEstado("LISTO PARA ENTREGAR"));
            pedidoDao.save(pedido);
        }
    }

    public void establecerEntregado(Long id) {
        Pedido pedido = pedidoDao.findById(id).get();
        if (pedido.getEstado().getNombreEstado().equals("LISTO PARA ENTREGAR")) {
            pedido.setEstado(estadoDao.findByNombreEstado("ENTREGADO"));
            pedidoDao.save(pedido);
        }
    }

    public void establecerNoEntregado(Long id) {
        Pedido pedido = pedidoDao.findById(id).get();
        if (!pedido.getEstado().getNombreEstado().equals("LISTO PARA ENTREGAR")) {
            pedido.setEstado(estadoDao.findByNombreEstado("NO ENTREGADO"));
            pedidoDao.save(pedido);
        }
    }

    public ByteArrayOutputStream reportePedidos() {

        List<List<String>> filas = new ArrayList();
        List<String> cabeceros = Arrays.asList("ID del Pedido", "Cliente", "Estado", "Monto del Pedido",
                "Menus del Pedido");

        for (Pedido p : pedidoDao.findAll()) {
            double monto = 0.0;
            List<String> fila = new ArrayList();
            fila.add(p.getIdPedido().toString());
            fila.add(p.getContacto().getNombre() + p.getContacto().getApellido());
            fila.add(p.getEstado().getNombreEstado());

            for (Menu m : p.getMenus()) {
                monto += m.getPrecio();
            }
            fila.add(String.valueOf(monto));

            for (Menu m : p.getMenus()) {
                fila.add(m.getNombreMenu());
            }
            filas.add(fila);
        }

        var escritorXLS = new EscritorXLS(filas, cabeceros);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            escritorXLS.exportar().write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}

