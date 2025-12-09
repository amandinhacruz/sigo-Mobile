package com.sigo.service;


import com.sigo.model.Ocorrencia;
import com.sigo.model.Perfil;
import com.sigo.model.Usuario;
import com.sigo.repository.OcorrenciaRepository;
import com.sigo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;

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


    public Ocorrencia editarOcorrencia (Long id, Ocorrencia dadosAtualizados) {
        Optional<Ocorrencia> optional = ocorrenciaRepository.findById(id);

        if(optional.isPresent()) {
            Ocorrencia ocorrencia = optional.get();
            ocorrencia.setViatura(dadosAtualizados.getViatura());
            ocorrencia.setRoles(dadosAtualizados.getRoles());
            ocorrencia.setGrupamento(dadosAtualizados.getGrupamento());
            ocorrencia.setNumeroVitimas(dadosAtualizados.getNumeroVitimas());
            ocorrencia.setSituacaoFinal(dadosAtualizados.getSituacaoFinal());
            ocorrencia.setRecursosUtilizados(dadosAtualizados.getRecursosUtilizados());
            ocorrencia.setEnderecoOcorrencia(dadosAtualizados.getEnderecoOcorrencia());
            ocorrencia.setDescricao(dadosAtualizados.getDescricao());
            ocorrencia.setNome(dadosAtualizados.getNome());
            ocorrencia.setCodigoIdentificacao(dadosAtualizados.getCodigoIdentificacao());
            ocorrencia.setCpf(dadosAtualizados.getCpf());
            ocorrencia.setTelefone(dadosAtualizados.getTelefone());
            ocorrencia.setFoto(dadosAtualizados.getFoto());

            return  ocorrenciaRepository.save(ocorrencia);
        } else  {
            throw  new RuntimeException("Ocorrência não encontrada com id: " + id);
        }
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
