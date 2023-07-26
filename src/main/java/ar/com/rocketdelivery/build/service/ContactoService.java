package ar.com.rocketdelivery.build.service;

import ar.com.rocketdelivery.build.Dao.iContactoDao;
import ar.com.rocketdelivery.build.domain.reportes.Contacto;
import ar.com.rocketdelivery.build.domain.usuario.Usuario;
import ar.com.rocketdelivery.build.util.EscritorXLS;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Contacto> listarContactos() {
        return contactoDao.findAll();
    }

    public Contacto buscarPorId(Long id) {
        return contactoDao.findById(id).get();
    }

    public Contacto buscarPorNombre(String nombre) {
        return contactoDao.findByNombre(nombre);
    }

    public Contacto buscarPorUsuario(Usuario usuario) {
        return contactoDao.findByUsuario(usuario);
    }

    public Contacto buscarContactoByUsername(String username) {
        return contactoDao.findByUsuarioUsername(username);
    }

    public void crearContacto(String nombre, String apellido, String email, String telefono, String direccion, Usuario usuario) {

        if (contactoDao.findByUsuario(usuario) == null) {
            contactoDao.save(new Contacto(nombre, apellido, email, telefono, direccion, usuario));

        }
    }

    public void eliminarContacto(Contacto contacto) {
        contactoDao.delete(contacto);
    }

    public void actualizarContacto(Contacto contacto) {
        Optional<Contacto> cOpt = contactoDao.findById(contacto.getIdContacto());
        Contacto c = cOpt.get();
        contacto.setNombre(c.getNombre());
        contacto.setApellido(c.getApellido());
        contacto.setEmail(c.getEmail());
        contacto.setTelefono(c.getTelefono());
        contacto.setDireccion(c.getDireccion());
        contactoDao.save(c);
    }

    public ByteArrayOutputStream reporteClientes() {
        List<List<String>> filas = new ArrayList();
        List<String> cabeceros = Arrays.asList("ID", "Cliente", "Email", "Telefono",
                "Direccion");

        for (Contacto c : contactoDao.findAll()) {
            List<String> fila = new ArrayList();
            fila.add(c.getIdContacto().toString());
            fila.add(c.getNombre() + " " + c.getApellido());
            fila.add(c.getEmail());
            fila.add(c.getTelefono());
            fila.add(c.getDireccion());
            filas.add(fila);
        }

        var escritorXLS = new EscritorXLS(filas, cabeceros);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            escritorXLS.exportar().write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}
