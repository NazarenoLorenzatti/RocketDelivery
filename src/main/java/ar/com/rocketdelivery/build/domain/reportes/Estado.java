package ar.com.rocketdelivery.build.domain.reportes;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "estados")
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    @NotEmpty
    @Column(name = "nombre_estado")
    private String nombreEstado;

//    @OneToMany
//    @JoinColumn(name = "estados_id_estado")
//    private List<Pedido> pedidos;
}
