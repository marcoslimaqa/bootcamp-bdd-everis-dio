#language: pt
#encoding: utf-8

@test
Funcionalidade: Realizar Compra no E-commerce

	Como um usuario que deseja realizar uma compra na internet
	Quero poder pesquisar um produto
	Para que eu possa realizar uma compra
		
  Cenario: Adicionar produto ao carrinho
  	Dado que um usuario acessa o site "http://automationpractice.com"
  	E pesquisa pelo produto "Blouse"
  	Quando adiciona o produto "Blouse" ao carrinho
  	Entao o produto "Blouse" deve estar presente no carrinho
