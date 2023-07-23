package com.arcoiris.vendacontrole.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.arcoiris.vendacontrole.models.*;
import com.arcoiris.vendacontrole.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoricoControle {
    final
    CarrinhoRepositorio carrinhoRepositorio;
    final
    UsuarioRepositorio usuarioRepositorio;
    final
    PedidoRepositorio pedidoRepositorio;
    final
    HistoricoRepositorio historicoRepositorio;
    final
    ItemCarrinhoRepositorio itemCarrinhoRepositorio;

    public HistoricoControle(CarrinhoRepositorio carrinhoRepositorio, UsuarioRepositorio usuarioRepositorio, PedidoRepositorio pedidoRepositorio, HistoricoRepositorio historicoRepositorio, ItemCarrinhoRepositorio itemCarrinhoRepositorio) {
        this.carrinhoRepositorio = carrinhoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.pedidoRepositorio = pedidoRepositorio;
        this.historicoRepositorio = historicoRepositorio;
        this.itemCarrinhoRepositorio = itemCarrinhoRepositorio;
    }

    @PostMapping("/carrinho/{cpf}/confirmar/")
    public ResponseEntity<Object> confirmarCompraEntity(@PathVariable("cpf") String cpf) {
        Optional<UsuarioModelo> usuarioO = usuarioRepositorio.findById(cpf);
        if (usuarioO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe!");
        }
        Optional<CarrinhoModelo> carrinhoO = carrinhoRepositorio.findByCpf(cpf);
        var carrinhoModelo = carrinhoO.get();
        if (carrinhoModelo.getItens().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Carrinho vazio!");
        }
        var pedidoModelo = new PedidoModelo();
        pedidoModelo.setItens(new ArrayList<>(carrinhoModelo.getItens()));
        pedidoModelo.setTotal(carrinhoModelo.getTotal());
        pedidoModelo.setData(new Date());
        pedidoModelo.setHistoricoModelo(historicoRepositorio.findByCpf(cpf).get());
        pedidoRepositorio.save(pedidoModelo);
        Optional<HistoricoModelo> historicoO = historicoRepositorio.findByCpf(cpf);
        var historicoModelo = historicoO.get();
        historicoModelo.getPedidos().add(pedidoModelo);
        historicoRepositorio.save(historicoModelo);
        itemCarrinhoRepositorio.deleteAll(carrinhoModelo.getItens());
        carrinhoModelo.getItens().clear();
        carrinhoRepositorio.save(carrinhoModelo);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoModelo);
    }

    @GetMapping("/historico/{cpf}/")
    public ResponseEntity<Object> getHistorico(@PathVariable("cpf") String cpf) {
        Optional<UsuarioModelo> usuarioO = usuarioRepositorio.findById(cpf);
        if (usuarioO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe!");
        }
        Optional<HistoricoModelo> historicO = historicoRepositorio.findByCpf(cpf);
        var historicoModelo = historicO.get();
        return ResponseEntity.status(HttpStatus.OK).body(historicoModelo);
    }
}

