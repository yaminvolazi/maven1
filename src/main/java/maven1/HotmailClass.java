package maven1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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



	public HotmailClass(String email, String password, String ip, String port, String proxylogin, String proxypass) {
		super();
		this.email = email;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.proxylogin = proxylogin;
		this.proxypass = proxypass;
	}

	public void runDriver() {
		System.setProperty("webdriver.chrome.driver", ClassLoader.getSystemResource("chromedriver.exe").getPath());
		driver = new ChromeDriver();
		driver.get("https://www.hotmail.com");
	}

	public void waiting() {
		try {
			while (this.driver.getCurrentUrl().length() > 1) {
				Thread.sleep(15000L);
			}
			this.driver.quit();
		} catch (Exception e) {
			try {
				this.driver.quit();
			} catch (Exception localException1) {
			}
		}
	}

	public boolean logIn() {
		if (this.driver != null) {
			System.out.println("Acces to hotmail");
			List<WebElement> catpchas;
			try {
				Thread.sleep(1000L);
				Thread.sleep(1000L);
				Thread.sleep(1000L);
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
					return false;
				}

				catpchas = this.driver.findElements(By.className("HipButton"));
			} catch (Exception e) {
				System.out.println("ERROR : LOGIN");
				this.driver.quit();
				return false;
			}
			return true;
		}
		System.out.println("Driver is null ");
		return false;
	}

	public void clickNext() {
		try {
			if (this.driver.getCurrentUrl().contains("https://account.live.com/tou/accrue?ru=")) {
				this.driver.findElement(By.id("iNext")).click();
				Thread.sleep(5000L);
			}
		} catch (Exception localException) {
		}

		if (!this.driver.findElements(By.id("iLandingViewAction")).isEmpty()) {
			try {
				this.driver.findElement(By.id("iLandingViewAction")).click();
				Thread.sleep(2000L);
				System.out.println("Click Suivant");
			} catch (Exception localException1) {
			}
		}
	}

	public void allSpamToInbox() {
		clickNext();
		try {
			System.out.println("In SpamToInbox 000");
			this.driver.get("https://blu180.mail.live.com/?fid=fljunk");
			Thread.sleep(3000L);
			WebElement NotSpam = null;
			WebElement chckALL = null;
			removepopUp();

			int range = Integer.parseInt(this.driver.findElement(By.id("mlRange")).getText().split(" ")[0]);
			System.out.println("Spam Range : " + range);
			int excepCounter = 0;
			while (range != 0) {
				System.out.println("Spam Range : " + range);
				int counter = 0;
				removepopUp();
				try {
					chckALL = this.driver.findElement(By.id("msgChkAll"));
					chckALL.click();
					Thread.sleep(1000L);
					while ((!this.driver.findElement(By.id("MarkAsNotJunk")).isDisplayed()) && (counter < 10)) {
						this.driver.findElement(By.tagName("body"))
								.sendKeys(new CharSequence[] { Keys.chord(new CharSequence[] { "s", "a" }) });
						counter++;
						Thread.sleep(1000L);
					}
					counter = 0;

					NotSpam = this.driver.findElement(By.id("MarkAsNotJunk"));
					NotSpam.click();
					Thread.sleep(5000L);
					while (!this.driver.findElements(By.cssSelector(".NotificationArea")).isEmpty()) {
						Thread.sleep(5000L);
						System.out.println("waiting..");
					}

					while (!this.driver.findElement(By.id("mlRange")).isDisplayed()) {
						Thread.sleep(500L);
						counter++;
						System.out.println("waiting for message range");
						if (counter == 30)
							break;
					}
					excepCounter = 0;
				} catch (Exception e) {
					e.printStackTrace();
					removepopUp();
					excepCounter++;
					if (excepCounter % 2 == 0) {
						this.driver.get("https://blu180.mail.live.com/?fid=fljunk");
						Thread.sleep(20000L);
					}
				}
				if (counter < 30)
					range = Integer.parseInt(this.driver.findElement(By.id("mlRange")).getText().split(" ")[0]);
				System.out.println(range);
				if (excepCounter == 5)
					break;
			}
			System.out.println("Moved mails to inbox from junk");
			Thread.sleep(2000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void spamToInbox() {
		try {
			System.out.println("In SpamToInbox");
			this.driver.get("https://blu180.mail.live.com/?fid=fljunk");
			Thread.sleep(3000L);
			WebElement NotSpam = null;
			WebElement chckALL = null;

			if (this.driver.findElements(By.className("UserArea")).isEmpty()) {
				try {
					((WebElement) this.driver.findElements(By.name("action")).get(0)).click();
				} catch (Exception localException1) {
				}
			}

			int range = Integer.parseInt(this.driver.findElement(By.id("mlRange")).getText().split(" ")[0]);
			System.out.println("Spam Range : " + range);
			int excepCounter = 0;
			while (range != 0) {
				int counter = 0;
				try {
					chckALL = this.driver.findElement(By.id("msgChkAll"));
					chckALL.click();

					while (!this.driver.findElement(By.id("MarkAsNotJunk")).isDisplayed()) {
						chckALL.click();
						Thread.sleep(1000L);
					}
					NotSpam = this.driver.findElement(By.id("MarkAsNotJunk"));
					NotSpam.click();

					while (!this.driver.findElement(By.id("mlRange")).isDisplayed()) {
						Thread.sleep(500L);
						counter++;
						if (counter == 30)
							break;
					}
					excepCounter = 0;
				} catch (Exception e) {
					e.printStackTrace();
					if (this.driver.findElements(By.className("UserArea")).isEmpty()) {
						try {
							((WebElement) this.driver.findElements(By.name("action")).get(0)).click();
						} catch (Exception localException2) {
						}
					}

					removepopUp();
					excepCounter++;
				}
				if (counter < 30)
					range = Integer.parseInt(this.driver.findElement(By.id("mlRange")).getText().split(" ")[0]);
				System.out.println(range);
				if (excepCounter == 5)
					break;
			}
			System.out.println("Moved mails to inbox from junk");
			Thread.sleep(2000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cleanInbox() {
		try {
			this.driver.get("https://blu180.mail.live.com/?fid=flinbox");
			Thread.sleep(1000L);
			List<WebElement> elements = new ArrayList(0);
			WebElement msg = null;

			if (this.driver.findElements(By.className("UserArea")).isEmpty()) {
				try {
					((WebElement) this.driver.findElements(By.name("action")).get(0)).click();
					System.out.println("remove pop up ");
					Thread.sleep(3000L);
				} catch (Exception e2) {
					System.out.println("1 ** " + e2.getMessage());
				}
			}

			int range = 1;
			int testing = 0;
			label627: while (range != 0) {
				try {
					elements = this.driver.findElements(By.className("Lt"));
					Thread.sleep(500L);
					msg = (WebElement) elements.get(0);

					msg.click();

					System.out.println("message opend...");
					Thread.sleep(1000L);

					int counter = 0;
					while (!this.driver.findElement(By.id("DeleteMessages")).isDisplayed()) {
						System.out.println("wating supButton");
						counter++;
						if (msg.isDisplayed())
							msg.click();
						Thread.sleep(300L);
						if (counter == 20) {
							break;
						}
					}
					try {
						this.driver.findElement(By.className("sfShowUnsafe")).click();
						System.out.println("show content");
						Thread.sleep(1500L);
					} catch (Exception e) {
						System.out.println("Can't found  show content link ");
					}
					try {
						this.driver.findElement(By.className("sfMarkAsSafe")).click();
						System.out.println("mark as safe message");
						Thread.sleep(1000L);
					} catch (Exception e) {
						System.out.println("Can't found  safly msg link ");
					}
					try {
						this.driver.findElement(By.className("sfMarkAsSafe")).click();
						System.out.println("add to contact ");
						Thread.sleep(1000L);
					} catch (Exception e) {
						System.out.println("Can't found  add to contact  link ");
					}

					this.driver.findElement(By.id("DeleteMessages")).click();

					System.out.println("Archiving...");
					((WebElement) this.driver.findElements(By.className("leftnavitem")).get(0)).click();

					Thread.sleep(3000L);
					testing = 0;
				} catch (Exception e) {
					System.err.println("refresh part ");
					this.driver.get("https://blu180.mail.live.com/?fid=flinbox");
					Thread.sleep(25000L);
					testing++;
					try {
						if (this.driver.findElements(By.className("UserArea")).isEmpty()) {
							try {
								((WebElement) this.driver.findElements(By.name("action")).get(0)).click();
								System.out.println("remove pop up ");
								Thread.sleep(3000L);
							} catch (Exception localException1) {
							}
						}

						System.out.println(testing + " is testing counter");
					} catch (Exception e0) {
						e0.printStackTrace();
					}

					if (testing != 5)
						break label627;
						
					elements.clear();
					break;
				}

				
				int ii = 0;
				while ((!this.driver.findElement(By.id("mlRange")).isDisplayed()) && (ii < 100)) {
					ii++;
					Thread.sleep(500L);
				}
				range = Integer.parseInt(this.driver.findElement(By.id("mlRange")).getText().split(" ")[0]);
				System.out.println(range + " is range ");
			}
			System.out.println("terminating...");
		} catch (Exception c) {
			c.printStackTrace();
		}
	}
	
	public void bye() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}

	public int spamToInbox365() {
		WebElement NotJunkButt = null;
		int reportCounter = 0;
		try {
			goToSpam365();

			List<WebElement> range = this.driver.findElements(
					By.cssSelector(".subfolders[role=group] > div:nth-child(2) >div div >span:nth-child(1)"));
			int globalError = 0;
			while ((range != null) && (((WebElement) range.get(1)).getText().length() > 0) && (globalError < 10)) {
				range = this.driver.findElements(
						By.cssSelector(".subfolders[role=group] > div:nth-child(2) >div div >span:nth-child(1)"));
				try {
					WebElement checkAll = this.driver.findElement(By.className("wf-size-checkboxMultiselectSize"));
					JavascriptExecutor js = (JavascriptExecutor) this.driver;
					js.executeScript("arguments[0].click();", new Object[] { checkAll });
					Thread.sleep(1250L);

					List<WebElement> MSGS = this.driver
							.findElements(By.cssSelector(".conductorContent .flex div[id^='_aria']"));

					WebElement MSG = null;
					for (WebElement e : MSGS) {
						if (e.isDisplayed()) {
							try {
								MSG = e;
							} catch (Exception e2) {
								System.out.println("can not click on msg ");
								e2.printStackTrace();
							}
						}
					}
					Thread.sleep(1000L);

					int countSelected = countSelected365();
					System.out.println("selected in spam " + countSelected);

					if (countSelected <= 0) {
						goToInbox365();
						goToSpam365();
						try {
							range = this.driver.findElements(By.cssSelector(
									".subfolders[role=group] > div:nth-child(1) >div div >span:nth-child(1)"));
						} catch (Exception localException2) {
						}

						globalError++;
						if (globalError % 3 == 0) {
							goToInboxByLink365();
							goToSpam365();
							range = this.driver.findElements(By.cssSelector(
									".subfolders[role=group] > div:nth-child(2) >div div >span:nth-child(1)"));
						}
					} else {
						try {
							if (countSelected > 1)
								MSG.click();
						} catch (Exception e) {
							removepopUp();
							System.out.println("3lach " + e.getMessage());
						}
						Thread.sleep(1000L);

						NotJunkButt = this.driver.findElement(By.cssSelector(
								".ms-bg-color-themeLighterAlt div :nth-child(10) div button:nth-child(1)"));
						if (!NotJunkButt.isDisplayed()) {
							NotJunkButt = this.driver.findElement(By.cssSelector(
									".ms-bg-color-themeLighterAlt div :nth-child(9) div button:nth-child(1)"));
						}
						NotJunkButt.click();
						reportCounter += countSelected;
						globalError = 0;
						NotJunkButt = null;

						Thread.sleep(1000L);

						range = this.driver.findElements(By
								.cssSelector(".subfolders[role=group] > div:nth-child(2) >div div >span:nth-child(1)"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Can not select all ");
					globalError++;
					this.driver.findElement(By.cssSelector(".subfolders[role=group] > div:nth-child(1)")).click();
					Thread.sleep(1000L);
					this.driver.findElement(By.cssSelector(".subfolders[role=group] > div:nth-child(2)")).click();
					Thread.sleep(3000L);
					range = this.driver.findElements(
							By.cssSelector(".subfolders[role=group] > div:nth-child(2) >div div >span:nth-child(1)"));
				}
			}

			return reportCounter;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reportCounter;
	}

	public int countSelected365() {
		try {
			List<WebElement> els = this.driver.findElements(By.cssSelector("button[aria-checked='true']"));
			return els.size() - 1;
		} catch (Exception e) {
			System.err.println("ERRORS::" + e.getMessage());
		}
		return -1;
	}

	public void goToInbox365() {
		int maxRepeat = 15;
		while (maxRepeat > 0) {
			maxRepeat--;
			try {
				Thread.sleep(3000L);
				this.driver.findElement(By.cssSelector(".subfolders[role=group] > div:nth-child(1)")).click();
				Thread.sleep(3000L);
			} catch (Exception e) {
				removepopUp();
			}
		}
	}

	public void goToInboxByLink365() {
		try {
			this.driver.get("https://outlook.live.com/owa/?path=/mail/inbox");
			int c0 = 0;
			while ((this.driver.findElements(By.cssSelector(".subfolders[role=group] > div:nth-child(2)")).size() == 0)
					&& (c0 < 120)) {
				Thread.sleep(1000L);
				System.out.println("LOADING INBOX..");
				c0++;
			}
			Thread.sleep(10000L);
		} catch (Exception localException) {
		}
	}

	public void goToSpam365() {
		try {
			int c0 = 0;
			while ((this.driver.findElements(By.cssSelector(".subfolders[role=group] > div:nth-child(2)")).size() == 0)
					&& (c0 < 60)) {
				Thread.sleep(2000L);
				removepopUp();
				c0++;
			}
			Thread.sleep(5000L);
			if (c0 == 60)
				return;
			while (!this.driver.getCurrentUrl().contains("/mail/junkemail")) {
				Thread.sleep(3000L);
				try {
					this.driver.findElement(By.cssSelector(".subfolders[role=group] > div:nth-child(2)")).click();
				} catch (Exception e) {
					System.out.println("Waiting for Junk Email menu item ");
					removepopUp();
				}
			}
		} catch (Exception localException1) {
		}
	}

	public void removepopUp() {
		clickNext();
		try {
			while (!this.driver.findElements(By.className("dialog")).isEmpty()) {
				System.out.println("DIALOG");
				try {
					System.out.println("DIALOG + NEXT");
					Thread.sleep(800L);
					this.driver.findElement(By.className("nextButton")).click();
					Thread.sleep(2300L);
				} catch (Exception localException1) {
				}
				try {
					System.out.println("DIALOG + finalize OK");
					Thread.sleep(800L);
					this.driver.findElement(By.className("goButton")).click();
					Thread.sleep(10000L);
				} catch (Exception localException2) {
				}
			}
		} catch (Exception localException3) {
		}

		System.out.println("REMOVE POPUP FUNCTIONs");
		try {
			try {
				List<WebElement> els = this.driver.findElements(By.className("ms-Icon--x"));
				for (WebElement e : els) {
					try {
						e.click();
					} catch (Exception localException4) {
					}
				}
			} catch (Exception localException5) {
			}

			if (!this.driver.findElements(By.className("UserArea")).isEmpty()) {
				try {
					((WebElement) this.driver.findElements(By.name("action")).get(0)).click();
					System.out.println("remove pop up ");
					Thread.sleep(3000L);
				} catch (Exception localException6) {
				}
			}

			if (!this.driver.findElements(By.className("__Microsoft_Owa_ConsumerFirstRun_templates_cs_4")).isEmpty()) {
				this.driver.findElement(By.className("__Microsoft_Owa_ConsumerFirstRun_templates_cs_4")).click();
				Thread.sleep(1000L);
			}
		} catch (Exception localException7) {
		}

		if (!this.driver.findElements(By.className("__Microsoft_Owa_Feedback_templates_cs_m")).isEmpty()) {
			try {
				this.driver.findElement(By.className("__Microsoft_Owa_Feedback_templates_cs_r")).click();
				Thread.sleep(1000L);
			} catch (Exception localException8) {
			}
		}
		if (!this.driver.findElements(By.className("__Microsoft_Owa_Feedback_templates_cs_a")).isEmpty()) {
			try {
				this.driver.findElement(By.className("__Microsoft_Owa_Feedback_templates_cs_a")).click();
				Thread.sleep(1000L);
				this.driver.navigate().refresh();
				Thread.sleep(10000L);
			} catch (Exception localException9) {
			}
		}

		if (!this.driver.findElements(By.className("pickerPopUp ")).isEmpty()) {
			try {
				System.out.println("popup");
				this.driver.findElement(By.className("ms-fcl-b-b")).click();
				Thread.sleep(1000L);
				this.driver.navigate().refresh();
				Thread.sleep(10000L);
			} catch (Exception localException10) {
			}
		}

		if (this.driver.findElements(By.cssSelector(".OutlookAppUpsellFrame")).size() > 0) {
			try {
				this.driver.switchTo().frame(this.driver.findElement(By.cssSelector(".OutlookAppUpsellFrame")))
						.findElement(By.linkText("No thanks")).click();
				this.driver.switchTo().defaultContent();
			} catch (Exception localException11) {
			}
		}

		if (!this.driver.findElements(By.cssSelector(".popupShadow .ms-bgc-tp")).isEmpty()) {
			try {
				List<WebElement> elements = this.driver.findElements(By.cssSelector(".popupShadow .ms-bgc-tp"));
				for (WebElement webElement : elements) {
					if (webElement.isDisplayed())
						webElement.click();
				}
			} catch (Exception e) {
				System.err.println("POP UP EXCEPTION :" + e.getMessage());
			}
		}

		if (!this.driver.findElements(By.cssSelector(".popupShadow .ms-Icon--x")).isEmpty()) {
			try {
				List<WebElement> elements = this.driver.findElements(By.cssSelector(".popupShadow .ms-Icon--x"));
				for (WebElement webElement : elements) {
					if (webElement.isDisplayed())
						webElement.click();
				}
			} catch (Exception e) {
				System.err.println("POP UP EXCEPTION :" + e.getMessage());
			}
		}
		if (!this.driver.findElements(By.cssSelector(".popupShadow .o365buttonOutlined")).isEmpty()) {
			try {
				this.driver.findElement(By.cssSelector(".popupShadow .o365buttonOutlined")).click();
				Thread.sleep(1000L);
			} catch (Exception localException12) {
			}
		}

		if (this.driver.getCurrentUrl().toLowerCase().contains("languageselection.aspx")) {
			try {
				int random = new Random().nextInt(20) + 1;
				int k = 0;
				while (k < random) {
					this.driver.findElement(By.id("selTz")).sendKeys(new CharSequence[] { Keys.DOWN });
					Thread.sleep(300L);
					k++;
				}
				Thread.sleep(1000L);
				this.driver.findElement(By.className("signinTxt")).click();
				Thread.sleep(5000L);
			} catch (Exception e) {
				System.out.println("SELECT TIME ZONE FAILED");
			}
		}
	}
	
	public Map<String, Integer> markAsRead_ArchiveOrDelete365(boolean archive, int replyTime) {
		System.out.println("PROCESS TO INBOX");
		Map<String, Integer> actionCounter = new HashMap();
		actionCounter.put("archived", Integer.valueOf(0));
		actionCounter.put("deleted", Integer.valueOf(0));

		try {
			this.driver.findElement(By.cssSelector(".subfolders[role=group] > div:nth-child(1)")).click();
			Thread.sleep(3000L);

			Thread.sleep(1000L);
			List<WebElement> range = this.driver.findElements(
					By.cssSelector(".subfolders[role=group] > div:nth-child(1) >div div >span:nth-child(1)"));

			boolean version0 = true;
			if (this.driver.findElements(By.className("vResize")).size() < 2)
				version0 = false;
			int globalError = 0;
			while ((((WebElement) range.get(1)).getText().length() > 0) && (globalError < 30) && (replyTime > 0)) {
				try {
					if ((globalError == 15) && (archive)) {
						archive = false;
						globalError = 0;
					}
					if (globalError == 15) {
						break;
					}

					WebElement checkAll = this.driver.findElement(By.className("wf-size-checkboxMultiselectSize"));
					JavascriptExecutor js = (JavascriptExecutor) this.driver;
					js.executeScript("arguments[0].click();", new Object[] { checkAll });
					Thread.sleep(1250L);

					int countSelected = countSelected365();
					System.out.println("SELECTED " + countSelected + " , total reported =" + actionCounter);

					if ((countSelected <= 0) && (range != null)
							&& (((WebElement) range.get(1)).getText().length() > 0)) {
						goToInbox365();
						goToSpam365();
						try {
							range = this.driver.findElements(By.cssSelector(
									".subfolders[role=group] > div:nth-child(1) >div div >span:nth-child(1)"));
						} catch (Exception localException1) {
						}

						globalError++;
						if (globalError % 5 == 0) {
							goToInboxByLink365();
							goToSpam365();
							range = this.driver.findElements(By.cssSelector(
									".subfolders[role=group] > div:nth-child(2) >div div >span:nth-child(1)"));
						}
						range = this.driver.findElements(By
								.cssSelector(".subfolders[role=group] > div:nth-child(2) >div div >span:nth-child(1)"));
						continue;
					}
					clickFisrt();
					boolean markedAsRead = false;
					int c1 = 0;
					if ((!markedAsRead) && (c1 < 10)) {
						try {
							Thread.sleep(2000L);
							if (version0) {
								System.out.println("VERSION 0 ");
								try {
									this.driver.findElement(By.className("ms-Icon--mailOpen")).click();
									Thread.sleep(1000L);
									markedAsRead = true;
									c1 = 0;
									removepopUp();
								} catch (Exception e) {
									removepopUp();
									System.out.println("Condition  if not valide ");
									this.driver.get("https://outlook.live.com/owa/?path=/mail/inbox");
									goToInbox365();
									c1++;
								}
							} else {
								System.out.println("VERSION 1 ");
								try {
									clickFisrt();
									this.driver.switchTo().activeElement()
											.sendKeys(new CharSequence[] { Keys.chord(new CharSequence[] { "q" }) });
									Thread.sleep(1000L);
									markedAsRead = true;
									System.out.println("MARKED AS READ ");
									removepopUp();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							Thread.sleep(3000L);
						} catch (Exception e) {
							System.err.println("MARK AS READ ERROR " + e.getMessage());
							c1++;
						}
					}

					if (archive) {
						checkAll = this.driver.findElement(By.className("wf-size-checkboxMultiselectSize"));
						js = (JavascriptExecutor) this.driver;
						js.executeScript("arguments[0].click();", new Object[] { checkAll });
						clickFisrt();
						this.driver.findElement(By.cssSelector(".ms-bg-color-themeLighterAlt .ms-Icon--archive"))
								.click();
						actionCounter.put("archived", Integer
								.valueOf(((Integer) actionCounter.get("archived")).intValue() + countSelected365()));
						replyTime--;
					} else {
						checkAll = this.driver.findElement(By.className("wf-size-checkboxMultiselectSize"));

						js = (JavascriptExecutor) this.driver;
						js.executeScript("arguments[0].click();", new Object[] { checkAll });
						clickFisrt();
						this.driver.findElement(By.cssSelector(".ms-bg-color-themeLighterAlt .ms-Icon--trash "))
								.click();
						actionCounter.put("deleted", Integer
								.valueOf(((Integer) actionCounter.get("deleted")).intValue() + countSelected365()));
						replyTime--;
					}
					removepopUp();
					Thread.sleep(2000L);
				} catch (Exception e) {
					globalError++;
					removepopUp();
					e.printStackTrace();
					goToInboxByLink365();
				}

				range = this.driver.findElements(
						By.cssSelector(".subfolders[role=group] > div:nth-child(1) >div div >span:nth-child(1)"));
			}
			Thread.sleep(5000L);
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("clean box ");
		return actionCounter;
	}
	
	public void clickFisrt() {
		try {
			try {
				List<WebElement> MSGS = this.driver
						.findElements(By.cssSelector(".conductorContent .flex div[id^='_aria']"));

				WebElement MSG = null;
				for (WebElement e : MSGS) {
					if (e.isDisplayed())
						try {
							MSG = e;
						} catch (Exception e2) {
							System.out.println("can not click on msg ");
							e2.printStackTrace();
						}
				}
				Thread.sleep(1000L);
				int countSelected = countSelected365();
				if (countSelected > 1) {
					MSG.click();
				}
			} catch (Exception e) {
				removepopUp();
				System.out.println("3lach " + e.getMessage());
			}
			Thread.sleep(1000L);
		} catch (Exception localException1) {
		}
	}

}
