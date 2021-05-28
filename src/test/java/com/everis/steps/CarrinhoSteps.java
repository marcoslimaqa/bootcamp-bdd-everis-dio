package com.everis.steps;

import org.junit.Assert;

import com.everis.pages.CarrinhoPage;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class CarrinhoSteps {
	
	@Entao("^o produto \"(.*)\" deve estar presente no carrinho$")
	public void apresentouProdutoEsperadoNoCarrinho(String nomeProduto) {
		CarrinhoPage carrinhoPage = new CarrinhoPage();
		Assert.assertTrue("Deveria ter apresentado o produto [" + nomeProduto + "] no carrinho", 
				carrinhoPage.apresentouProdutoEsperadoNoCarrinho(nomeProduto));
	}

	@E("^acessa o checkout$")
	public void acessarCheckout() {
		CarrinhoPage carrinhoPage = new CarrinhoPage();
		carrinhoPage.acessarCheckout();
	}

	@E("^realiza o login$")
	public void realizarLogin() {
		CarrinhoPage carrinhoPage = new CarrinhoPage();
		carrinhoPage.fazerLogin();
	}

	@E("^confirma o endereco de entrega$")
	public void confirmarEndereco(){
		CarrinhoPage carrinhoPage = new CarrinhoPage();
		carrinhoPage.confirmarEndereco();
		carrinhoPage.aceitarTermosServiço();
		carrinhoPage.confirmarEntrega();
	}

	@E("^escolhe a forma de pagamento$")
	public void escolherFormaPagamento(){
		CarrinhoPage carrinhoPage = new CarrinhoPage();
		carrinhoPage.escolherMetodoPagamento();
	}

	@Quando("^o pagamento for confirmado$")
	public void confirmarCompra() {
		CarrinhoPage carrinhoPage = new CarrinhoPage();
		carrinhoPage.confirmarCompra();
	}

	@Entao("^deve ser apresentada a mensagem \"(.*)\"$")
	public void mensagemEsperadaEstaPresente(String mensagemEsperada){
		CarrinhoPage carrinhoPage = new CarrinhoPage();
		
		Assert.assertTrue(
			"A mensagem não foi encontrada.", 
			carrinhoPage.mensagemEsperadaEncontrada(mensagemEsperada)
		);
	}
	
}