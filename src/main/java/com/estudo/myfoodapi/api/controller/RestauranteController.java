package com.estudo.myfoodapi.api.controller;

import com.estudo.myfoodapi.domain.entity.Restaurante;
import com.estudo.myfoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.myfoodapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    private final RestauranteService restauranteService;

    @Autowired
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteService.buscar(id);
        return restaurante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/por-filtro")
    public ResponseEntity<List<Restaurante>> buscarPorFiltro(String nome, BigDecimal taxaFreteInicial,
                                                             BigDecimal taxaFreteFinal) {
        return ResponseEntity.ok(restauranteService.buscarPorFiltro(nome, taxaFreteInicial, taxaFreteFinal));
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            Restaurante atualizaRestaurante = restauranteService.buscar(id).orElse(null);
            if (Objects.nonNull(atualizaRestaurante)) {
                BeanUtils.copyProperties(restaurante, atualizaRestaurante, "id");
                atualizaRestaurante = restauranteService.salvar(atualizaRestaurante);
                return ResponseEntity.status(HttpStatus.OK).body(atualizaRestaurante);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Restaurante atualizaRestaurante = restauranteService.buscar(id).orElse(null);
        if (Objects.isNull(atualizaRestaurante)) {
            return ResponseEntity.notFound().build();
        }
        atualizarParcialMerge(campos, atualizaRestaurante);
        return atualizar(id, atualizaRestaurante);
    }

    private void atualizarParcialMerge(Map<String, Object> campos, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restaurante = objectMapper.convertValue(campos, Restaurante.class);

        campos.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object valor = ReflectionUtils.getField(field, restaurante);

            ReflectionUtils.setField(field, restauranteDestino, valor);
        });
    }

}
