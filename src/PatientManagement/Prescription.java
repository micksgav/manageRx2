/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : May 18, 2023
 * @Last Modified: December 16, 2023
 * @Description: Prescription object containing information about a prescription belonging to a pharmacy patient
 ***********************************************
 */


package PatientManagement;
import inventory.*;
import apiInteracting.*;

public class Prescription {
	private Drug drug; // prescribed drug
	private String[] interactions; // interactions with other drugs that a patient is taking
	private String datePrescribed; // prescribed date for the drug
	private int numRefills; // number of refills left
	private int quantity; // number of pills if applicable
	private int dosage; // dosage of the drug
	private String instructions; // instructions for taking the prescription
	private String prescribedDuration; // duration to take prescribed drug for
	
	public Prescription(Drug drug, String datePrescribed, int numRefills, int quantity, int dosage, String instructions, String prescribedDuration) {
		this.drug = drug;
		//this.interactions = interactions;
		this.datePrescribed = datePrescribed;
		this.numRefills = numRefills;
		this.quantity = quantity;
		this.dosage = dosage;
		this.instructions = instructions;
		this.prescribedDuration = prescribedDuration;
	}
	
	public Prescription(){};
	
	public Drug getDrug() {
		return drug;
	}


	public String getName() {
		return drug.getDrugName();
	}

	public void setName(String genName) {
		drug.setDrugName(genName);
	}

	public String getBrandName() {
		return drug.getDrugName();
	}

	public void setBrandName(String brandName) {
		drug.setDrugName(brandName);
	}

	public String getDate() {
		return datePrescribed;
	}

	public void setDate(String date) {
		datePrescribed = date;
	}

	public int getRefills() {
		return numRefills;
	}

	public void setRefills(int refills) {
		numRefills = refills;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getDosage() {
		return dosage;
	}

	public void setDosage(int dosage) {
		this.dosage = dosage;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getDuration() {
		return prescribedDuration;
	}

	public void setDuration(String duration) {
		prescribedDuration = duration;
	}
}
