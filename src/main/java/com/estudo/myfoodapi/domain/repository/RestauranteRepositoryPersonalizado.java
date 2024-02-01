package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryPersonalizado {
    List<Restaurante> buscarPorFiltro(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
    List<Restaurante> buscarPorFiltroCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
}
