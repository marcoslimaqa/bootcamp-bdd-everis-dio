#language: pt
#encoding: utf-8

@test
Funcionalidade: Realizar Compra no E-commerce

	Como um comprador
	Quero ver a lista de produtos disponiveis
	Para que eu possa escolher qual devo comprar
		
  Cenario: Realizar compra
  	Dado que um usuario acessa o site "http://automationpractice.com"
  	E pesquisa pelo produto "Blouse"
  	E adiciona o produto "Blouse" ao carrinho
	E acessa o checkout
	E realiza o login
	E confirma o endereco de entrega
	E escolhe a forma de pagamento
	Quando o pagamento for confirmado
  	Entao deve ser apresentada a mensagem "Your order on My Store is complete."

