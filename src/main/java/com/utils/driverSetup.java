package com.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.firefox.FirefoxBinary;

public class driverSetup {
	private static WebDriver driver;

	@SuppressWarnings("resource")
	public static WebDriver getWebDriver() {
		while (true) {
			System.out.println("Enter Browser Name or 0 to Exit");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			String browser = sc.nextLine();
			if (browser.equalsIgnoreCase("0")) {
				System.out.println("Program terminatted");
				System.exit(0);
			} else if (!(browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("firefox"))) {
				System.out.println("Invalid browser name, ");
				continue;
			}

			else if (browser.equalsIgnoreCase("firefox")) {
				//FirefoxBinary firefoxBinary = new FirefoxBinary();
				//firefoxBinary.addCommandLineOptions("--headless");
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("marionette.logging", "TRACE");
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				//firefoxOptions.setBinary(firefoxBinary);
				firefoxOptions.setProfile(profile);
				driver = new FirefoxDriver(firefoxOptions);

				return driver;
			}

			else if (browser.equalsIgnoreCase("chrome")) {
				// To launch application in Chrome browser
				// Create a map to store preferences
				Map<String, Object> prefs = new HashMap<String, Object>();

				// add key and value to map as follow to switch off browser notification
				// Pass the argument 1 to allow and 2 to block
				prefs.put("profile.default_content_setting_values.notifications", 2);

				// Create an instance of ChromeOptions
				ChromeOptions options = new ChromeOptions();

				// set ExperimentalOption - prefs
				options.setExperimentalOption("prefs", prefs);

				// Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize
				// chrome driver which will switch off this browser notification on the chrome
				// browser
				driver = new ChromeDriver(options);
				return driver;
			}
		}
	}

}
