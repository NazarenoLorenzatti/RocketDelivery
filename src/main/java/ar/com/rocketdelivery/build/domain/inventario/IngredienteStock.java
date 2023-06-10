package ar.com.rocketdelivery.build.domain.inventario;

import jakarta.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "ingredientes_en_stock")
public class IngredienteStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_ingredientes_en_stock")
    private Long idIngredienteStock;


    @Column(name = "cantidad_stock")
    private double cantidadStock;

    @NotEmpty
    @Column(name = "nombre_ingrediente")
    private String nombreIngrediente;

    @NotEmpty
    @Column(name = "descripcion_ingrediente")
    private String descripcionIngrediente;

    @Column(name = "imagen_ingrediente")
    private String imagenIngrediente;

    public IngredienteStock(double cantidadStock, String nombreIngrediente, String descripcion_ingrediente, String imagenIngrediente) {
        this.cantidadStock = cantidadStock;
        this.nombreIngrediente = nombreIngrediente;
        this.descripcionIngrediente = descripcion_ingrediente;
        this.imagenIngrediente = imagenIngrediente;
    }



    public IngredienteStock() {
    }

}
