package com.sigo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sigo.model.Foto;
import com.sigo.model.Ocorrencia;
import com.sigo.repository.FotoRepository;
import com.sigo.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FotoService {

    private final FotoRepository fotoRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final Cloudinary cloudinary;

    public FotoService(FotoRepository fotoRepository,
                       OcorrenciaRepository ocorrenciaRepository,
                       Cloudinary cloudinary) {
        this.fotoRepository = fotoRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.cloudinary = cloudinary;
    }

    public void uploadFoto(Long ocorrenciaId, MultipartFile file) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(ocorrenciaId)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            String url = (String) uploadResult.get("secure_url");

            Foto foto = new Foto();
            foto.setNomeArquivo(file.getOriginalFilename());
            foto.setUrl(url);
            foto.setOcorrencia(ocorrencia);

            fotoRepository.save(foto);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar a imagem");
        }
    }
}

