package maven1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSelenium {

	public static void main(String[] args) {

		System.out.println("#####  MainSelenium  #####");

		ExecutorService taskExecutor = null;
		FileInputStream ips;
		HotmailClass hotmailClass = null;
		try {
			ips = new FileInputStream(ClassLoader.getSystemResource("Accounts").getPath());
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			
			while ((ligne = br.readLine()) != null) {
				System.out.println("reporting");
				System.out.println(ligne);
				String[] data = ligne.split(";");
				String email = data[0].replaceAll("\"", "");
				String passwd = data[1].replaceAll("\"", "");
				hotmailClass = new HotmailClass(email, passwd);
			}

			hotmailClass.runDriver();
			System.out.println("driver start");
			boolean process = hotmailClass.logIn();
			System.out.println("LOGGED IN " + process);
			Thread.sleep(2000L);

			if (process) {
				hotmailClass.removepopUp();
				hotmailClass.clickNext();
				hotmailClass.removepopUp();
				Thread.sleep(3000L);
				// hotmailClass.goToSpam365();
				if (hotmailClass.driver.getCurrentUrl().contains("/owa/")) {
					hotmailClass.spamToInbox365();
				}

				Thread.sleep(3000L);
			}
			Thread.sleep(3000L);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot get data");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			hotmailClass.bye();
		} catch (Exception e) {
			System.out.println("Can not quit the driver because " + e.getMessage());
		}

	}

}
