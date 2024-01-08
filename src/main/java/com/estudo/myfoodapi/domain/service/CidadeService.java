package com.estudo.myfoodapi.domain.service;

import com.estudo.myfoodapi.domain.entity.Cidade;
import com.estudo.myfoodapi.domain.entity.Estado;
import com.estudo.myfoodapi.domain.exception.EntidadeEmUsoException;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.repository.CidadeRepository;
import com.estudo.myfoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public CidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Optional<Cidade> buscar(Long id) {
        return cidadeRepository.findById(id);
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(estadoId);
        if (estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com o código %d.", estadoId));
        }
        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public void remover(Long id) {
        try {
            Cidade cidade = cidadeRepository.findById(id).orElseThrow(() ->
                    new EntidadeNaoEncontradaException(
                            String.format("Não existe um cadastro de cidade com o código %d.", id)));
            cidadeRepository.delete(cidade);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso.", id));
        }
    }

}
