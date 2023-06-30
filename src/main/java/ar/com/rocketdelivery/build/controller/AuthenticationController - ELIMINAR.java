//package ar.com.rocketdelivery.build.controller;
//
//import ar.com.rocketdelivery.build.config.JwtUtils;
//import ar.com.rocketdelivery.build.domain.Json.*;
//import ar.com.rocketdelivery.build.domain.usuario.Usuario;
//import ar.com.rocketdelivery.build.security.UserDetailsServiceImp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//
//@RestController
//@CrossOrigin("*")
//public class AuthenticationController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsServiceImp userDetailsService;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @PostMapping("/generate-token")
//    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
//        try{
//            autenticar(jwtRequest.getUsername(),jwtRequest.getPassword());
//        }catch (Exception exception){
//            exception.printStackTrace();
//            throw new Exception("Usuario no encontrado");
//        }
//
//        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
//        String token = this.jwtUtils.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    private void autenticar(String username,String password) throws Exception {
//        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
//        }catch (DisabledException exception){
//            throw  new Exception("USUARIO DESHABILITADO " + exception.getMessage());
//        }catch (BadCredentialsException e){
//            throw  new Exception("Credenciales invalidas " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/actual-usuario")
//    public Usuario obtenerUsuarioActual(Principal principal){
//        return (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());
//    }
//}
