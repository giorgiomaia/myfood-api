package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();

    Cozinha buscar(Long id);

    Cozinha salvar(Cozinha cozinha);

    void remover(Long id);

}
