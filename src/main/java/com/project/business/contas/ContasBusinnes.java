package com.project.business.contas;

import com.project.entity.Conta;
import com.project.repository.UsuarioRepository;
import com.project.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ContasBusinnes {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ResponseEntity<String> saveAccount(Conta conta) {
        ExampleMatcher filtro = ExampleMatcher.matching().withMatcher("pessoa.idPessoa", new ExampleMatcher.GenericPropertyMatcher().exact());

        if (!usuarioRepository.exists(Example.of(conta, filtro))) {
            usuarioRepository.save(conta);
            return new MessageUtils()
                    .messagesResponseEntity("Conta Criada Com Sucesso", HttpStatus.CREATED);
        }
        return new MessageUtils()
                .messagesResponseEntity("Essa Pessoa Já Possui uma conta", HttpStatus.UNAUTHORIZED);
    }

}
