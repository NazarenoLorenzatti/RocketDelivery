package ar.com.rocketdelivery.build.domain.reportes;

import ar.com.rocketdelivery.build.domain.inventario.Menu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "menus_has_pedidos",
            joinColumns = @JoinColumn(name = "pedidos_id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "menus_id_menu")
    )
    private List<Menu> menus = new ArrayList();
        
    
    @ManyToOne
    @JoinColumn(name = "contacto_id_contacto")
    private Contacto contacto;

    @ManyToOne
    @JoinColumn(name = "estados_id_estado")
    private Estado estado;

    public Pedido(Estado estado, List<Menu> menus, Contacto contacto) {
        this.menus = menus;
        this.estado = estado;
        this.contacto = contacto;
    }

    public Pedido() {
    }
    
    

}
