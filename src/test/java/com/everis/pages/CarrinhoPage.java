package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class CarrinhoPage extends BasePage {

	@FindBy(xpath = ".//p[@class='cart_navigation clearfix']/a[@title='Proceed to checkout']")
	protected WebElement botaoProsseguir;

	@FindBy(xpath = "//button[@name='processAddress']")
	protected WebElement botaoProcessAddress;
	//processCarrier
	@FindBy(xpath = "//button[@name='processCarrier']")
	protected WebElement botaoProcessCarrier;

	@FindBy(xpath = ".//label[@for='cgv']")
	protected WebElement checkTermosServico;

	@FindBy(css = "a[title='Pay by bank wire']")
	protected WebElement botaoContratarBanco;

	@FindBy(xpath = "//*/div[@class='box']/p[@class='cheque-indent']/strong/")
	protected WebElement tituloMensagemConfirmacao;

	// 
	@FindBy(xpath = "//*[@id='cart_navigation']/button[@type='submit']")
	protected WebElement botaoConfirmarCompra;
	
	public CarrinhoPage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}
	
	public boolean apresentouProdutoEsperadoNoCarrinho(String nomeProduto) {
		boolean apresentouProdutoEsperadoNoCarrinho = isElementDisplayed(By.xpath("//*[contains(@class,'cart_item')]//a[text()='" + nomeProduto + "']"));
		if (apresentouProdutoEsperadoNoCarrinho) {
			log("Apresentou o produto [" + nomeProduto + "] no carrinho conforme esperado.");
			return true;
		}
		logFail("Deveria ter apresentado o produto [" + nomeProduto + "] no carrinho.");
		return false;
	}

	public void acessarCheckout(){
		waitElement(By.xpath(".//p[@class='cart_navigation clearfix']/a[@title='Proceed to checkout']"), 5);
		botaoProsseguir.click();
	}

	public void fazerLogin(){
		waitElement(By.cssSelector("#email"), 5);

		this.driver.findElement(
			By.cssSelector("#email")
		).sendKeys("jao.02@digitalinovation.one");

		this.driver.findElement(
			By.cssSelector("#passwd")
		).sendKeys("_senha_");

		this.driver.findElement(
			By.cssSelector("#SubmitLogin")
		).click();
		logInfo("Logado e pronto para a compra!");
	}

	public void confirmarEndereco(){
		botaoProcessAddress.click();
		logInfo("O botão de confirmação de endereço foi pressionado.");
	}

	public void aceitarTermosServiço(){
		waitElement(By.xpath(".//label[@for='cgv']"), 5);
		checkTermosServico.click();
		logInfo("Termos de serviço aceito.");
	}

	public void confirmarEntrega() {
		botaoProcessCarrier.click();
		logInfo("O botão de confirmação de entregafoi pressionado.");
	}

	public void escolherMetodoPagamento(){
		waitElement(By.xpath("//*[@id='center_column']/h1[text()='Please choose your payment method']"), 5);
		botaoContratarBanco.click();
		logInfo("Metodo de pagamento escolhido.");
	}

	public void confirmarCompra(){
		botaoConfirmarCompra.click();
		logInfo("O botão de confirmação de compra foi pressionado.");
	}

	public Boolean mensagemEsperadaEncontrada(String mensagemEsperada){
		try{
			driver.findElement(
				By.xpath("//*/div[@class='box']/*/*[text()='" + mensagemEsperada +"']")
			);
			logInfo("A mensagem '+ mensagemEsperada +' na página atual!");
			return true;
		}catch(NoSuchElementException e){
			logFail("A mensagem esperada ('"+ mensagemEsperada + "') não foi encontrada.");
		}
		return false;
	}
}