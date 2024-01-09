package com.estudo.myfoodapi.domain.service;

import com.estudo.myfoodapi.domain.entity.FormaPagamento;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormaPagamentoService {
    private final FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    public Optional<FormaPagamento> buscar(Long id) {
        return formaPagamentoRepository.findById(id);
    }

    public FormaPagamento adicionar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento atualizar(Long id, FormaPagamento formaPagamento) {
        FormaPagamento atualizaFormaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe forma de pagamento com o id %d.", id)));

        BeanUtils.copyProperties(formaPagamento, atualizaFormaPagamento, "id");
        return formaPagamentoRepository.save(atualizaFormaPagamento);
    }

    public void remover(Long id) {
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe forma de pagamento com id %d.", id)));
        formaPagamentoRepository.delete(formaPagamento);
    }

}
