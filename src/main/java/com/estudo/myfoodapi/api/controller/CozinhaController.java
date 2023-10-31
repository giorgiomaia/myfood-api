package com.estudo.myfoodapi.api.controller;

import com.estudo.myfoodapi.domain.entity.Cozinha;
import com.estudo.myfoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping(value = "/cozinhas")
    public List<Cozinha> cozinhas() {
        return cozinhaRepository.listar();
    }
}
