package com.sigo.controller;


import com.sigo.model.Ocorrencia;
import com.sigo.model.Usuario;
import com.sigo.service.OcorrenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @PostMapping
    public Ocorrencia cadastrar(@RequestBody Ocorrencia ocorrencia) {
        return ocorrenciaService.criarOcorrencia(ocorrencia);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Ocorrencia> criarComFoto(
            @RequestPart("ocorrencia") Ocorrencia ocorrencia,
            @RequestPart("file") MultipartFile file
    ) {
        Ocorrencia salva = ocorrenciaService.criarComFoto(ocorrencia, file);
        return ResponseEntity.ok(salva);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> editarOcorrencia(
            @PathVariable Long id,
            @RequestBody Ocorrencia dadosAtualizados,
            @AuthenticationPrincipal Usuario authUser) {
        Ocorrencia atualizado = ocorrenciaService.editarOcorrencia(id, dadosAtualizados, authUser);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public List<Ocorrencia> buscarTodas() {
        return  ocorrenciaService.buscarTodas();
    }

    @GetMapping("/{id}")
    public Ocorrencia buscarOcorrencia(@PathVariable Long id) {
        return ocorrenciaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Ocorrencia> excluirOcorrencia (@PathVariable Long id,  @AuthenticationPrincipal Usuario usuario) {
        ocorrenciaService.excluirOcorrencia(id, usuario);
        return ResponseEntity.noContent().build();
    }

}
