package ar.com.rocketdelivery.build.domain.reportes;

import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "contacto")
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContacto;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellido;

    @NotEmpty
    private String email;

    @NotEmpty
    private String telefono;

    @NotEmpty
    private String direccion;

    @OneToOne
    @JoinColumn(name = "usuario_id_usuario")
    private Usuario usuario;

//    @OneToMany
//    @JoinColumn(name = "contacto_id_contacto")
//    private List<Pedido> pedidos;
    public Contacto(String nombre, String apellido, String email, String telefono, String direccion, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuario = usuario;
    }

    public Contacto() {
    }

}
