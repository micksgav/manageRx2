package patientUI;

import java.io.IOException;

public class altsTestMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Example for interactions:
		String[] drugs = new String[]{"02248808", "00548359", "00888346", "02132702", "02163764"}; //adderall, xanax, fentanyl, zoloft, imitrix
;		
		System.out.println("Opening Alt Selection UI");
		System.out.println("Selected Alt: " + SelectAlternativeUI.getAltSelection("02248808")); //adderall
		System.out.println("---\nOpening Interactions Display");
		InteractionsUI UI = new InteractionsUI(drugs);
		System.out.println("---\nOpening Alts Display");
		AlternativesUI UI2 = new AlternativesUI("02248808"); //adderall		
		
		
	}

}
