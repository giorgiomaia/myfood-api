package com.estudo.myfoodapi.infrastructure.repository;

import com.estudo.myfoodapi.domain.entity.Estado;
import com.estudo.myfoodapi.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> listar() {
        return entityManager.createQuery("FROM Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Override
    @Transactional
    public Estado salvar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Estado estado = buscar(id);
        if (Objects.isNull(estado)) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(estado);
    }
}
