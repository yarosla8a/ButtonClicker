package org.itstet.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.itstet.model.Account;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;

public class ClientImitator {

	private static final String SEPARATOR = System.getProperty("file.separator");

	private static final String USER_DIR = System.getProperty("user.dir");

	private static final String DRIVER_PATH = USER_DIR + SEPARATOR + "lib" + SEPARATOR + "chromedriver.exe";

	private static final String BASE_URL = "https://www.amazon.com";

	public WebDriver getWebDriver() {

		System.setProperty("webdriver.chrome.driver", DRIVER_PATH);

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(45, TimeUnit.SECONDS);

		Timer.waitSeconds(3);
		return driver;
	}

	public void registerAmazonAccount(Account account) {

		WebDriver driver = getWebDriver();
		Timer.waitSeconds(3);
		driver.get("https://www.amazon.com");
		Timer.waitSeconds(5);

		WebElement registerBlock = driver.findElement(By.id("nav-flyout-ya-newCust"));
		WebElement registerLinkElement = registerBlock.findElement(By.tagName("a"));//?

		String registerLink = registerLinkElement.getAttribute("href");

		driver.get(registerLink);
		Timer.waitSeconds(25);

		WebElement nameElement = driver.findElement(By.id("ap_customer_name"));
		WebElement emailElement = driver.findElement(By.id("ap_email"));
		WebElement passwordElement = driver.findElement(By.id("ap_password"));
		WebElement checkPasswordElement = driver.findElement(By.id("ap_password_check"));
		WebElement submitElement = driver.findElement(By.id("continue"));

		nameElement.sendKeys(account.getName() + " " + account.getSurname());
		emailElement.sendKeys(account.getEmail());
		passwordElement.sendKeys(account.getPassword());
		checkPasswordElement.sendKeys(account.getPassword());

		submitElement.submit();

		String pageLink = driver.getCurrentUrl();
		Timer.waitSeconds(6);
		driver.get(pageLink);

		Timer.waitSeconds(20);

		driver.quit();
	}

	public WebDriver loginAmazonAccount(Account account) {
		WebDriver driver = getWebDriver();

		Timer.waitSeconds(3);
		driver.get(BASE_URL);
		Timer.waitSeconds(5);

		WebElement loginBlock = driver.findElement(By.id("nav-flyout-ya-signin"));
		WebElement loginLinkElement = loginBlock.findElement(By.tagName("a"));

		String loginLink = loginLinkElement.getAttribute("href");

		loginLink = loginLink.contains(BASE_URL) ? loginLink : BASE_URL + loginLink;

		driver.get(loginLink);
		Timer.waitSeconds(15);

		WebElement emailElement = driver.findElement(By.id("ap_email"));
		WebElement passwordElement = driver.findElement(By.id("ap_password"));
		WebElement submitElement = driver.findElement(By.id("signInSubmit"));

		emailElement.sendKeys(account.getEmail());
		passwordElement.sendKeys(account.getPassword());

		submitElement.submit();//?

		Timer.waitSeconds(5);

		String currentPage = driver.getCurrentUrl();//?
		driver.get(currentPage);
		Timer.waitSeconds(5);
		return driver;
	}

	public WebDriver addItemToWL(WebDriver driver, String itemAsin) {

		WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
		searchInput.sendKeys(itemAsin);
		Timer.waitSeconds(15);

		WebElement searchBox = driver.findElement(By.id("nav-search"));
		List<WebElement> inputElements = searchBox.findElements(By.tagName("input"));
		for (WebElement inputElement : inputElements) {
			if (inputElement.getAttribute("type").equals("submit")) {
				inputElement.submit();
				break;
			}
		}
		Timer.waitSeconds(5);
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);

		Timer.waitSeconds(5);

		WebElement productsBlock = driver.findElement(By.id("s-results-list-atf"));
		List<WebElement> productList = productsBlock.findElements(By.tagName("li"));

		for (WebElement productElement : productList) {

			if (productElement.getAttribute("data-asin") == null) {
				continue;
			}
			if (productElement.getAttribute("data-asin").equals(itemAsin)) {
				List<WebElement> productLinkElements = productElement.findElements(By.tagName("a"));
				for (WebElement aElement : productLinkElements) {
					if (aElement.getAttribute("class") != null) {
						if (aElement.getAttribute("class").contains("s-access-detail-page")) {
							String productLink = aElement.getAttribute("href");
							if (!productLink.contains(BASE_URL)) {
								productLink = BASE_URL + productLink;
							}
							driver.get(productLink);
							Timer.waitSeconds(5);
							break;
						}
					}

				}
			}
		}
		String currentWindow = driver.getWindowHandle();

		WebElement wlBtn = driver.findElement(By.id("add-to-wishlist-button-submit"));

		wlBtn.click();
		Timer.waitSeconds(15);
		Set<String> windows = driver.getWindowHandles();

		for (String win : windows) {
			if (!win.equals(currentWindow)) {
				driver.switchTo().window(win);

				WebElement wlRadioBtn = driver.findElement(By.id("WLNEW_list_type_WL"));
				wlRadioBtn.click();

				List<WebElement> inpElements = driver.findElements(By.tagName("input"));
				for (WebElement inputEl : inpElements) {
					if (inputEl.getAttribute("aria-labelledby").equals("WLNEW_privacy_public-announce")) {
						inputEl.click();
					}
				}
				inpElements.get(inpElements.size() - 1).submit();
			}
		}

		driver.switchTo().window(currentWindow);

		return driver;
	}
}
