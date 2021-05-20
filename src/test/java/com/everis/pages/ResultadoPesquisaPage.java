package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class ResultadoPesquisaPage extends BasePage {

	@FindBy(xpath = "//*[text()='Add to cart']")
	protected WebElement botaoAdicionarAoCarrinho;
	
	@FindBy(css = "[title='Proceed to checkout']")
	protected WebElement botaoProsseguir;
	
	public ResultadoPesquisaPage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}

	public void adicionarProdutoAoCarrinho(String nomeProduto) {
		WebElement nomeProdutoTela = driver.findElement(By.xpath(".//*[@itemprop='name']/*[contains(text(), '" + nomeProduto + "')] | .//*[@itemprop='name'][text()='" + nomeProduto + "']"));
		moveToElement(nomeProdutoTela);
		botaoAdicionarAoCarrinho.click();
		waitElement(botaoProsseguir, 10).click();
		log("Adicionou o produto [" + nomeProduto + "] ao carrinho");
	}

}