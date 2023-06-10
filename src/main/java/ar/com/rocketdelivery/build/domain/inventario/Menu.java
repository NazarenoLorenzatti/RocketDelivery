package ar.com.rocketdelivery.build.domain.inventario;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "menus")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMenu;

    @NotEmpty
    @Column(name = "nombre_menu")
    private String nombreMenu;

    @NotEmpty
    private String descripcion_menu;

    @NotNull
    private Double precio;

    private String imagen_menu;

    private boolean disponible;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menus_id_menu")
    private List<IngredienteEnMenu> ingredientesEnMenu = new ArrayList();


    public Menu() {
    }

    public Menu(String nombreMenu, String descripcion_menu, Double precio, String imagen_menu) {
        this.nombreMenu = nombreMenu;
        this.descripcion_menu = descripcion_menu;
        this.precio = precio;
        this.imagen_menu = imagen_menu;
        this.disponible = true;
    }

}
