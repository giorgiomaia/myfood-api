package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
