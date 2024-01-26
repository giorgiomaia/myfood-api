package com.estudo.myfoodapi.infrastructure.repository;

import com.estudo.myfoodapi.domain.entity.Restaurante;
import com.estudo.myfoodapi.domain.repository.RestauranteRepositoryPersonalizado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryPersonalizado {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Restaurante> buscarPorFiltro(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        var jpql = new StringBuilder();
        var filtros = new HashMap<String, Object>();

        jpql.append("FROM Restaurante ");
        adicionarCriterios(nome, taxaInicial, taxaFinal, jpql, filtros);
        TypedQuery<Restaurante> query = adicionarParams(jpql, filtros);

        return query.getResultList();
    }

    private void adicionarCriterios(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal, StringBuilder jpql, HashMap<String, Object> filtro) {
        if (StringUtils.hasLength(nome) || Objects.nonNull(taxaInicial) || Objects.nonNull(taxaFinal)) {
            jpql.append("WHERE 0 = 0 ");
        }
        if (Objects.nonNull(nome)) {
            jpql.append("AND UPPER(nome) like :nome ");
            filtro.put("nome", "%" + nome.toUpperCase() + "%");
        }
        if (Objects.nonNull(taxaInicial)) {
            jpql.append("AND taxaFrete >= :taxaInicial ");
            filtro.put("taxaInicial", taxaInicial);
        }
        if (Objects.nonNull(taxaFinal)) {
            jpql.append("AND taxaFrete <= :taxaFinal ");
            filtro.put("taxaFinal", taxaFinal);
        }
    }

    private TypedQuery<Restaurante> adicionarParams(StringBuilder jpql, HashMap<String, Object> filtros) {
        TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
        filtros.forEach(query::setParameter);
        return query;
    }
}
