package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> listar();

    Permissao buscar(Long id);

    Permissao salvar(Permissao permissao);

    void remover(Permissao permissao);
}
