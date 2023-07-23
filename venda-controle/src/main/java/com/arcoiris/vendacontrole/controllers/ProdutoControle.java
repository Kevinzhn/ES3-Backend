package com.arcoiris.vendacontrole.controllers;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

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

import com.arcoiris.vendacontrole.dtos.ProdutoDto;
import com.arcoiris.vendacontrole.models.ProdutoModelo;
import com.arcoiris.vendacontrole.repositories.ProdutoRepositorio;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController

public class ProdutoControle {

	final
	ProdutoRepositorio produtoRepositorio;

	public ProdutoControle(ProdutoRepositorio produtoRepositorio) {
		this.produtoRepositorio = produtoRepositorio;
	}

	@PostMapping("/produto")
	public ResponseEntity<ProdutoModelo> saveProduto(@RequestBody @Valid ProdutoDto produtoDto) {
		var produtoModelo = new ProdutoModelo();
		BeanUtils.copyProperties(produtoDto, produtoModelo);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepositorio.save(produtoModelo));
	}

	@GetMapping("/produto")
	public ResponseEntity<List<ProdutoModelo>> getAllProduto() {
		List<ProdutoModelo> produtoLista = produtoRepositorio.findAll();
		if (!produtoLista.isEmpty()) {
			for (ProdutoModelo produto : produtoLista) {
				UUID id = produto.getProdutoUuid();
				produto.add(linkTo(methodOn(ProdutoControle.class).getOneProduto(id)).withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(produtoLista);
	}

	@GetMapping("/produto/{id}")
	public ResponseEntity<Object> getOneProduto(@PathVariable(value = "id") UUID id) {
		Optional<ProdutoModelo> produto0 = produtoRepositorio.findById(id);
		if (produto0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não existe");
		}
		produto0.get().add(linkTo(methodOn(ProdutoControle.class).getAllProduto()).withRel("Lista de produto"));
		return ResponseEntity.status(HttpStatus.OK).body(produto0.get());
	}

	@PutMapping("/produto/{id}")
	public ResponseEntity<Object> updateProduto(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ProdutoDto produtoDto) {

		Optional<ProdutoModelo> produto0 = produtoRepositorio.findById(id);
		if (produto0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não existe");
		}
		var produtoModelo = produto0.get();
		BeanUtils.copyProperties(produtoDto, produtoModelo);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepositorio.save(produtoModelo));
	}

	@DeleteMapping("/produto/{id}")
	public ResponseEntity<Object> deleteProduto(@PathVariable(value = "id") UUID id) {
		Optional<ProdutoModelo> produto0 = produtoRepositorio.findById(id);
		if (produto0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não existe");
		}
		produtoRepositorio.delete(produto0.get());
		return ResponseEntity.status(HttpStatus.OK).body("Produto deletado");
	}
}
