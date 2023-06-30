package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.domain.Json.JwtResponse;
import ar.com.rocketdelivery.build.security.JwtUtil;
import ar.com.rocketdelivery.build.security.UserDetailsServiceImp;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Data
public class LoginService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDetailsServiceImp userDetails;
    
    @Autowired
    private JwtUtil jwtUtil;

    public LoginService() {
    }

    public JwtResponse login(String userName, String password) {

        try {
            UserDetails userDetails = this.userDetails.loadUserByUsername(userName);

            if (this.passwordEncoder.matches(password, userDetails.getPassword())) {
                return new JwtResponse(this.jwtUtil.encode(userDetails.getUsername()));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o password incorrecto");
            }

        } catch (UsernameNotFoundException unnfe) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o password incorrecto");
        }
    }

}
