package maven1.utils;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriverService.Builder;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverSetting {
	public static int i;
	static String[] agent = { "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Dragon/52.15.25.664 Chrome/52.0.2743.82 Safari/537.36",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0 SeaMonkey/2.32.1",
			"Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36" };

	public static WebDriver connectUsingProxy() {
		i += 1;
		if (i == Proxies.list.size())
			i = 0;
		WebDriver driver = null;
		boolean proxyBlocked = false;
		ChromeDriverService service =

				(ChromeDriverService) ((ChromeDriverService.Builder) ((ChromeDriverService.Builder) ((ChromeDriverService.Builder) new ChromeDriverService.Builder()
						.usingDriverExecutable(new File("Resources/chromedriver.exe"))).usingAnyFreePort())
								.withEnvironment(com.google.common.collect.ImmutableMap.of("DISPLAY", ":0.0"))).build();

		try {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addExtensions(new File[] { new File(((Proxy) Proxies.list.get(i)).getPath()) });
			capabilities.setCapability("chromeOptions", options);

			driver = new ChromeDriver(service, capabilities);
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(120L, java.util.concurrent.TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(1248, 800));
			try {
				if (driver.findElement(org.openqa.selenium.By.id("main-frame-error")) != null) {
					proxyBlocked = true;
					LogWriter.write(LogFiles.BadProxy.name(), ((Proxy) Proxies.list.get(i)).toString());
				}
			} catch (Exception e) {
				LogWriter.write(LogFiles.GoodProxy.name(), ((Proxy) Proxies.list.get(i)).toString());
			}

			System.out.println(proxyBlocked);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				driver.close();
			} catch (Exception localException1) {
			}

			driver = null;
		}

		int j = 0;
		while (proxyBlocked) {
			i += 1;
			j++;
			if (i == Proxies.list.size())
				i = 0;
			if (driver != null) {
				driver.quit();
			}
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addExtensions(new File[] { new File(((Proxy) Proxies.list.get(i)).getPath()) });
			capabilities.setCapability("chromeOptions", options);
			driver = new ChromeDriver(service, capabilities);
			driver.manage().window().setSize(new Dimension(1248, 800));
			driver.manage().timeouts().pageLoadTimeout(60L, java.util.concurrent.TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();
			try {
				if (driver.findElement(org.openqa.selenium.By.id("main-frame-error")) != null) {
					proxyBlocked = true;
					LogWriter.write(LogFiles.BadProxy.name(), ((Proxy) Proxies.list.get(i)).toString());
				}
			} catch (Exception e) {
				LogWriter.write(LogFiles.MyExecptions.name(), "\n\n\n" + e.getMessage() + "\n\n\n");
				proxyBlocked = false;
				LogWriter.write(LogFiles.GoodProxy.name(), ((Proxy) Proxies.list.get(i)).toString());
			}
			if (j == Proxies.list.size()) {
				LogWriter.write(LogFiles.LimetedProxy.name(), "*** ALL PROXY ARE LIMITED ***");
				break;
			}
		}
		try {
			System.out.println("Testing driver ");
			driver.get("http://checkip.amazonaws.com");
		} catch (Exception e) {
			System.out.println("Test Failed! Closing driver." + e.toString() + "---" + e.getLocalizedMessage() + "--->"
					+ e.getMessage());
			try {
				driver.close();
			} catch (Exception localException2) {
			}

			return null;
		}
		return driver;
	}

	public static synchronized WebDriver connectUsingProxy(String ip, String port, String login, String pass) {
		WebDriver driver = null;
		Proxy prox = new Proxy();
		prox.setIP(ip);
		prox.setPasswd(pass);
		prox.setPort(port);
		prox.setUsername(login);
		prox.generateZip();

		ChromeDriverService service =

				(ChromeDriverService) ((ChromeDriverService.Builder) ((ChromeDriverService.Builder) ((ChromeDriverService.Builder) new ChromeDriverService.Builder()
						.usingDriverExecutable(new File("Resources/chromedriver.exe"))).usingAnyFreePort())
								.withEnvironment(com.google.common.collect.ImmutableMap.of("DISPLAY", ":0.0"))).build();

		try {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addExtensions(new File[] { new File(prox.getPath()) });

			java.util.Random r = new java.util.Random();
			String userAgent = agent[(r.nextInt(50) % agent.length)];
			System.out.println("USER AGENT " + userAgent);
			options.addArguments(new String[] { "--user-agent=" + userAgent });

			capabilities.setCapability("chromeOptions", options);

			driver = new ChromeDriver(service, capabilities);
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(180L, java.util.concurrent.TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(1248, 800));
		} catch (Exception e) {
			e.printStackTrace();
			driver = null;
		}
		try {
			System.out.println("Testing driver ");
			driver.get("http://checkip.amazonaws.com");
			LogWriter.write(LogFiles.GoodProxy.name(), prox.toString());
		} catch (Exception e) {
			System.out.println("Test Failed! Closing driver." + e.getMessage());
			LogWriter.write(LogFiles.BadProxy.name(), prox.toString());
			driver.quit();
			return null;
		}
		return driver;
	}

	public static WebDriver connectWithoutProxy() {
		WebDriver driver = null;

		ChromeDriverService service =

				(ChromeDriverService) ((ChromeDriverService.Builder) ((ChromeDriverService.Builder) ((ChromeDriverService.Builder) new ChromeDriverService.Builder()
						.usingDriverExecutable(new File("Resources/chromedriver.exe"))).usingAnyFreePort())
								.withEnvironment(com.google.common.collect.ImmutableMap.of("DISPLAY", ":0.0"))).build();
		driver = new ChromeDriver(service);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60L, java.util.concurrent.TimeUnit.SECONDS);
		driver.manage().window().setSize(new Dimension(1248, 800));

		return driver;
	}
}
