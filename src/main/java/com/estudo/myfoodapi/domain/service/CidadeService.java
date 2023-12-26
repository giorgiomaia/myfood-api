package com.estudo.myfoodapi.domain.service;

import com.estudo.myfoodapi.domain.entity.Cidade;
import com.estudo.myfoodapi.domain.entity.Estado;
import com.estudo.myfoodapi.domain.exception.EntidadeEmUsoException;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.repository.CidadeRepository;
import com.estudo.myfoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return cidadeRepository.listar();
    }

    public Cidade buscar(Long id) {
        return cidadeRepository.buscar(id);
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);
        if (Objects.isNull(estado)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com o código %d.", estadoId));
        }
        cidade.setEstado(estado);
        return cidadeRepository.salvar(cidade);
    }

    public void remover(Long id) {
        try {
        Cidade cidade = cidadeRepository.buscar(id);
        cidadeRepository.remover(id);

        } catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com o código %d.", id)
            );

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
              String.format("Cidade de código %d não pode ser removida, pois está em uso.", id)
            );
        }

    }
}
