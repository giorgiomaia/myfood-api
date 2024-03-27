package com.estudo.myfoodapi.infrastructure.repository;

import com.estudo.myfoodapi.domain.entity.Restaurante;
import com.estudo.myfoodapi.domain.repository.RestauranteRepositoryPersonalizado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryPersonalizado {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Restaurante> buscarPorFiltro(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        var jpql = new StringBuilder();
        var parametros = new HashMap<String, Object>();

        jpql.append("FROM Restaurante ");
        adicionarCriterios(nome, taxaInicial, taxaFinal, jpql, parametros);
        TypedQuery<Restaurante> query = adicionarParams(jpql, parametros);

        return query.getResultList();
    }

    private void adicionarCriterios(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal, StringBuilder jpql, HashMap<String, Object> parametros) {
        if (StringUtils.hasLength(nome) || Objects.nonNull(taxaInicial) || Objects.nonNull(taxaFinal)) {
            jpql.append("WHERE 0 = 0 ");

            if (StringUtils.hasLength(nome)) {
                jpql.append("AND UPPER(nome) like :nome ");
                parametros.put("nome", "%" + nome.toUpperCase() + "%");
            }
            if (Objects.nonNull(taxaInicial)) {
                jpql.append("AND taxaFrete >= :taxaInicial ");
                parametros.put("taxaInicial", taxaInicial);
            }
            if (Objects.nonNull(taxaFinal)) {
                jpql.append("AND taxaFrete <= :taxaFinal ");
                parametros.put("taxaFinal", taxaFinal);
            }
        }
    }

    private TypedQuery<Restaurante> adicionarParams(StringBuilder jpql, HashMap<String, Object> parametros) {
        TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
        parametros.forEach(query::setParameter);
        return query;
    }

    // USANDO CONSULTAS COM CRITERIA
    @Override
    public List<Restaurante> buscarPorFiltroCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        var predicates = new ArrayList<Predicate>();

        Root<Restaurante> restauranteRaiz = criteria.from(Restaurante.class);

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(restauranteRaiz.get("nome"), "%" + nome + "%"));
        }
        if (Objects.nonNull(taxaInicial)) {
            predicates.add(builder.greaterThanOrEqualTo(restauranteRaiz.get("taxaFrete"), taxaInicial));
        }
        if (Objects.nonNull(taxaFinal)) {
            predicates.add(builder.lessThanOrEqualTo(restauranteRaiz.get("taxaFrete"), taxaFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }
}
