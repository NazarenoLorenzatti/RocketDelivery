package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.Dao.iContactoDao;
import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class ContactoService {
    
    @Autowired
    private iContactoDao contactoDao;
    
    public List<Contacto> listarContactos(){
        return contactoDao.findAll();
    }
    
    public Contacto buscarPorId(String id){
        return contactoDao.findById(Long.parseLong(id)).get();
    }
    
    public Contacto buscarPorNombre(String nombre){
        return contactoDao.findByNombre(nombre);
    }
    
        public Contacto buscarPorUsuario(Usuario usuario){
        return contactoDao.findByUsuario(usuario);
    }
    
    public void crearContacto(String nombre, String apellido, String email, String telefono, String direccion, Usuario usuario){
        
        if (contactoDao.findByUsuario(usuario) == null){
            contactoDao.save(new Contacto(nombre, apellido, email, telefono, direccion, usuario));
        
        }
    }
    
    public void eliminarContacto(Contacto contacto){
        contactoDao.delete(contacto);
    }
    
    public void actualizarContacto(Contacto contactoId, String nombre, String apellido, String email, String telefono, String direccion){
        Optional<Contacto> c = contactoDao.findById(contactoId.getIdContacto());
        Contacto contacto = c.get();
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setEmail(email);
        contacto.setTelefono(telefono);
        contacto.setDireccion(direccion);
    }
}
