package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Cidade;

import java.util.List;

public interface CidadeRepository {
    List<Cidade> listar();

    Cidade buscar(Long id);

    Cidade salvar(Cidade cidade);

    void remover(Cidade cidade);
}
