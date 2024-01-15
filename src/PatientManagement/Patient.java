/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 16, 2023
 * @Last Modified: December 16, 2023
 * @Description: Patient object containing all relevant information about a patient at a pharmacy
 ***********************************************
 */


package PatientManagement;

import inventory.*;

import java.io.IOException;
import java.util.*;
import utilities.*;

public class Patient {
	private int id;
	private String name; // patient name
	private String address; // patient address
	private int birthMonth; // patient birth month
	private int birthDay; // patient birth day
	private int birthYear; // patient birth year
	PrescriptionList activePrescriptions; // current prescriptions
	PrescriptionList pastPrescriptions; // inactive prescriptions
	private String phoneNumber; // patient phone number
	private String email; // patient email
	private String healthCardNum; // health card number
	private FamilyDoctor familyDoctor; // patient's family doctor
	private LinkedList<Insurance> insuranceInformation; // patient's insurance information
	private String additionalNotes = "Medical Conditions:\n\nLifestyle Habits:\n\nAllergies/Dietary Restrictions:\n";
	private String gender; // patient gender
	private double weight; // patient weight

	public Patient(int id, String name, String address, int dateOfBirthMonth, int dateOfBirthDay, int birthYear, PrescriptionList activePrescriptions,
			PrescriptionList pastPrescriptions, String phoneNumber, String email, FamilyDoctor familyDoctor, LinkedList<Insurance> insuranceInformation, String healthCardNum, String gender, double weight) {
		this.insuranceInformation = new LinkedList<Insurance>();
		this.name = name;
		this.address = address;
		this.birthMonth = dateOfBirthMonth;
		this.birthDay = dateOfBirthDay;
		this.birthYear = birthYear;
		this.activePrescriptions = activePrescriptions;
		this.pastPrescriptions = pastPrescriptions;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.familyDoctor = familyDoctor;
		this.insuranceInformation = insuranceInformation;
		this.healthCardNum = healthCardNum;
		this.id = id;
		this.gender = gender;
		this.weight = weight;
	} // end Patient

	public Patient() {
		familyDoctor = new FamilyDoctor();
		insuranceInformation = new LinkedList<Insurance>();
		activePrescriptions = new PrescriptionList();
		pastPrescriptions = new PrescriptionList();
	} // end Patient
	
	public void addArchivedPrescription(Prescription prescription) {
		pastPrescriptions.insert(prescription);
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	} // end setWeight
	
	public double getWeight() {
		return weight;
	} // end getWeight
	
	public void setGender(String gender) {
		this.gender = gender;
	} // end setGender
	
	public String getGender() {
		return gender;
	} // end getGender
	
	public void setId(int id) {
		this.id = id;
	} // end setId
		
	public int getId() {
		return id;
	} // end getId
	
	public void setAdditionalNotes(String notes) {
		additionalNotes = notes;
	} // end setAdditionalNotes
	
	public String getAdditionalNotes() {
		return additionalNotes;
	} // end getAdditionalNotes
	
	public String getHealthCardNumber() {
		return healthCardNum;
	} // end getHealthCardNumber
	
	public void setHealthCardNumber(String healthCardNum) {
		this.healthCardNum = healthCardNum;
	} // end setHealthCardNumber

	public void newFamilyDoctor(String newName, String newAddress, String phoneNumber) {
		familyDoctor.setName(newName);
		familyDoctor.setAddress(newAddress);
		familyDoctor.setPhoneNumber(phoneNumber);
	} // end newFamilyDoctor
	
	public ArrayList<String[]> drugInteractions(Drug newDrug) throws IOException {

		ArrayList<String[]> allInteractions = new ArrayList<String[]>(); // list containing all interaction data for the two drugs
		String[] interactions; // list containing interactions between the current drug in the list and the new drug
		for (int i = 0; i < activePrescriptions.length(); i++) {
			String currentDIN = activePrescriptions.atIndex(i).getDrug().getDIN();
			interactions = getInteractions.search(currentDIN, newDrug.getDIN());
			allInteractions.add(interactions);
		} 
		return allInteractions;
	}

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

	public int getDateOfBirthMonth() {
		return birthMonth;
	} // end getDateOfBirthMonth
	
	public int getDateOfBirthDay() {
		return birthDay;
	} // end getDateOfBirthDay

	public void setDateOfBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	} // end setDateOfBirthMonth
	
	public void setDateOfBirthDay(int day) {
		this.birthDay = day;
	} // end setDateOfBirthDay
	
	public int getBirthYear() {
		return birthYear;
	} // end getBirthYear
	
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	} // end setBirthYear
	
	public String getBirthday() {
		String birthDayString = String.valueOf(birthDay);
		String birthMonthString = String.valueOf(birthMonth);
		if (birthDay / 10 == 0) {
			birthDayString = "0" + birthDay;
		} // end if
		if (birthMonth / 10 == 0) {
			birthMonthString = "0" + birthMonth;
		} // end if
		return birthDayString + "/" + birthMonthString + "/" + birthYear;
	} // end getBirthday

	public PrescriptionList getActivePrescriptions() {
		return activePrescriptions;
	} // end getActivePrescriptions

	public void addActivePrescription(Prescription prescription) {
		activePrescriptions.insert(prescription);
	} // end addActivePrescription

	public void removeActivePrescription(Prescription prescription) {
		pastPrescriptions.insert(prescription);
		activePrescriptions.delete(prescription);
	} // end removeActivePrescription

	public PrescriptionList getAllPrescriptions() {
		return activePrescriptions;
	} // end getAllPrescriptions

	public String getPhoneNumber() {
		return phoneNumber;
	} // end getPhoneNumber

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	} // end setPhoneNumber
	
	public PrescriptionList getArchivedPrescriptions() {
		return pastPrescriptions;
	} // end getArchivedPrescriptions

	public String getEmail() {
		return email;
	} // end getEmail

	public void setEmail(String email) {
		this.email = email;
	} // end setEmail

	public String getFamilyDoctorName() {
		return familyDoctor.getName();
	} // end getFamilyDoctorName

	public void setFamilyDoctorName(String docName) {
		familyDoctor.setName(docName);
	} // end setFamilyDoctorName


	public String getFamilyDoctorNumber() {
		return familyDoctor.getPhoneNumber();
	} // end getFamilyDoctorNumber

	public void setFamilyDoctorNumber(String number) {
		familyDoctor.setPhoneNumber(number);
	} // end setFamilyDoctorNumber

	public String getFamilyDoctorAddress() {
		return familyDoctor.getAddress();
	} // end getFamilyDoctorAddress

	public void setFamilyDoctorAddress(String Address) {
		familyDoctor.setAddress(address);
	} // end setFamilyDoctorAddress

	public LinkedList<Insurance> getInsuranceInformation() {
		return insuranceInformation;
	} // end getInsuranceInformation

	public void addNewInsuranceInfo(String company, int num, String notes) {
		insuranceInformation.add(new Insurance(company, num, notes));
	} // end addNewInsuranceInfo
	
	public void addNewInsuranceInfo(Insurance insurance) {
		insuranceInformation.add(insurance);
	}
	
	public void removeInsurance(int i) {
				insuranceInformation.remove(i);
	} // end removeInsurance
	
	public void newInsuranceList() {
		insuranceInformation = new LinkedList<Insurance>();
	} // end newInsuranceList
	
	public void newPrescriptionList() {
		activePrescriptions = new PrescriptionList();
		pastPrescriptions = new PrescriptionList();
	} // end newPrescriptionList


} // end Patient
