package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> todas();

    Cozinha buscar(Long id);

    Cozinha salvar(Cozinha cozinha);

    void remover(Cozinha cozinha);

}
