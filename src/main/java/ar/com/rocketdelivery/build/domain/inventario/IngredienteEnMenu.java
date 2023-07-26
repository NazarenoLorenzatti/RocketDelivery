package ar.com.rocketdelivery.build.domain.inventario;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name = "ingredientes_en_menu")
public class IngredienteEnMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrediente_menu")
    private Long idIngrediente;
    
    @OneToOne
    @JoinColumn(name = "ingredientes_en_stock_id")
    private IngredienteStock ingredienteEnStock;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menus_id_menu")
    private Menu menu;

    private double cantidad;

    public IngredienteEnMenu() {
    }

}
