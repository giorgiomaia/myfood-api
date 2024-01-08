package com.estudo.myfoodapi.domain.repository;

import com.estudo.myfoodapi.domain.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
