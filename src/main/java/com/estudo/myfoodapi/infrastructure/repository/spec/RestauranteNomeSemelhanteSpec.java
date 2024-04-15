package com.estudo.myfoodapi.infrastructure.repository.spec;

import com.estudo.myfoodapi.domain.entity.Restaurante;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class RestauranteNomeSemelhanteSpec implements Specification<Restaurante> {

    private String nome;

    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    }
}
