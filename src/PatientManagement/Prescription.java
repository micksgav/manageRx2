/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 16, 2023
 * @Last Modified: December 16, 2023
 * @Description: Prescription object containing information about a prescription belonging to a pharmacy patient
 ***********************************************
 */


package PatientManagement;
import inventory.*;

public class Prescription {
	private Drug drug; // prescribed drug
	private String datePrescribed; // prescribed date for the drug
	private int numRefills; // number of refills left
	private int quantity; // number of pills if applicable
	private String[][] dosage; // dosage of the drug
	private String instructions; // instructions for taking the prescription
	private String prescribedDuration; // duration to take prescribed drug for
	private int ID;
	private int patientID;
	private boolean current;
	
	public Prescription(Drug drug, String datePrescribed, int numRefills, int quantity, String[][] dosage, String instructions, String prescribedDuration) {
		this.drug = drug;
		this.datePrescribed = datePrescribed;
		this.numRefills = numRefills;
		this.quantity = quantity;
		this.dosage = dosage;
		this.instructions = instructions;
		this.prescribedDuration = prescribedDuration;
		current = true;
	} // end Prescription
	
	public Prescription(){
		drug = new Drug();
		current = true;
	} // end Prescription
	
	public Drug getDrug() {
		return drug;
	} // end getDeug


	public String getName() {
		return drug.getDrugName();
	} // end getName

	public void setName(String genName) {
		drug.setDrugName(genName);
	} // end setName
	
	public String getDIN() {
		return drug.getDIN();
	}
	
	public void setDIN(String DIN) {
		drug.setDIN(DIN);
	}

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

	public String[][] getDosage() {
		return dosage;
	} // end getDosage

	public void setDosage(String[][] dosage) {
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
		// TODO Auto-generated method stub
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getForm() {
		return drug.getForm();
	}
	
	public void setForm(String form) {
		drug.setForm(form);
	}
	
	public int getPatientID() {
		return patientID;
	}
	
	public void setPatientID(int ID) {
		patientID = ID;
	}

	public void setCurrent(int current) {
		if (current == 1) {
			this.current = true;
		}
		else {
			this.current = false;
		}
	}
	
	public boolean getCurrent() {
		return current;
	}
	
} // end Prescription