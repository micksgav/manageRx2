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
	private int ID;
	private int patientID;
	private boolean delete;
	
	public Insurance(String company, int insuranceNumber, int id) {
		this.company = company;
		this.insuranceNumber = insuranceNumber;
		this.patientID = id;
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

	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}

	public void setPatientID(int ID) {
		patientID = ID;
	}
	
	public int getPatientID() {
		return patientID;
	}
	
	public boolean getDelete() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
} // end Insurance