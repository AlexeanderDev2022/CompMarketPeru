package com.autodoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autodoc.entity.Usuario;
import com.autodoc.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        // Si es un usuario nuevo o se escribió una contraseña en el formulario, se cifra con BCrypt.
        if (usuario.getPasswordHash() != null && !usuario.getPasswordHash().isBlank()
                && !usuario.getPasswordHash().startsWith("$2a$")) {
            usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        } else if (usuario.getId_user() != null
                && (usuario.getPasswordHash() == null || usuario.getPasswordHash().isBlank())) {
            // Edición sin tocar la contraseña: se conserva el hash existente.
            Usuario actual = repo.findById(usuario.getId_user()).orElse(null);
            if (actual != null) {
                usuario.setPasswordHash(actual.getPasswordHash());
            }
        }
        if (usuario.getEstado() == null) {
            usuario.setEstado(1);
        }
        return repo.save(usuario);
    }

    @Override
    public Usuario buscar(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}