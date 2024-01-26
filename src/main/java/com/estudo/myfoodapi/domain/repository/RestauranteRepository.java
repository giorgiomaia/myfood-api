package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository
        extends JpaRepository<Restaurante, Long>, RestauranteRepositoryPersonalizado {
}
