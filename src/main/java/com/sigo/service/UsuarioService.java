package com.sigo.service;

import com.sigo.model.Perfil;
import com.sigo.model.Usuario;
import com.sigo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(String nome) {
        return usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public Usuario editarUsuario(Long id, Usuario dadosAtualizados) {
        Optional<Usuario> optional = usuarioRepository.findById(id);

        if (optional.isEmpty()) {
            Usuario usuario = optional.get();
            usuario.setNome(dadosAtualizados.getNome());
            usuario.setEmail(dadosAtualizados.getEmail());
            usuario.setSenha(dadosAtualizados.getSenha());
            usuario.setRoles(dadosAtualizados.getRoles());
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }

    public void deletarUsuario (Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (usuario.getRoles().contains(Perfil.ADMIN)) {
            usuarioRepository.delete(usuario);
        }
    }
}


