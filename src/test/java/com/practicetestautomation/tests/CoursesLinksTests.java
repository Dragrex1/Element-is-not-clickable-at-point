package com.practicetestautomation.tests;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CoursesLinksTests {
	private WebDriver driver;

	@Test
	public void teachableLinkTest() {
		// 1 - Open courses page
		driver.get("https://practicetestautomation.com/courses/");
		sleep(17000);

		// 2 - Click on Enroll for just $9.99 on Teachable button
		driver.findElement(
				By.xpath("//img[@alt='Selenium WebDriver with Java for beginners']"))
				.click();
		sleep(5000);
		
		// Verify there are two tabs
		Set<String> handles = driver.getWindowHandles();
		Assert.assertTrue(handles.size() == 2, "There should be 2 tabs opened, but there is only 1");
		
		// Switch to a new tab
		switchToWindow();

		// Verify new window url
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.contains("sso.teachable.com/secure/971788/checkout"),
				"URL is not expected. URL is: " + url);
	}
	
	public void switchToWindow() {
		// Switching to new window
		String firstWindow = driver.getWindowHandle();

		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();

		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		// Start driver
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		this.driver = new ChromeDriver();
		//driver.manage().window().setSize(new Dimension(1000,1080));
		driver.manage().window().setSize(new Dimension(1920,1080));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
