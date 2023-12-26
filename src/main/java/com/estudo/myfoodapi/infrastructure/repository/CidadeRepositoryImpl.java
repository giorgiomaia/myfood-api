package com.estudo.myfoodapi.infrastructure.repository;

import com.estudo.myfoodapi.domain.entity.Cidade;
import com.estudo.myfoodapi.domain.repository.CidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cidade> listar() {
        return entityManager.createQuery("FROM Cidade", Cidade.class).getResultList();
    }

    @Override
    public Cidade buscar(Long id) {
        return entityManager.find(Cidade.class, id);
    }

    @Override
    @Transactional
    public Cidade salvar(Cidade cidade) {
        return entityManager.merge(cidade);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Cidade cidade = buscar(id);
        if (Objects.isNull(cidade)) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(cidade);
    }
}
