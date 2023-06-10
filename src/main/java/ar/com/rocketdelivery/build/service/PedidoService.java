package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.Dao.*;
import ar.com.rocketdelivery.build.domain.inventario.*;
import ar.com.rocketdelivery.build.domain.reportes.*;
import jakarta.transaction.Transactional;
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
        return pedidoDao.findAll();
    }

    public List<Pedido> buscarPorEstado(Estado estado) {
        return pedidoDao.findByEstado(estado);
    }

    public Pedido buscarPorId(long id) {
        return pedidoDao.findById(id).get();
    }

    public void eliminarPedido(Pedido pedido) {
        pedidoDao.delete(pedido);
    }

    public Long crearPedido(List<Menu> menus, Contacto contacto) {
        Pedido p = pedidoDao.save(new Pedido(estadoDao.findById(Long.valueOf(1)).get(), menus, contacto)); // CREA UN NUEVO PEDIDO CON ESTADO "NUEVO"
        return p.getIdPedido();
    }

    @Transactional
    public void establecerEnProgreso(Pedido pedido, List<Menu> menus) {

        pedido.setEstado(estadoDao.findById(Long.valueOf(2)).get());

        // Recorre La lista de menus del pedido
        for (Menu m : menus) {
            List<IngredienteEnMenu> ingredientes = m.getIngredientesEnMenu();

            // Por cada Menu Recorre el ingrediente y resta la cantidad al stock
            for (IngredienteEnMenu iM : ingredientes) {

                IngredienteStock iStock = stockDao.findByNombreIngrediente(iM.getIngredienteEnStock().getNombreIngrediente());

                if (iM.getCantidad() < iStock.getCantidadStock()) {
                    iStock.setCantidadStock(iStock.getCantidadStock() - iM.getCantidad());
                    stockDao.save(iStock);
                }
            }
        }
        pedidoDao.save(pedido);

    }

    @Transactional
    public void establecerCancelado(Pedido pedido, List<Menu> menus) {
        if (!pedido.getEstado().getNombreEstado().equals("LISTO PARA ENTREGAR")) {
            pedido.setEstado(estadoDao.findById(Long.valueOf(6)).get());

            // Recorre La lista de menus del pedido
            for (Menu m : menus) {
                List<IngredienteEnMenu> ingredientes = m.getIngredientesEnMenu();

                // Por cada Menu Recorre el ingrediente y resta la cantidad al stock
                for (IngredienteEnMenu iM : ingredientes) {
                    IngredienteStock iStock = stockDao.findByNombreIngrediente(iM.getIngredienteEnStock().getNombreIngrediente());
                    iStock.setCantidadStock(iStock.getCantidadStock() + iM.getCantidad());
                    stockDao.save(iStock);
                }
            }
            pedidoDao.save(pedido);
        }
    }

    public void establecerListoParaEntregar(Pedido pedido) {
        if (!pedido.getEstado().getNombreEstado().equals("CANCELADO")) {
            pedido.setEstado(estadoDao.findById(Long.valueOf(3)).get());
        }
    }

    public void establecerEntregado(Pedido pedido) {
        if (pedido.getEstado().getNombreEstado().equals("LISTO PARA ENTREGAR")) {
            pedido.setEstado(estadoDao.findById(Long.valueOf(4)).get());
        }
    }

    public void establecerNoEntregado(Pedido pedido) {
        if (!pedido.getEstado().getNombreEstado().equals("LISTO PARA ENTREGAR")) {
            pedido.setEstado(estadoDao.findById(Long.valueOf(5)).get());
        }
    }
}
