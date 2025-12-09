package com.sigo.controller;

import com.sigo.model.Usuario;
import com.sigo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        Usuario salvo = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @GetMapping("/{nome}")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario buscarUsuario (@PathVariable String nome) {
        return  usuarioService.buscarUsuario(nome);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario editar (@PathVariable Long id, @RequestBody Usuario usuario) {
        return  usuarioService.editarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario>  excluirUsuario (@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }


}




