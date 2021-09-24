package com.project.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Conta conta;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_transacao", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataTransacao;

    @PrePersist
    public void prePersistDate(){
        setDataTransacao(LocalDateTime.now());
    }

    }
