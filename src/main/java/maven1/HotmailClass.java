package maven1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HotmailClass {

	public void runDriver() {
		System.setProperty("webdriver.chrome.driver", ClassLoader.getSystemResource("chromedriver.exe").getPath());
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.hotmail.com");
	}
	
	
}
