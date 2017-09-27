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
				HotmailClass hotmailClass = new HotmailClass(email,passwd);
				hotmailClass.runDriver();
				System.out.println("driver start");
				hotmailClass.logIn();
				hotmailClass.goToSpam365();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Cannot get data");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
