/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 16, 2023
 * @Last Modified: December 16, 2023
 * @Description: Insurance object containing information for the insurance of a pharmacy patient
 ***********************************************
 */


package PatientManagement;

public class Insurance {
	private String company; // insurance company
	private int insuranceNumber; // insurance number
	private String notes; // notes about insurance
	
	public Insurance(String company, int insuranceNumber, String notes) {
		this.company = company;
		this.insuranceNumber = insuranceNumber;
		this.notes = notes;
	} // end Insurance
	
	public Insurance() {
		this.company = "";
		this.insuranceNumber = 0;
	} // end Insurance
	
	public String getCompany() {
		return company;
	} // end getCompany
	
	public void setCompany(String company) {
		this.company = company;
	} // end setCompany
	
	public int getNumber() {
		return insuranceNumber;
	} // end getNumber
	
	public void setNumber(int insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	} // end setNumber
	
	public void setNotes(String notes) {
		this.notes = notes;
	} // end setNotes
	
	public String getNotes() {
		return notes;
	} // end getNotes
	
} // end Insurance