package com.everis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.everis.util.Hooks;

public class HomePage extends BasePage {

	@FindBy(xpath = "//*[@id='search_query_top']")
	protected WebElement campoBusca;
	
	@FindBy(name = "submit_search")
	protected WebElement botaoLupaBuscar;
	
	public HomePage() {
		PageFactory.initElements(Hooks.getDriver(), this);
	}

	public void pesquisarProduto(String nomeProduto) {
		waitElement(By.cssSelector("#search_query_top"), 5);
		campoBusca.clear();
		campoBusca.click();
		campoBusca.sendKeys(nomeProduto);
		waitUntilElementHasValue(campoBusca, nomeProduto);
		botaoLupaBuscar.click();
		log("Pesquisou pelo produto: " + nomeProduto);
	}

}