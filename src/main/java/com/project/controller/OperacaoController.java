package com.project.controller;

import com.project.business.Transactions.TransacaoBusinnes;
import com.project.business.contas.ContasBusinnes;
import com.project.dto.FlagDTO;
import com.project.dto.SaldoDTO;
import com.project.dto.TransacaoDTO;
import com.project.dto.UsuarioDTO;
import com.project.entity.Conta;
import com.project.entity.Transacao;
import com.project.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/operacoes")
public class OperacaoController {

    /**
     * Classe para acessar paths.
     *
     * @author Gabriel Cabral chupa pau
     * @version 1.0
     */


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    TransacaoBusinnes transacaoBO;


    @Autowired
    ContasBusinnes contasBusinnes;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> save(@RequestBody @Valid Conta contas) {
        return contasBusinnes.saveAccount(contas);

    }

    @PostMapping("/saque/{idConta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Conta saque(@RequestBody @Valid TransacaoDTO transacaoDTO, @PathVariable Integer idConta) {
        return transacaoBO.saque(idConta, transacaoDTO);
    }

    @GetMapping("/consulta/{idConta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SaldoDTO consultaSaldo(@Valid @PathVariable Integer idConta) {
        return transacaoBO.consultaSaldo(idConta);
    }

    @PostMapping("/deposito/{idConta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Conta depositar(@RequestBody @Valid TransacaoDTO transacaoDTO, @PathVariable Integer idConta) {
        return transacaoBO.deposito(idConta, transacaoDTO);
    }

    @GetMapping("/extrato/{idConta}")
    public List<Transacao> consultaTransacao(@PathVariable String idConta) {
        return transacaoBO.consultaTransacao(idConta);
    }

    @PutMapping("/bloqueiaConta/{idConta}")
    public FlagDTO bloqueiaConta(@PathVariable Integer idConta){
        return transacaoBO.bloqueioConta(idConta);
    }
}
