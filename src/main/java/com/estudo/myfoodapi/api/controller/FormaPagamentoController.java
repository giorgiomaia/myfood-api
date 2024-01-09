package com.estudo.myfoodapi.api.controller;

import com.estudo.myfoodapi.domain.entity.FormaPagamento;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.service.FormaPagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/formasDePagamento")
public class FormaPagamentoController {
    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamento>> listar() {
        return ResponseEntity.ok(formaPagamentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamento> buscar(@PathVariable Long id) {
        Optional<FormaPagamento> formaPagamento = formaPagamentoService.buscar(id);
        return formaPagamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> adicionar(@RequestBody FormaPagamento formaPagamento) {
        formaPagamento = formaPagamentoService.adicionar(formaPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody FormaPagamento formaPagamento) {
        try {
            formaPagamento = formaPagamentoService.atualizar(id, formaPagamento);
            return ResponseEntity.ok(formaPagamento);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            formaPagamentoService.remover(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
