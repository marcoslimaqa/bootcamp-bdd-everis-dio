package com.everis.pages;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.everis.util.Hooks;
import com.everis.util.Utils;

public class BasePage {

	protected WebDriver driver = Hooks.getDriver();
	protected ExtentTest extentTest = Hooks.getExtentTest();
	protected ExtentReports extentReport = Hooks.getExtentReports();

	public BasePage() {

	}

	protected void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected void waitMilliseconds(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected WebElement waitElement(By by, int timeOutInSeconds) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return element;
	}

	protected WebElement waitElement(WebElement webElement, int timeOutInSeconds) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(ElementNotVisibleException.class);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(webElement));
		return element;
	}

	protected List<WebElement> waitElements(By by, int timeOutInSeconds) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		List<WebElement> element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		return element;
	}

	protected boolean waitNotPresent(By by, int timeOutInSeconds) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		boolean isElementInvisible = false;
		try {
			isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			return false;
		}
		return isElementInvisible;
	}

	protected boolean waitUntilElementHasValue(WebElement element, String text) {
		boolean isElementhasText = false;
		try {
			waitMilliseconds(500);
			@SuppressWarnings("deprecation")
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(1, TimeUnit.SECONDS)
					.pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class);
			isElementhasText = wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
			return isElementhasText;
		} catch (Exception e) {
			return isElementhasText;
		}
	}

	protected void moveToElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	protected boolean isElementDisplayed(By by) {
		boolean isElementPresent = false;
		boolean isElementDisplayed = false;
		isElementPresent = !driver.findElements(by).isEmpty();
		if (isElementPresent) {
			isElementDisplayed = driver.findElement(by).isDisplayed();
		}
		return isElementPresent && isElementDisplayed;
	}

	protected void aguardarLoading() {
		try {
			waitElement(By.id("loading"), 3);
		} catch (Exception e) {
		}
		waitNotPresent(By.id("loading"), 120);
	}

	private String saveScreenshotInRelatoriosPath() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int milliseconds = calendar.get(Calendar.MILLISECOND);
		String datahora = "" + day + month + year + "_" + hours + minutes + seconds + milliseconds;
		String screenShotName = datahora + ".png";
		File scrFile = null;
		try {
			scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("target/report/html/img/" + screenShotName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return screenShotName;
	}

	protected void log(String log) {
		String screenShotName = saveScreenshotInRelatoriosPath();
		try {
			extentTest.pass(log, MediaEntityBuilder.createScreenCaptureFromPath("img/" + screenShotName).build());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void logPrintFail(String log) {
		String screenShotName = saveScreenshotInRelatoriosPath();
		try {
			extentTest.fail(log, MediaEntityBuilder.createScreenCaptureFromPath("img/" + screenShotName).build());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void logInfo(String log) {
		extentTest.info(log);
	}

	protected void logSkip(String log) {
		extentTest.skip(log);
	}

	protected void logFail(String log) {
		extentTest.fail(log);
	}

	protected void logError(String log) {
		extentTest.error(log);
	}

	protected void logPass(String log) {
		extentTest.pass(log);
	}

	protected ExtentTest createChildStart(String strNomeTeste) {
		ExtentTest parentTest = Hooks.getExtentTest();
		ExtentTest child = parentTest.createNode(strNomeTeste);
		return child;
	}

	protected void childLogFail(ExtentTest child, String log) {
		child.fail(log);
	}

	protected void childLogPass(ExtentTest child, String log) {
		child.pass(log);
	}

	protected void childLogInfo(ExtentTest child, String log) {
		child.info(log);
	}

	public void aguardarDownloadArquivo() {
		String downloadFilepath = System.getProperty("user.dir") + "/target/temp";
		Utils.waitForFileExistsInPath(downloadFilepath, 10);
		waitMilliseconds(500);
	}

}