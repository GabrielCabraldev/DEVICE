package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransacaoDTO {

    private Integer idTransacao;

    private BigDecimal valor;

    private BigDecimal valorSaque;

    private BigDecimal valorDeposito;


}
