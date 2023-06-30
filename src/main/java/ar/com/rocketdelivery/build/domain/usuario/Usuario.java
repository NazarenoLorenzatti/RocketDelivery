package ar.com.rocketdelivery.build.domain.usuario;

import ar.com.rocketdelivery.build.security.Authority;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @OneToMany
    @JoinColumn(name = "usuario_id_usuario")
    private List<Rol> roles;

    public Usuario(String username, String password, List<Rol> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Usuario() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        this.roles.forEach(rol -> {
            autoridades.add(new Authority(rol.getNombre_rol()));
        });
        return autoridades;
    }
    
      @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
    
}
