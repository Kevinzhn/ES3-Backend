package com.arcoiris.vendacontrole.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

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

import com.arcoiris.vendacontrole.dtos.UsuarioDto;
import com.arcoiris.vendacontrole.models.CarrinhoModelo;
import com.arcoiris.vendacontrole.models.HistoricoModelo;
import com.arcoiris.vendacontrole.models.UsuarioModelo;
import com.arcoiris.vendacontrole.repositories.CarrinhoRepositorio;
import com.arcoiris.vendacontrole.repositories.HistoricoRepositorio;
import com.arcoiris.vendacontrole.repositories.UsuarioRepositorio;

import jakarta.validation.Valid;

@RestController
public class UsuarioControle {

	final
	UsuarioRepositorio usuarioRepositorio;
	final
	CarrinhoRepositorio carrinhoRepositorio;
	final
	HistoricoRepositorio historicoRepositorio;

	public UsuarioControle(UsuarioRepositorio usuarioRepositorio, CarrinhoRepositorio carrinhoRepositorio, HistoricoRepositorio historicoRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
		this.carrinhoRepositorio = carrinhoRepositorio;
		this.historicoRepositorio = historicoRepositorio;
	}
	//Adicionar um usuário
	@PostMapping("/usuario")
	public ResponseEntity<UsuarioModelo> saveUser(@RequestBody @Valid UsuarioDto usuarioDto) {
		var usuarioModelo = new UsuarioModelo();
		BeanUtils.copyProperties(usuarioDto, usuarioModelo);
		
		var carrinhoModelo = new CarrinhoModelo();
		carrinhoModelo.setCpf(usuarioModelo.getCpf());
		carrinhoRepositorio.save(carrinhoModelo);

		var historicoModelo = new HistoricoModelo();
		historicoModelo.setCpf(usuarioModelo.getCpf());
		historicoRepositorio.save(historicoModelo);
		usuarioRepositorio.save(usuarioModelo);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModelo);
	}
	//Listar todos os usuários
	@GetMapping("/usuario")
	public ResponseEntity<java.util.List<UsuarioModelo>> getAllUsuario() {
		List<UsuarioModelo> usuarioLista = usuarioRepositorio.findAll();
		if (!usuarioLista.isEmpty()) {
			for (UsuarioModelo usuario : usuarioLista) {
				String cpf = usuario.getCpf();
				usuario.add(linkTo(methodOn(UsuarioControle.class).getOneUsuario(cpf)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(usuarioLista);
	}
	//Mostrar um usuário
	@GetMapping("/usuario/{cpf}")
	public ResponseEntity<Object> getOneUsuario(@PathVariable(value = "cpf") String cpf) {
		Optional<UsuarioModelo> usuario0 = usuarioRepositorio.findById(cpf);
		if (usuario0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usurio não existe");
		}
		usuario0.get().add(linkTo(methodOn(UsuarioControle.class).getAllUsuario()).withRel("Lista de usuario"));
		return ResponseEntity.status(HttpStatus.OK).body(usuario0.get());
	}
	//Atualizar um usuário
	@PutMapping("/usuario/{cpf}")
	public ResponseEntity<Object> updateUsuario(@PathVariable(value = "cpf") String cpf,
			@RequestBody @Valid UsuarioDto usuarioDto) {

		Optional<UsuarioModelo> usuario0 = usuarioRepositorio.findById(cpf);
		if (usuario0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe");
		}
		var usuarioModelo = usuario0.get();
		BeanUtils.copyProperties(usuarioDto, usuarioModelo);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepositorio.save(usuarioModelo));
	}
	//Deletar um usuário
	@DeleteMapping("/usuario/{cpf}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "cpf") String cpf) {
		Optional<UsuarioModelo> usuario0 = usuarioRepositorio.findById(cpf);
		if (usuario0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe");
		}
		Optional<CarrinhoModelo> carrinhOptional = carrinhoRepositorio.findByCpf(cpf);
		if (carrinhOptional.isEmpty()){
			return ResponseEntity.status(HttpStatus.OK).body("Carrinho do usuario não existe!");
		}
		carrinhoRepositorio.delete(carrinhOptional.get());
		usuarioRepositorio.delete(usuario0.get());
		return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado");
	}
}
