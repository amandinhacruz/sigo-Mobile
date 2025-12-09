package com.sigo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ocorrencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private LocalDateTime dataRegistro;
    private String viatura;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<TipoOcorrencia> roles;

    private String grupamento;
    private String local;
    private int numeroVitimas;
    private String situacaoFinal;
    private String recursosUtilizados;
    private String enderecoOcorrencia;
    private String descricao;
    private String nome;
    private String codigoIdentificacao;
    private String cpf;
    private String telefone;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
