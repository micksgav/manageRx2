/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : May 18, 2023
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
	}
	
	public Insurance() {
		this.company = null;
		this.insuranceNumber = 0;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public int getNumber() {
		return insuranceNumber;
	}
	
	public void setNumber(int insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
}
