/**
***********************************************
 @Name: Drug
 @Author           : Christina Wong
 @Creation Date    : December 12, 2023
 @Modified Date	   : January 10, 2024
   @Description    : 
   
***********************************************
*/
package inventory;
import java.io.IOException;

import utilities.getInteractions;


public class Drug {
	private String drugName; // brand name of drug
	private String drugClass; // drug class
	private String[] sideEffects; // drug side effects
	private String DIN; // drug identification number
	private final String schedule; // drug schedule
	private final String company; // drug company

	private final String description; // drug description

	private String form; // drug form

	private final String dosage; // dosage of drug, column 0 is dosage amount, column 1 is dosage unit

	public Drug(String drugDIN, String brandName, String classDrug, String schedule, String company, String description, String form, String dosage) {
		drugName = brandName;
		drugClass = classDrug;
		DIN = drugDIN;
		this.schedule = schedule;
		this.company = company;
		this.description = description;
		this.form = form;
		this.dosage = dosage;
	} // end Drug constructor

	public Drug() {
		drugName = "";
		drugClass = "";
		sideEffects = null;
		DIN = "";
		schedule = "";
		company = "";
		description = "";
		form = "";
		dosage = null;
	} // end blank constructor

	public String getDrugName() {
		return drugName;
	} // end getDrugName

	public void setDrugName(String brandName) {
		this.drugName = brandName;
	} // end setDrugNameBrand
	
	public String getForm() {
		return form;
	}
	
	public void setForm(String form) {
		this.form = form;
	}

	public String getDrugClass() {
		return drugClass;
	} // end getDrugClass

	public void setDrugClass(String classDrug) {
		this.drugClass = classDrug;
	} // end setDrugClass

	public String getDosage() {
		return dosage;
	} // end getDrugDosage

	public String[] getSideEffects() {
		return sideEffects;
	} // end getSideEffects

	public void setSideEffects(String[] drugSideEffects) {
		this.sideEffects = new String[drugSideEffects.length];
		for(int i = 0; i < drugSideEffects.length; i++) {
			this.sideEffects[i] = drugSideEffects[i];
		} // end for
	} // end setSideEffects

	// returns formatted drug DIN
	public String getDIN() {
		return DIN;
	} // end getDIN

	public void setDIN(String drugDIN) {
		DIN = drugDIN;
	} // end setDIN

	/** Method Name: checkInteractions
	* @Author Christina Wong
	* @Date December 18, 2023
	* @Modified December 22, 2023
	* @Description This compares the current Drug object with another to check for and print any interactions.
	* @Parameters  Drug drug2, the second drug to compare to the current drug for drug-drug interactions
	* @Returns void
	* Dependencies: Drug, getInteractions
	* Throws/Exceptions: IOException
    */
	public void checkInteractions(Drug drug2) throws IOException {
		String din2 = drug2.getDIN();
		String[] interactions = getInteractions.search(this.DIN, din2);
		for(int i = 0; i < interactions.length; i++) {
			System.out.println(interactions[i]);
		} // end for

	} // end checkInteractions
} // end Drug