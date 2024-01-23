/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 16, 2023
 * @Last Modified: January 21, 2024
 * @Description: Prescription object containing information about a prescription belonging to a pharmacy patient
 ***********************************************
 */

package patientManagement;

import inventory.*;

public class Prescription {
	private Drug drug; // prescribed drug
	private String datePrescribed; // prescribed date for the drug
	private int numRefills; // number of refills left
	private int quantity; // number of pills if applicable
	private String dosage; // dosage of the drug
	private String instructions; // instructions for taking the prescription
	private String prescribedDuration; // duration to take prescribed drug for
	private int ID; // id for sql
	private int patientID; // patient id for sql
	private boolean current; // if the prescription is current or not, used for sql
	private String docPrescribedName; // name of doctor who prescribed meds
	private String docPrescribedAddress; // address of doctor who prescribed meds
	private String docPrescribedPhone; // phone number of doctor who prescribed meds
	private String docPrescribedFax; // fax number of doctor who prescribed meds
	private boolean delete; // used for archiving a prescription from the current prescriptions page

	public Prescription(Drug drug, String datePrescribed, int numRefills, int quantity, String dosage,
			String instructions, String prescribedDuration, String docPrescribedName, String docPrescribedAddress,
			String docPrescribedPhone, String docPrescribedFax) {
		this.drug = drug;
		this.datePrescribed = datePrescribed;
		this.numRefills = numRefills;
		this.quantity = quantity;
		this.dosage = dosage;
		this.instructions = instructions;
		this.prescribedDuration = prescribedDuration;
		this.docPrescribedName = docPrescribedName;
		this.docPrescribedAddress = docPrescribedAddress;
		this.docPrescribedPhone = docPrescribedPhone;
		this.docPrescribedFax = docPrescribedFax;
		current = true;
	} // end Prescription

	public Prescription() {
		drug = new Drug();
		current = true;
	} // end Prescription

	public void setDocName(String docName) {
		docPrescribedName = docName;
	} // end setDocName

	public String getDocName() {
		return docPrescribedName;
	} // end getDocName

	public void setDocAddress(String address) {
		docPrescribedAddress = address;
	} // end setDocAddress

	public String getDocAddress() {
		return docPrescribedAddress;
	} // end getDocAddress

	public void setDocPhone(String phone) {
		docPrescribedPhone = phone;
	} // end setDocPhone

	public String getDocPhone() {
		return docPrescribedPhone;
	} // end getDocPhone

	public void setDocFax(String fax) {
		docPrescribedFax = fax;
	} // end setDocFax

	public String getDocFax() {
		return docPrescribedFax;
	} // end getDocFax

	public Drug getDrug() {
		return drug;
	} // end getDrug

	public String getName() {
		return drug.getDrugName();
	} // end getName

	public void setName(String genName) {
		drug.setDrugName(genName);
	} // end setName

	public String getDIN() {
		return drug.getDIN();
	} /// end getDIN

	public void setDIN(String DIN) {
		drug.setDIN(DIN);
	} // end setDIN

	public String getBrandName() {
		return drug.getDrugName();
	} // end getBrandNamew

	public void setBrandName(String brandName) {
		drug.setDrugName(brandName);
	} // end setBrandName

	public String getDate() {
		return datePrescribed;
	} // end getDate

	public void setDate(String date) {
		datePrescribed = date;
	} // end setDate

	public int getRefills() {
		return numRefills;
	} // end getRefills

	public void setRefills(int refills) {
		numRefills = refills;
	} // end setRefills

	public int getQuantity() {
		return quantity;
	} // end getQuantity

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	} // end setQuantity

	public String getDosage() {
		return dosage;
	} // end getDosage

	public void setDosage(String dosage) {
		this.dosage = dosage;
	} // end setDosage

	public String getInstructions() {
		return instructions;
	} // end getInstructions

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	} // end setInstructions

	public String getDuration() {
		return prescribedDuration;
	} // end getDuration

	public void setDuration(String duration) {
		prescribedDuration = duration;
	} // end setDuration

	public int getID() {
		return ID;
	} // end getID

	public void setID(int ID) {
		this.ID = ID;
	} // end setID

	public String getForm() {
		return drug.getForm();
	} // end getForm

	public void setForm(String form) {
		drug.setForm(form);
	} // end setForm

	public int getPatientID() {
		return patientID;
	} // end getPatientID

	public void setPatientID(int ID) {
		patientID = ID;
	} // end setPatientID

	public void setCurrent(int current) {
		if (current == 1) {
			this.current = true;
		} // end if
		else {
			this.current = false;
		} // end else
	} // end setCurrent

	public boolean getCurrent() {
		return current;
	} // end getCurrent

	public boolean getDelete() {
		return delete;
	} // end getDelete

	public void setDelete(boolean delete) {
		this.delete = delete;
	} // end setDelete

} // end Prescription