/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 16, 2023
 * @Last Modified: December 16, 2023
 * @Description: Family doctor object containing information about the family doctor of a pharmacy patient
 ***********************************************
 */


package PatientManagement;

public class FamilyDoctor {
	private String name; // family doctor name
	private String address; // family doctor address
	private String faxNumber; // fax number
	private String phoneNumber; // family doctor phone number
	
	public FamilyDoctor(String name, String address, String phoneNumber, String faxNumber) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
	} // end FamilyDoctor
	
	public FamilyDoctor() {
		this.name = "";
		this.address = "";
		this.faxNumber = "";
		this.phoneNumber = "";
	} // end FamilyDoctor

	
	public String getFax() {
		return faxNumber;
	} // end getFax
	
	public void setFax(String faxNumber) {
		this.faxNumber = faxNumber;
	} // end setFax

	public String getName() {
		return name;
	} // end getName

	public void setName(String name) {
		this.name = name;
	} // end setName

	public String getAddress() {
		return address;
	} // end getAddress

	public void setAddress(String address) {
		this.address = address;
	} // end setAddress

	public String getPhoneNumber() {
		return phoneNumber;
	} // end getPhoneNumber

	public void setPhoneNumber(String phoneNumber) {

		this.phoneNumber = phoneNumber;
	} // end setPhoneNumber
} // end FamilyDoctor
