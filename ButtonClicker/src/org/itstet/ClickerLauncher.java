package org.itstet;

import org.itstet.model.Account;
import org.itstet.service.ClientImitator;
import org.itstet.service.Timer;
import org.openqa.selenium.WebDriver;

final class ClickerLauncher {

	public static void main(String[] args) {
	//	WebDriver driver = ClientImitator.getWebDriver();

		Account acc = new Account("Yaroslava", "Piskalova", "yaro67819@gmail.com", "ke6a1999");

		ClientImitator imitator = new ClientImitator();
		
		WebDriver driver = imitator.loginAmazonAccount(acc);
		
		imitator.registerAmazonAccount(acc);

	}

}
