package com.project.business.Transactions;

import com.project.dto.FlagDTO;
import com.project.dto.SaldoDTO;
import com.project.dto.TransacaoDTO;
import com.project.entity.Conta;
import com.project.entity.Transacao;
import com.project.repository.TransacaoRepository;
import com.project.repository.UsuarioRepository;
import com.project.util.error.ContaInativaException;
import com.project.util.error.ContaInvalidaException;
import com.project.util.error.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class TransacaoBusinnes {

    /**
     * Classe para objetos do tipo Contas, onde serão contidos, valores e métodos para o mesmo.
     *
     * @author Gabriel Cabral
     * @version 1.0
     */

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TransacaoRepository transacaoRepository;

    /**
     * Método para realizar saque
     *
     * @return -  "idConta", saldo, "flagAtivo", "dataCadastro"
     */
    public Conta saque(Integer idConta, TransacaoDTO transacaoDTO) throws RuntimeException {
        Optional<Conta> usuarioOperacao = usuarioRepository.findById(idConta);
        if (usuarioOperacao.isPresent() && usuarioOperacao.get().isFlag()) {
            BigDecimal saldo = usuarioOperacao.get().getSaldo();
            if (saldo.compareTo(transacaoDTO.getValorSaque()) >= 0) {
                usuarioOperacao.get().setSaldo(saldo.subtract(transacaoDTO.getValorSaque()));
                transacaoRepository.save(Transacao.builder()
                        .conta(usuarioOperacao.get())
                        .valor(transacaoDTO.getValorSaque())
                        .build());
                return usuarioRepository.save(usuarioOperacao.get());
            } else {
                throw new SaldoInsuficienteException("SALDO INSUFICIENTE");
            }
        } else {
            throw new ContaInvalidaException("CONTA INVALIDA OU BLOQUEADA");
        }
    }

    /**
     * Método para realizar deposito
     *
     * @return -  "idConta", saldo, "flagAtivo", "dataCadastro"
     */
    public Conta deposito(Integer idConta, TransacaoDTO transacaoDTO) throws RuntimeException {
        Optional<Conta> usuarioOperacaoDeposito = usuarioRepository.findById(idConta);
        if (usuarioOperacaoDeposito.isPresent() && usuarioOperacaoDeposito.get().isFlag()) {
            BigDecimal saldo = usuarioOperacaoDeposito.get().getSaldo();
            usuarioOperacaoDeposito.get().setSaldo(saldo.add(transacaoDTO.getValorDeposito()));
            transacaoRepository.save(Transacao.builder()
                    .conta(usuarioOperacaoDeposito.get())
                    .valor(transacaoDTO.getValorDeposito())
                    .build());
            return usuarioRepository.save(usuarioOperacaoDeposito.get());
        } else {
            throw new ContaInativaException("CONTA INATIVA OU BLOQUEADA");
        }
    }

    /**
     * Método consultar transacoes por IdConta
     *
     * @return -  "extrato transacoes"
     */
    public List<Transacao> consultaTransacao(String idConta) {
        ExampleMatcher filtro = ExampleMatcher.matching().withMatcher("conta.idConta", new ExampleMatcher.GenericPropertyMatcher().exact());
        Transacao transacao = Transacao.builder()
                .conta(Conta.builder()
                        .idConta(Integer.parseInt(idConta))
                        .flag(true)
                        .build())
                .build();
        Example<Transacao> transacaoExample = Example.of(transacao, filtro);

        if (transacaoRepository.exists(transacaoExample)) {
            return transacaoRepository.findAll(transacaoExample);
        }

        throw new ContaInvalidaException("Transações não encontradas");
    }

    /**
     * Método para consultar saldo da conta
     *
     * @return -  "saldo"
     */
    public SaldoDTO consultaSaldo(Integer idConta) {
        Optional<Conta> usuarioOpercaoSaldo = usuarioRepository.findById(idConta);
        if (usuarioOpercaoSaldo.isPresent()) {
            return SaldoDTO.builder().saldo(usuarioOpercaoSaldo.get().getSaldo())
                    .build();
        }
        throw new ContaInvalidaException("CONTA INVALIDA");
    }

    /**
     * Método para bloquear conta
     *
     * @return -  "flag"
     */
    public FlagDTO bloqueioConta(Integer idConta) {
        Optional<Conta> usuarioBloqueio = usuarioRepository.findById(idConta);
        if (usuarioBloqueio.get().isFlag()) {
            usuarioBloqueio.get().setFlag(false);
            usuarioRepository.save(usuarioBloqueio.get());
            return FlagDTO.builder()
                    .flag(false)
                    .messages("CONTA BLOQUEADA")
                    .build();
        } else {
            usuarioBloqueio.get().setFlag(true);
            usuarioRepository.save(usuarioBloqueio.get());
            return FlagDTO.builder()
                    .flag(true)
                    .messages("CONTA DESBLOQUEADA")
                    .build();
        }

    }
}

