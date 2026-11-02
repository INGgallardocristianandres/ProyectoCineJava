package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testIniciarSesionCredencialesCorrectas() {
        Usuario usuario = new Cliente("U1", "Carlos", "carlos@mail.com", "secret123", "3000000");
        assertTrue(usuario.iniciarSesion("carlos@mail.com", "secret123"), "Debe permitir iniciar sesión con credenciales correctas");
    }

    @Test
    void testIniciarSesionCredencialesIncorrectas() {
        Usuario usuario = new Cliente("U1", "Carlos", "carlos@mail.com", "secret123", "3000000");
        assertFalse(usuario.iniciarSesion("carlos@mail.com", "wrongpass"), "Debe denegar inicio de sesión con contraseña incorrecta");
        assertFalse(usuario.iniciarSesion("wrong@mail.com", "secret123"), "Debe denegar inicio de sesión con correo incorrecto");
    }
}