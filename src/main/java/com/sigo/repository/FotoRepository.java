package com.sigo.repository;

import com.sigo.model.Foto;
import com.sigo.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long> {
}
