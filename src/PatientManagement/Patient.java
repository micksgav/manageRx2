/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : May 18, 2023
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
	private String name; // patient name
	private int age; // patient age
	private String address; // patient address
	private int birthMonth; // patient birth month
	private int birthDay; // patient birth day
	private int birthYear; // patient birth year
	PrescriptionList activePrescriptions; // current prescriptions
	PrescriptionList pastPrescriptions; // inactive prescriptions
	private String phoneNumber; // patient phone number
	private String email; // patient email
	private String healthCardNum;
	private LinkedList<Long> creditNum; // patient credit card number
	private LinkedList<Integer> cardExpDate; // patient credit card expiry date
	private LinkedList<String> allergiesAndDietary; // patient allergies/dietary restrictions
	private LinkedList<String> medicalConditions; // patient medical conditions
	private LinkedList<String> lifestyleHabits; // patient lifestyle habits that could have negative health impacts
	private FamilyDoctor familyDoctor; // patient's family doctor
	private LinkedList<Insurance> insuranceInformation; // patient's insurance information
	private String additionalNotes = "Medical Conditions:/n/nLifestyle Habits:\n\nAllergies/Dietary Restrictions:\n";

	public Patient(String name, int age, String address, int dateOfBirthMonth, int dateOfBirthDay, int birthYear, PrescriptionList activePrescriptions,
			PrescriptionList pastPrescriptions, String phoneNumber, String email, long creditNum, int cardExp,
			LinkedList<String> allergiesAndDietary, LinkedList<String> medicalConditions,
			LinkedList<String> lifestyleHabits, FamilyDoctor familyDoctor, LinkedList<Insurance> insuranceInformation, String healthCardNum) {
		this.creditNum = new LinkedList<Long>();
		this.cardExpDate = new LinkedList<Integer>();
		this.allergiesAndDietary = new LinkedList<String>();
		this.medicalConditions = new LinkedList<String>();
		this.lifestyleHabits = new LinkedList<String>();
		this.insuranceInformation = new LinkedList<Insurance>();
		this.name = name;
		this.age = age;
		this.address = address;
		this.birthMonth = dateOfBirthMonth;
		this.birthDay = dateOfBirthDay;
		this.birthYear = birthYear;
		this.activePrescriptions = activePrescriptions;
		this.pastPrescriptions = pastPrescriptions;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.creditNum.addFirst(creditNum);
		cardExpDate.addFirst(cardExp);
		this.allergiesAndDietary = allergiesAndDietary;
		this.medicalConditions = medicalConditions;
		this.lifestyleHabits = lifestyleHabits;
		this.familyDoctor = familyDoctor;
		this.insuranceInformation = insuranceInformation;
		this.healthCardNum = healthCardNum;
	}

	public Patient() {
		familyDoctor = new FamilyDoctor();
	}
	
	public void setAdditionalNotes(String notes) {
		additionalNotes = notes;
	}
	
	public String getAdditionalNotes() {
		return additionalNotes;
	}
	
	public String getHealthCardNumber() {
		return healthCardNum;
	}
	
	public void setHealthCardNumber(String healthCardNum) {
		this.healthCardNum = healthCardNum;
	}
	

	public void printPatientInfo() {
		System.out.println(name + "\n" + age + "\n" + creditNum.toString().replace("[", "").replace("]", "") + "\n" + address + "\n" + activePrescriptions.atIndex(0).getBrandName());
	}

	public void newFamilyDoctor(String newName, String newAddress, String phoneNumber) {
		familyDoctor.setName(newName);
		familyDoctor.setAddress(newAddress);
		familyDoctor.setPhoneNumber(phoneNumber);
	}
	
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
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getDateOfBirthMonth() {
		return birthMonth;
	}
	
	public int getDateOfBirthDay() {
		return birthDay;
	}

	public void setDateOfBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}
	
	public void setDateOfBirthDay(int day) {
		this.birthDay = day;
	}
	
	public int getBirthYear() {
		return birthYear;
	}
	
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public String getBirthday() {
		String birthDayString = String.valueOf(birthDay);
		String birthMonthString = String.valueOf(birthMonth);
		if (birthDay / 10 == 0) {
			birthDayString = "0" + birthDay;
		}
		if (birthMonth / 10 == 0) {
			birthMonthString = "0" + birthMonth;
		}
		return birthDayString + "/" + birthMonthString + "/" + birthYear;
	}

	public PrescriptionList getActivePrescriptions() {
		return activePrescriptions;
	}

	public void addActivePrescription(Prescription prescription) {
		activePrescriptions.insert(prescription);
	}

	public void removeActivePrescription(Prescription prescription) {
		pastPrescriptions.insert(prescription);
		activePrescriptions.delete(prescription);
	}

	public PrescriptionList getAllPrescriptions() {
		return activePrescriptions;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public PrescriptionList getArchivedPrescriptions() {
		return pastPrescriptions;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LinkedList<Long> getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(long creditNum) {
		this.creditNum.add(creditNum);
	}

	public LinkedList<Integer> getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(int cardExpDate, int whichCard) {
		this.cardExpDate.add(cardExpDate); // make so works with multiple cards
	}

	public void removeCard(long cardNum, int creditExp) {
		this.cardExpDate.remove(creditExp);
		this.creditNum.remove(cardNum);
	}

	public void addNewCard(long cardNum, int cardExp) {
		this.creditNum.add(cardNum);
		this.cardExpDate.add(cardExp);
	}

	public LinkedList<String> getAllergiesAndDietary() {
		return allergiesAndDietary;
	}

	public void addAllergyOrDietary(String allergyOrDietary) {
		allergiesAndDietary.add(allergyOrDietary);
	}

	public void removeAllergyOrDietary(String allergyOrDietary) {
		allergiesAndDietary.remove(allergyOrDietary);
	}

	public void addMedicalCondition(String medicalCondition) {
		medicalConditions.add(medicalCondition);
	}

	public void removeMedicalCondition(String medicalCondition) {
		medicalConditions.remove(medicalCondition);
	}

	public void addLifestyleHabit(String lifestyleHabit) {
		lifestyleHabits.add(lifestyleHabit);
	}

	public void removeLifestyleHabit(String lifestyleHabit) {
		lifestyleHabits.remove(lifestyleHabit);
	}

	public String getFamilyDoctorName() {
		return familyDoctor.getName();
	}

	public void setFamilyDoctorName(String docName) {
		familyDoctor.setName(docName);
	}


	public String getFamilyDoctorNumber() {
		return familyDoctor.getPhoneNumber();
	}

	public void setFamilyDoctorNumber(String number) {

		familyDoctor.setPhoneNumber(number);
	}

	public String getFamilyDoctorAddress() {
		return familyDoctor.getAddress();
	}

	public void setFamilyDoctorAddress(String Address) {
		familyDoctor.setAddress(address);
	}

	public LinkedList<Insurance> getInsuranceInformation() {
		return insuranceInformation;
	}

	public void addNewInsuranceInfo(String company, int num, String notes) {
		insuranceInformation.add(new Insurance(company, num, notes));
	}
	
	
	public void removeInsurance(int i) {
				insuranceInformation.remove(i);
	}
	
	public void newInsuranceList() {
		insuranceInformation = new LinkedList<Insurance>();
	}
	
	public void newPrescriptionList() {
		activePrescriptions = new PrescriptionList();
		pastPrescriptions = new PrescriptionList();

	}

}
