package com.sigo.service;


import com.sigo.model.Ocorrencia;
import com.sigo.model.Perfil;
import com.sigo.model.Usuario;
import com.sigo.repository.OcorrenciaRepository;
import com.sigo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FotoService fotoService;

    public Ocorrencia criarOcorrencia (Ocorrencia ocorrencia) {
        ocorrencia.setDataRegistro(LocalDateTime.now());
        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia criarComFoto(Ocorrencia ocorrencia, MultipartFile file) {
        ocorrencia.setDataRegistro(LocalDateTime.now());

        Ocorrencia salva = ocorrenciaRepository.save(ocorrencia);

        fotoService.uploadFoto(salva.getId(), file);

        return salva;
    }


    public Ocorrencia editarOcorrencia(Long id, Ocorrencia dadosAtualizados, Usuario authUser) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada com id: " + id));

        boolean isOwner = ocorrencia.getUsuario() != null && ocorrencia.getUsuario().getId().equals(authUser.getId());

        boolean isAdmin = false;
        if (authUser.getRoles() != null) {
            isAdmin = authUser.getRoles().stream()
                    .map(Object::toString)
                    .anyMatch(r -> r.equalsIgnoreCase("ROLE_ADMIN") || r.equalsIgnoreCase("ADMIN"));
        }

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException( HttpStatus.FORBIDDEN, "Usuário não autorizado a editar esta ocorrência");
        }


        if (dadosAtualizados.getViatura() != null) {
            ocorrencia.setViatura(dadosAtualizados.getViatura());
        }
        if (dadosAtualizados.getRoles() != null) {
            ocorrencia.setRoles(dadosAtualizados.getRoles());
        }
        if (dadosAtualizados.getGrupamento() != null) {
            ocorrencia.setGrupamento(dadosAtualizados.getGrupamento());
        }
        if (dadosAtualizados.getLocal() != null) {
            ocorrencia.setLocal(dadosAtualizados.getLocal());
        }

        ocorrencia.setNumeroVitimas(dadosAtualizados.getNumeroVitimas());

        if (dadosAtualizados.getSituacaoFinal() != null) {
            ocorrencia.setSituacaoFinal(dadosAtualizados.getSituacaoFinal());
        }
        if (dadosAtualizados.getRecursosUtilizados() != null) {
            ocorrencia.setRecursosUtilizados(dadosAtualizados.getRecursosUtilizados());
        }
        if (dadosAtualizados.getEnderecoOcorrencia() != null) {
            ocorrencia.setEnderecoOcorrencia(dadosAtualizados.getEnderecoOcorrencia());
        }
        if (dadosAtualizados.getDescricao() != null) {
            ocorrencia.setDescricao(dadosAtualizados.getDescricao());
        }
        if (dadosAtualizados.getNome() != null) {
            ocorrencia.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getCodigoIdentificacao() != null) {
            ocorrencia.setCodigoIdentificacao(dadosAtualizados.getCodigoIdentificacao());
        }
        if (dadosAtualizados.getCpf() != null) {
            ocorrencia.setCpf(dadosAtualizados.getCpf());
        }
        if (dadosAtualizados.getTelefone() != null) {
            ocorrencia.setTelefone(dadosAtualizados.getTelefone());
        }
        if (dadosAtualizados.getFotos() != null) {
            ocorrencia.getFotos().clear();
            ocorrencia.getFotos().addAll(dadosAtualizados.getFotos());
        }

        return ocorrenciaRepository.save(ocorrencia);
    }

    public  Ocorrencia buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada com id: " + id));
    }

    public List<Ocorrencia> buscarTodas() {
        return ocorrenciaRepository.findAll();
    }

    public void excluirOcorrencia (Long id,  @AuthenticationPrincipal Usuario usuario) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada."));
            if (!usuario.getRoles().contains(Perfil.OPERADOR) ) {
                ocorrenciaRepository.delete(ocorrencia);
            }
    }

}
