package com.autodoc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autodoc.entity.Usuario;
import com.autodoc.repository.UsuarioRepository;

/**
 * Autentica contra la tabla USUARIOS, usando el rol asociado (ADMIN / VENDEDOR)
 * para construir las autoridades de Spring Security (ROLE_ADMIN / ROLE_VENDEDOR).
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));

        if (usuario.getEstado() == null || usuario.getEstado() == 0) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        String rol = usuario.getRol() != null ? usuario.getRol().getNombre_rol() : "VENDEDOR";

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPasswordHash())
                .authorities(new SimpleGrantedAuthority("ROLE_" + rol))
                .build();
    }
}
