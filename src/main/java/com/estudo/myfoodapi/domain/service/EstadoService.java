package com.estudo.myfoodapi.domain.service;

import com.estudo.myfoodapi.domain.entity.Estado;
import com.estudo.myfoodapi.domain.exception.EntidadeEmUsoException;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> listar() {
        return estadoRepository.listar();
    }

    public Estado buscar(Long id) {
        return estadoRepository.buscar(id);
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }

    public void remover(Long id) {
        try {
            estadoRepository.remover(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com o código %d.", id));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso.", id));
        }
    }

}
