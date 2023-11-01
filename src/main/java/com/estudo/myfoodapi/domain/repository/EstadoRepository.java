package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Estado;

import java.util.List;

public interface EstadoRepository {
    List<Estado> listar();

    Estado buscar(Long id);

    Estado salvar(Estado estado);

    void remover(Estado estado);
}