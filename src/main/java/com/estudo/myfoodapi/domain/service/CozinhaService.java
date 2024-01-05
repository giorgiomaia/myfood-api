package com.estudo.myfoodapi.domain.service;

import com.estudo.myfoodapi.domain.entity.Cozinha;
import com.estudo.myfoodapi.domain.exception.EntidadeEmUsoException;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CozinhaService {
    private final CozinhaRepository cozinhaRepository;

    @Autowired
    public CozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    public Optional<Cozinha> buscar(Long id) {
        return cozinhaRepository.findById(id);
    }

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha atualizar(Long id, Cozinha cozinha) {
        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
        if (cozinhaAtual.isPresent()) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
            return cozinhaRepository.save(cozinhaAtual.get());
        }
        return null;
    }

    public void remover(Long id) {
        try {
            Cozinha cozinha = cozinhaRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            String.format("Não existe um cadastro de cozinha com o código %d.", id)));
            cozinhaRepository.delete(cozinha);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de id %d não pode ser removida, pois está em uso.", id));
        }
    }
}
