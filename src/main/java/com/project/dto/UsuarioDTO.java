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
public class UsuarioDTO {

    private Integer idConta;

    private Integer idPessoa;

    private BigDecimal saldo;

    private BigDecimal limiteSaqueDiario;

    private Integer tipoConta;

}
