package com.arcoiris.vendacontrole.controllers;

import java.util.List;
import java.util.Optional;

import com.arcoiris.vendacontrole.repositories.ItemCarrinhoRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arcoiris.vendacontrole.dtos.ItemCarrinhoDto;
import com.arcoiris.vendacontrole.models.CarrinhoModelo;
import com.arcoiris.vendacontrole.models.ItemCarrinhoModelo;
import com.arcoiris.vendacontrole.models.ProdutoModelo;
import com.arcoiris.vendacontrole.models.UsuarioModelo;
import com.arcoiris.vendacontrole.repositories.CarrinhoRepositorio;

import com.arcoiris.vendacontrole.repositories.ProdutoRepositorio;
import com.arcoiris.vendacontrole.repositories.UsuarioRepositorio;
import com.arcoiris.vendacontrole.services.CarrinhoServico;

import jakarta.validation.Valid;

@RestController
public class CarrinhoControle {

	private final CarrinhoServico carrinhoServico;
	private final CarrinhoRepositorio carrinhoRepositorio;
	private final ProdutoRepositorio produtoRepositorio;
	private final UsuarioRepositorio usuarioRepositorio;
	final ItemCarrinhoRepositorio itemCarrinhoRepositorio;

	public CarrinhoControle(CarrinhoServico carrinhoServico, CarrinhoRepositorio carrinhoRepositorio, ProdutoRepositorio produtoRepositorio, UsuarioRepositorio usuarioRepositorio, ItemCarrinhoRepositorio itemCarrinhoRepositorio) {
		this.carrinhoServico = carrinhoServico;
		this.carrinhoRepositorio = carrinhoRepositorio;
		this.produtoRepositorio = produtoRepositorio;
		this.usuarioRepositorio = usuarioRepositorio;
		this.itemCarrinhoRepositorio = itemCarrinhoRepositorio;
	}
	//adicionar item ao carrinho
	@PostMapping("/carrinho/{cpf}/adicionaritem/")
	public ResponseEntity<String> adicionarItemAoCarrinho(@PathVariable("cpf") String cpf,
			@RequestBody @Valid ItemCarrinhoDto itemCarrinhoDto) {
		Optional<UsuarioModelo> usuariOptional = usuarioRepositorio.findById(cpf);
		if (usuariOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe!");
		}
		var itemCarrinhoModelo = new ItemCarrinhoModelo();
		var carrinhoModelo = new CarrinhoModelo();
		BeanUtils.copyProperties(itemCarrinhoDto, itemCarrinhoModelo);
		Optional<ProdutoModelo> produtoOptional = produtoRepositorio.findById(itemCarrinhoModelo.getProdutoUuid());
		if (produtoOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não existe!");
		} else {
			Optional<CarrinhoModelo> carrinhoOptional = carrinhoRepositorio.findByCpf(cpf);
			carrinhoModelo = carrinhoOptional.get();
		}
		carrinhoServico.adicionarItem(carrinhoModelo, produtoOptional.get(), itemCarrinhoModelo.getQuantidade());
		return ResponseEntity.ok("Item adicionado ao carrinho com sucesso.");
	}
	//Listar carrinho
	@GetMapping("/carrinho/{cpf}/")
	public ResponseEntity<Object> mostrarCarrinho(@PathVariable("cpf") String cpf) {
		Optional<UsuarioModelo> usuariOptional = usuarioRepositorio.findById(cpf);
		if (usuariOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe!");
		}
		Optional<CarrinhoModelo> carrinhoO = carrinhoRepositorio.findByCpf(cpf);
		return ResponseEntity.status(HttpStatus.OK).body(carrinhoO.get().getItens());
	}
	//Atualiar item
	@PutMapping("/carrinho/{cpf}/atualizarItem")
	public ResponseEntity<Object> updateItem(@PathVariable("cpf") String cpf,
			@RequestBody @Valid ItemCarrinhoDto itemCarrinhoDto) {
		Optional<UsuarioModelo> usuariOptional = usuarioRepositorio.findById(cpf);
		if (usuariOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe!");
		}
		var itemCarrinhoModelo = new ItemCarrinhoModelo();
		BeanUtils.copyProperties(itemCarrinhoDto, itemCarrinhoModelo);
		Optional<ProdutoModelo> produtO = produtoRepositorio.findById(itemCarrinhoModelo.getProdutoUuid());
		if (produtO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item a ser modificado não existe!");
		}
		var carrinhoModelo = new CarrinhoModelo();
		Optional<CarrinhoModelo> carrinho0 = carrinhoRepositorio.findByCpf(cpf);
		carrinhoModelo = carrinho0.get();
		var produtoModelo = produtO.get();
		if (carrinhoServico.atualizarItem(carrinhoModelo, produtoModelo, itemCarrinhoModelo.getQuantidade())) {
			return ResponseEntity.status(HttpStatus.OK).body("Item modificado!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item a ser modificado não está no carrinho!");
	}
	//Deletar item
	@DeleteMapping("/carrinho/{cpf}/deletarItem")
	public ResponseEntity<Object> deleteItem(@PathVariable("cpf") String cpf,
			@RequestBody @Valid ItemCarrinhoDto itemCarrinhoDto) {
		Optional<UsuarioModelo> usuariOptional = usuarioRepositorio.findById(cpf);
		if (usuariOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe!");
		}
		var itemCarrinhoModelo = new ItemCarrinhoModelo();
		BeanUtils.copyProperties(itemCarrinhoDto, itemCarrinhoModelo);

		Optional<ProdutoModelo> produtO = produtoRepositorio.findById(itemCarrinhoModelo.getProdutoUuid());
		if (produtO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item a ser deletado não existe!");
		}
		Optional<CarrinhoModelo> carrinho0 = carrinhoRepositorio.findByCpf(cpf);
		var carrinhoModelo = carrinho0.get();
		var produtoModelo = produtO.get();
		if (carrinhoServico.deletarItem(carrinhoModelo, produtoModelo)) {
			return ResponseEntity.status(HttpStatus.OK).body("Item deletado!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item a ser deletado não está no carrinho!");
	}
	//Esvaziar carrinho
	@DeleteMapping("/carrinho/{cpf}/esvaziarCarrinho")
	public ResponseEntity<Object> deleteAllItem(@PathVariable("cpf") String cpf) {
		Optional<UsuarioModelo> usuariOptional = usuarioRepositorio.findById(cpf);
		if (usuariOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe!");
		}

		Optional<CarrinhoModelo> carrinho0 = carrinhoRepositorio.findByCpf(cpf);
		var carrinhoModelo = carrinho0.get();
		List<ItemCarrinhoModelo> itemCarrinhoModelo = carrinhoModelo.getItens();
		if (itemCarrinhoModelo.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Carrinho já está vazio!");
		}

		for (ItemCarrinhoModelo item : itemCarrinhoModelo) {
			itemCarrinhoRepositorio.delete(item);
		}
		carrinhoModelo.getItens().clear();
		carrinhoRepositorio.save(carrinhoModelo);
		return ResponseEntity.status(HttpStatus.OK).body("Carrinho esvaziado!");
	}
}
