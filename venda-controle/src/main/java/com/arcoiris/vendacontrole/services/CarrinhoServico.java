package com.arcoiris.vendacontrole.services;

import org.springframework.stereotype.Service;

import com.arcoiris.vendacontrole.models.CarrinhoModelo;
import com.arcoiris.vendacontrole.models.ItemCarrinhoModelo;
import com.arcoiris.vendacontrole.models.ProdutoModelo;
import com.arcoiris.vendacontrole.repositories.CarrinhoRepositorio;
import com.arcoiris.vendacontrole.repositories.ItemCarrinhoRepositorio;

@Service
public class CarrinhoServico {

	private final CarrinhoRepositorio carrinhoRepositorio;
	private final ItemCarrinhoRepositorio itemCarrinhoRepositorio;

	public CarrinhoServico(CarrinhoRepositorio carrinhoRepositorio, ItemCarrinhoRepositorio itemCarrinhoRepositorio) {
		this.carrinhoRepositorio = carrinhoRepositorio;
		this.itemCarrinhoRepositorio = itemCarrinhoRepositorio;
	}

	public void adicionarItem(CarrinhoModelo carrinho, ProdutoModelo produto, int quantidade) {
		var item = new ItemCarrinhoModelo();
		for (ItemCarrinhoModelo itemCarrinhoModelo : carrinho.getItens()) {
			if (produto.getProdutoUuid().equals(itemCarrinhoModelo.getProdutoUuid())) {
				quantidade += itemCarrinhoModelo.getQuantidade();
				itemCarrinhoModelo.setQuantidade(quantidade);
				itemCarrinhoModelo.setSubtotal(produto.getPreco() * quantidade);
				calcularTotal(carrinho);
				itemCarrinhoRepositorio.save(itemCarrinhoModelo);
				carrinhoRepositorio.save(carrinho);
				return;
			}
		}
		item.setProdutoUuid(produto.getProdutoUuid());
		item.setQuantidade(quantidade);
		item.setSubtotal(produto.getPreco() * quantidade);
		item.setCarrinho(carrinho);
		carrinho.getItens().add(item);
		calcularTotal(carrinho);
		carrinhoRepositorio.save(carrinho);
	}

	public boolean atualizarItem(CarrinhoModelo carrinho, ProdutoModelo produto, int quantidade) {
		for (ItemCarrinhoModelo itemCarrinhoModelo : carrinho.getItens()) {
			if (produto.getProdutoUuid().equals(itemCarrinhoModelo.getProdutoUuid())) {
				if (quantidade == 0) {
					itemCarrinhoRepositorio.delete(itemCarrinhoModelo);
					carrinhoRepositorio.save(carrinho);
					return true;
				}
				itemCarrinhoModelo.setQuantidade(quantidade);
				itemCarrinhoModelo.setSubtotal(produto.getPreco() * quantidade);
				itemCarrinhoRepositorio.save(itemCarrinhoModelo);
				calcularTotal(carrinho);
				carrinhoRepositorio.save(carrinho);
				return true;
			}
		}
		return false;
	}

	public boolean deletarItem(CarrinhoModelo carrinho, ProdutoModelo produto) {
		for (ItemCarrinhoModelo itemCarrinhoModelo : carrinho.getItens()) {
			if (produto.getProdutoUuid().equals(itemCarrinhoModelo.getProdutoUuid())) {
				carrinho.getItens().remove(itemCarrinhoModelo);
				itemCarrinhoRepositorio.delete(itemCarrinhoModelo);
				calcularTotal(carrinho);
				carrinhoRepositorio.save(carrinho);
				return true;
			}
		}
		return false;
	}

	private void calcularTotal(CarrinhoModelo carrinho) {
		double total = 0.0;
		for (ItemCarrinhoModelo item : carrinho.getItens()) {
			total += item.getSubtotal();
		}
		carrinho.setTotal(total);
	}
}
