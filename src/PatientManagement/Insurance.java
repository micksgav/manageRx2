/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 16, 2023
 * @Last Modified: January 21, 2024
 * @Description: Insurance object containing information for the insurance of a pharmacy patient
 ***********************************************
 */

package PatientManagement;

public class Insurance {
	private String company; // insurance company
	private int insuranceNumber; // insurance number
	private int ID; // id for sql
	private int patientID; // patient id for sql
	private boolean delete; // for deleting the plan

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
	} // end setID

	public int getID() {
		return ID;
	} // end getID

	public void setPatientID(int ID) {
		patientID = ID;
	} // end setPatientID

	public int getPatientID() {
		return patientID;
	} // end getPatientID

	public boolean getDelete() {
		return delete;
	} // end getDelete

	public void setDelete(boolean delete) {
		this.delete = delete;
	} // end setDelete

} // end Insurance