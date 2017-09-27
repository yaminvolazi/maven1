package maven1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HotmailClass {

	WebDriver driver = null;
	private String email;
	private String password;
	private String ip;
	private String port;
	private String proxylogin;
	private String proxypass;

	public HotmailClass(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public void runDriver() {
		System.setProperty("webdriver.chrome.driver", ClassLoader.getSystemResource("chromedriver.exe").getPath());
		driver = new ChromeDriver();
		driver.get("https://www.hotmail.com");
	}

	public void logIn() {
		if (this.driver != null) {
			System.out.println("Acces to hotmail");
			List<WebElement> catpchas;
			try {
				WebElement Email = this.driver.findElement(By.name("loginfmt"));
				Email.sendKeys(new CharSequence[] { this.email });
				WebElement Passwd = this.driver.findElement(By.name("passwd"));
				if (!Passwd.isDisplayed()) {
					Email.sendKeys(new CharSequence[] { Keys.ENTER });
					int counter = 0;
					Passwd = this.driver.findElement(By.name("passwd"));
					while ((!Passwd.isDisplayed()) && (counter < 10)) {
						counter++;
						Thread.sleep(2000L);
						System.out.println("waiting ");
						Passwd = this.driver.findElement(By.name("passwd"));
					}
					Thread.sleep(3000L);
				}
				try {
					Passwd.sendKeys(new CharSequence[] { this.password + Keys.ENTER });
					Thread.sleep(1000L);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("ERROR : LOGIN");
				}

				catpchas = this.driver.findElements(By.className("HipButton"));
			} catch (Exception e) {
				System.out.println("ERROR : LOGIN");
				this.driver.quit();
			}
		}
	}

	public void goToSpam365() {
		try {
			int c0 = 0;
			while ((this.driver.findElements(By.cssSelector(".subfolders[role=group] > div:nth-child(2)")).size() == 0)
					&& (c0 < 60)) {
				Thread.sleep(2000L);
				//removepopUp();
				c0++;
			}
			Thread.sleep(2000L);
			if (c0 == 60)
				return;
			while (!this.driver.getCurrentUrl().contains("/mail/junkemail")) {
				Thread.sleep(3000L);
				try {
					this.driver.findElement(By.cssSelector(".subfolders[role=group] > div:nth-child(2)")).click();
				} catch (Exception e) {
					System.out.println("Waiting for Junk Email menu item ");
					//removepopUp();
				}
			}
		} catch (Exception localException1) {
		}
	}

}
