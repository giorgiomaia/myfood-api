package com.estudo.myfoodapi.domain.service;

import com.estudo.myfoodapi.domain.entity.Cozinha;
import com.estudo.myfoodapi.domain.entity.Restaurante;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.repository.CozinhaRepository;
import com.estudo.myfoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    @Autowired
    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> buscar(Long id) {
        return restauranteRepository.findById(id);
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() ->
                new EntidadeNaoEncontradaException(
                        String.format("Não existe um cadastro de cozinha com o código %d", cozinhaId)));
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

}
