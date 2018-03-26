package org.itstep.service;

import static org.junit.Assert.*;

import org.itstet.model.Account;
import org.itstet.service.ClientImitator;
import org.itstet.service.Timer;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CliendImitatorTest {

	@Test
	public void testLoginAccount() {

		Account acc = new Account("Yaroslava", "Pis", "yaro444@gmail.com", "zzzz1234");

		ClientImitator imitator = new ClientImitator();

		WebDriver driver = imitator.loginAmazonAccount(acc);

		Timer.waitSeconds(5);
		//assertTrue(driver.getPageSource().contains("Hello, " + acc.getName()));

		driver = imitator.addItemToWL(driver, "B077N43PST");
		
	//	assertThat(driver.getPageSource().contains(""));

		driver.quit();
	}

}
