package maven1;

public class MainSelenium {

	public static void main(String[] args) {
		
		System.out.println("#####  MainSelenium  #####");
			
		HotmailClass hotmailClass = new HotmailClass("yamin.volazi@outlook.com","*3yaminvolazi3*");
		hotmailClass.runDriver();
		System.out.println("driver start");
		hotmailClass.logIn();
		hotmailClass.goToSpam365();
	}

}
