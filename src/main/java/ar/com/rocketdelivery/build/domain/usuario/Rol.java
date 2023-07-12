package ar.com.rocketdelivery.build.domain.usuario;

import jakarta.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table(name = "rol")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

   @NotEmpty
    @Column(name = "nombre_rol")
    private String nombreRol;


}
