package com.freemind.freemind;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

import com.freemind.freemind.model.Actividad;
import com.freemind.freemind.model.Estado;
import com.freemind.freemind.model.Institucion;
import com.freemind.freemind.model.TipoActividad;
import com.freemind.freemind.model.TipoUsuario;
import com.freemind.freemind.model.Usuario;

import com.freemind.freemind.repository.ActividadRepository;
import com.freemind.freemind.repository.EstadoRepository;
import com.freemind.freemind.repository.InstitucionRepository;
import com.freemind.freemind.repository.TipoActividadRepository;
import com.freemind.freemind.repository.TipoUsuarioRepository;
import com.freemind.freemind.repository.UsuarioRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private TipoActividadRepository tipoActividadRepository;

    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            TipoUsuario tipoUsuario = new TipoUsuario();
            tipoUsuario.setId(i + 1);
            tipoUsuario.setNombre(faker.university().name());
            tipoUsuarioRepository.save(tipoUsuario);
        }

        List<TipoUsuario> tipoUsuarios = tipoUsuarioRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setId(i + 1);
            usuario.setRun(faker.idNumber().valid());
            usuario.setNombres(faker.name().fullName());
            usuario.setApellidos(faker.name().fullName());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setTelefono(String.valueOf(faker.number().numberBetween(100000000, 900000000)));
            usuario.setTipoUsuario(tipoUsuarios.get(random.nextInt(tipoUsuarios.size())));
            usuarioRepository.save(usuario);
        }

        for (int i = 0; i < 10; i++) {
            TipoActividad tipoActividad = new TipoActividad();
            tipoActividad.setId(i + 1);
            tipoActividad.setNombre(faker.university().name());
            tipoActividadRepository.save(tipoActividad);
        }

        for (int i = 0; i < 10; i++) {
            Institucion institucion = new Institucion();
            institucion.setId(i + 1);
            institucion.setNombre(faker.university().name());
            institucion.setDireccion(faker.address().fullAddress());
            institucionRepository.save(institucion);
        }

        for (int i = 0; i < 3; i++) {
            Estado estado = new Estado();
            estado.setId(i + 1);
            estado.setNombre(faker.university().name());
            estadoRepository.save(estado);
        }

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<TipoActividad> tipoActividades = tipoActividadRepository.findAll();
        List<Estado> estados = estadoRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Actividad actividad = new Actividad();
            actividad.setId(i + 1);
            actividad.setNombre(faker.university().name());
            actividad.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            actividad.setTipoActividad(tipoActividades.get(random.nextInt(tipoActividades.size())));
            actividad.setEstado(estados.get(random.nextInt(estados.size())));
            actividadRepository.save(actividad);
        }
    }
}
