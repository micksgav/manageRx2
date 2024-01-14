/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 12, 2023
 * @Last Modified: May 23, 2023
 * @Description: Main for managing patient information
 ***********************************************
 */

package PatientManagement;

import inventory.*;
import java.util.*;
import java.time.*;

public class MainForPatientManagement {

	public static PrescriptionList addListOfPrescriptions() {
		String[][] idk = new String[][]{ { "h", "h" }, { "h", "h" } };
		PrescriptionList list = new PrescriptionList();
		String[] random = { "random", "random2" };
		Drug drug = new Drug("00015", "Advil", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
		Prescription script = new Prescription(drug, "December 15, 2023", 3, 25, drug.getDosage(), "lalala","6 months");
		list.insert(script);

		return list;

	}

	// instantiate a new patient object and place it in the list of patients
	public static PatientList createPatient(String name, int age, String address, String monthOfBirth, int dayOfBirth,
			int birthYear, PrescriptionList activePrescriptions, PrescriptionList pastPrescriptions, int phoneNumber,
			String email, long creditNum, int cardExp, LinkedList<String> allergiesAndDietary,
			LinkedList<String> medicalConditions, LinkedList<String> lifestyleHabits, FamilyDoctor familyDoctor,
			LinkedList<Insurance> insuranceInformation, PatientList patientList, String healthCardNum) {

		patientList.insert(new Patient(name, age, address, monthOfBirth, dayOfBirth, birthYear, activePrescriptions,
				pastPrescriptions, phoneNumber, email, creditNum, cardExp, allergiesAndDietary, medicalConditions,
				lifestyleHabits, familyDoctor, insuranceInformation, healthCardNum);

		return patientList;
	}

	// search for a patient in the list by name and phone number
	public static int searchPatientByNameAndPhoneNumber(String name, PatientList patients, int patientPhoneNumber) {
		if (name != null && patientPhoneNumber > 0) {
			int index = patients.findPatientByPhoneNumber(name, patientPhoneNumber);
			return index;
		}
		return -1;
	}

	// search for a patient in the list by name and address
	public static int searchPatientByNameAndAddress(String name, PatientList patients, String address) {
		if (name != null && address != null) {
			int index = patients.findPatientByAddress(name, address);
			return index;
		}
		return -1;
	}

	// search for a patient in the list by name and birthday

	public static int searchPatientByNameAndBirthday(String name, PatientList patients, String birthMonth, int birthDay,
			int birthYear) {
		int index = patients.findPatientByBirthday(name, birthMonth, birthDay, birthYear);
		return index;

	}

	// create a list of medical conditions manually before creating a new patient
	public static LinkedList<String> createListMedConditions(Scanner scan) {
		LinkedList<String> medicalConditions = new LinkedList<String>();
		String current = "";
		while (true) {
			System.out.println(
					"Press 1 to add new medical condition, 2 to save to the list, 3 to exit with saved list, 4 to exit and cancel changes");
			switch (Integer.parseInt(scan.nextLine())) {
			case 1:
				System.out.println("Enter condition to add");
				current = scan.nextLine();
				break;
			case 2:
				if (current != "") {
					medicalConditions.add(current);
				} else {
					System.out.println("Error, nothing to add");
				}
				break;
			case 3:
				return medicalConditions;
			case 4:
				return null;
			}
		}
	}

	// create a list of allergies/dietary manually before creating a new patient
	public static LinkedList<String> createListAllergiesDietary(Scanner scan) {
		LinkedList<String> allergiesDietary = new LinkedList<String>();
		String current = "";
		while (true) {
			System.out.println(
					"Press 1 to add new allergy/dietary, 2 to save to the list, 3 to exit with saved list, 4 to exit and cancel changes");
			switch (Integer.parseInt(scan.nextLine())) {
			case 1:
				System.out.println("Enter allergy/dietary to add");
				current = scan.nextLine();
				break;
			case 2:
				if (current != "") {
					allergiesDietary.add(current);
				} else {
					System.out.println("Error, nothing to add");
				}
				break;
			case 3:
				return allergiesDietary;
			case 4:
				return null;
			}
		}
	}

	// enter family doctor info before creating a new patient
	public static FamilyDoctor createDoctor(Scanner scan) {
		FamilyDoctor doc = new FamilyDoctor();
		System.out.println(
				"Press 1 to add family doc name, 2 to add family doc address, 3 to add family doc phone number");
		switch (Integer.parseInt(scan.nextLine())) {
		case 1:
			doc.setName(scan.nextLine());
			break;
		case 2:
			doc.setAddress(scan.nextLine());
			break;
		case 3:
			doc.setPhoneNumber(Integer.parseInt(scan.nextLine()));
			break;
		}
		return doc;
	}

	// create a list of insurance information manually before creating a new patient
	public static LinkedList<Insurance> createInsuranceList(Scanner scan) {
		LinkedList<Insurance> insuranceList = new LinkedList<Insurance>();
		Insurance current = new Insurance();
		while (true) {
			System.out.println(
					"Press 1 to enter insurance info, 2 to save and add to list, 3 to save list, 4 to discard");
			switch (Integer.parseInt(scan.nextLine())) {
			case 1:
				System.out.println("Enter company name");
				current.setCompany(scan.nextLine());
				System.out.println("Enter insurance number");
				current.setNumber(Integer.parseInt(scan.nextLine()));
				break;
			case 2:
				if (current != null) {
					insuranceList.add(current);
				}
				break;
			case 3:
				return insuranceList;
			case 4:
				return null;
			}
		}
	}

	// create a list of lifestyle habits manually before creating a new patient
	public static LinkedList<String> createListLifestyleHabits(Scanner scan) {
		LinkedList<String> lifestyleHabits = new LinkedList<String>();
		String current = "";
		while (true) {
			System.out.println(
					"Press 1 to add new allergy/dietary, 2 to save to the list, 3 to exit with saved list, 4 to exit and cancel changes");
			switch (Integer.parseInt(scan.nextLine())) {
			case 1:
				System.out.println("Enter allergy/dietary to add");
				current = scan.nextLine();
				break;
			case 2:
				if (current != "") {
					lifestyleHabits.add(current);
				} else {
					System.out.println("Error, nothing to add");
				}
				break;
			case 3:
				return lifestyleHabits;
			case 4:
				return null;
			}
		}
	}

	// convert a month from the String form to an int 1-12
	public static int convertMonthToNumber(String month) {
		switch (month.toLowerCase()) {
		case "january":
			return 1;
		case "february":
			return 2;
		case "march":
			return 3;
		case "april":
			return 4;
		case "may":
			return 5;
		case "june":
			return 6;
		case "july":
			return 7;
		case "august":
			return 8;
		case "september":
			return 9;
		case "october":
			return 10;
		case "november":
			return 11;
		case "december":
			return 12;
		default:
			return -1;
		}
	}

	// convert a month from int to String value
	public static String convertNumToMonth(int month) {
		switch (month) {
		case 1:
			return "january";
		case 2:
			return "february";
		case 3:
			return "march";
		case 4:
			return "april";
		case 5:
			return "may";
		case 6:
			return "june";
		case 7:
			return "july";
		case 8:
			return "august";
		case 9:
			return "september";
		case 10:
			return "october";
		case 11:
			return "november";
		case 12:
			return "december";
		default:
			return null;
		}
	}

	// automatically update the age of the patient on their birthday
	public static void updatePatientAge(PatientList patients, int indexInList) {

		LocalDateTime date = LocalDateTime.now();
		if (date.getMonthValue() == convertMonthToNumber(patients.returnData(indexInList).getDateOfBirthMonth())
				&& date.getDayOfMonth() == patients.returnData(indexInList).getDateOfBirthDay()) {
			patients.returnData(indexInList).setAge(patients.returnData(indexInList).getAge() + 1);
		}
	}

	// update the address of an existing patient
	public static void updatePatientAddress(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter new address of patient");
			patients.returnData(index).setAddress(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}

	// update the phone number of an existing patient
	public static void updatePatientNumber(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter new phone number of patient");
			patients.returnData(index).setPhoneNumber(Integer.parseInt(scan.nextLine()));
		} else {
			System.out.println("Unable to find patient");
		}
	}

	// update the email of an existing patient
	public static void updatePatientEmail(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter new email of patient");
			patients.returnData(index).setEmail(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}

	// add a prescription to the patient
	public static void addActivePrescription(PatientList patients, Scanner scan) {
		LocalDateTime date = LocalDateTime.now();
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		// add a new prescription to the patient
		if (index >= 0) {
			Prescription newRx = new Prescription();
			System.out.println("Enter brand name of drug");
			newRx.setBrandName(scan.nextLine());
			// set generic name based on DIN gathered from brand name
			System.out.println("Enter number of refills");
			newRx.setRefills(Integer.parseInt(scan.nextLine()));
			System.out.println("Enter quantity");
			newRx.setQuantity(Integer.parseInt(scan.nextLine()));
			System.out.println("Enter dosage");
			newRx.setDosage(Integer.parseInt(scan.nextLine()));
			System.out.println("Enter instructions");
			newRx.setInstructions(scan.nextLine());
			int year = date.getYear();
			int month = date.getMonthValue();
			int day = date.getDayOfMonth();
			newRx.setDate(convertNumToMonth(month) + " " + day + ", " + year);
			System.out.println("Enter prescribed duration for prescription");
			newRx.setDuration(scan.nextLine());
			patients.returnData(index).addActivePrescription(newRx);
			
			stock.getDrugsList().fillPrescription(newRx.getDrug().getDIN(), quantity); // 1/08/24, assuming that one of the methods in the code above sets the DIN somewhere

		} else {
			System.out.println("Unable to find patient");
		}
	}
	


	// remove a prescription from a patient's active prescriptions
	public static void ArchivePrescription(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		String drugName = "";
		// archive prescription
		if (index >= 0) {
			System.out.println("Enter the name of the drug for the prescription you would like to remove");
			drugName = scan.nextLine();
			patients.returnData(index).removeActivePrescription(drugName);
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// add a new credit card to patient file
	public static void addCreditCard(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		long creditNum = 0L;
		int creditExp = 0;
		if (index >= 0) {
			System.out.println("Enter the new credit card number");
			creditNum = Long.parseLong(scan.nextLine());
			System.out.println("Enter the expiry date of the credit card");
			creditExp = Integer.parseInt(scan.nextLine());
			patients.returnData(index).addNewCard(creditNum, creditExp);
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// remove a credit card from patient file
	public static void removeCreditCard(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		long creditNum = 0L;
		int creditExp = 0;
		if (index >= 0) {
		System.out.println("Enter the credit card number of the card you would like to remove");
		creditNum = Long.parseLong(scan.nextLine());
		System.out.println("Enter the expiry date");
		creditExp = Integer.parseInt(scan.nextLine());
		patients.returnData(index).removeCard(creditNum, creditExp);
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// add a new allergy or dietary restriction
	public static void addAllergy(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter the dietary restriction or allergy");
			patients.returnData(index).addAllergyOrDietary(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// remove an allergy or dietary restriction -- will probably be clicking a button so putting the exact string in isn't important
	public static void removeAllergy(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter the dietary restriction or allergy");
			patients.returnData(index).removeAllergyOrDietary(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// add a medical condition
	public static void addCondition(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter the medical condition");
			patients.returnData(index).addMedicalCondition(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// remove a medical condition
	public static void removeCondition(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter the dietary restriction or allergy");
			patients.returnData(index).removeMedicalCondition(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// add a lifestyle habit
	public static void addLifestyleHabit(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter the lifestyle habit");
			patients.returnData(index).addLifestyleHabit(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// remove a lifestyle habit
	public static void removeLifestyleHabit(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("Enter the lifestyle habit");
			patients.returnData(index).removeLifestyleHabit(scan.nextLine());
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// change family doctor
	public static void updateFamilyDoctor(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		String newName = "";
		String newAddress = "";
		int phoneNumber = 0;
		if (index >= 0) {
			System.out.println("Enter the name of the new doctor");
			newName = scan.nextLine();
			System.out.println("Enter the address of the new doctor");
			newAddress = scan.nextLine();
			System.out.println("Enter the phone number of the new doctor");
			phoneNumber = Integer.parseInt(scan.nextLine());
			patients.returnData(index).newFamilyDoctor(newName, newAddress, phoneNumber);
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// add new insurance info
	public static void newInsurance(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		String newComp = "";
		int newNum = 0;
		if (index >= 0) {
			System.out.println("Enter the company for the new insurance");
			newComp = scan.nextLine();
			System.out.println("Enter the new insurance number");
			newNum = Integer.parseInt(scan.nextLine());
			patients.returnData(index).addNewInsuranceInfo(newComp, newNum);
		} else {
			System.out.println("Unable to find patient");
		}
	}
	
	// remove insurance info
	public static void removeInsurance(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		String remComp = "";
		int remNum = 0;
		if (index >= 0) {
			System.out.println("Enter the insurance number for the plan you would like to remove");
			patients.returnData(index).removeInsurance(remComp, remNum);
		} else {
			System.out.println("Unable to find patient");
		}
	}
  
	// print a report of the patient's information to the screen
	public static void printPatientReport(Scanner scan, PatientList patients) {
		System.out.println("Enter the name of the patient");
		String name = scan.nextLine();
		System.out.println("Enter the birth month of the patient");
		String birthMonth = scan.nextLine();
		System.out.println("Enter the birth day of the patient (int day of month)");
		int birthDay = Integer.parseInt(scan.nextLine());
		System.out.println("Enter the birth year of the patient");
		int birthYear = Integer.parseInt(scan.nextLine());
		int index = searchPatientByNameAndBirthday(name, patients, birthMonth, birthDay, birthYear);
		if (index >= 0) {
			System.out.println("General Information\nPatient full name: " + patients.returnData(index).getName()
					+ "\nPatient date of birth: " + patients.returnData(index).getDateOfBirthMonth() + " "
					+ patients.returnData(index).getDateOfBirthDay() + ", " + patients.returnData(index).getBirthYear()
					+ "\nPatient age: " + patients.returnData(index).getAge() + "\nPatient phone number: "
					+ patients.returnData(index).getPhoneNumber() + "\nPatient email: "
					+ patients.returnData(index).getEmail() + "\nPatient family doctor: "
					+ patients.returnData(index).getFamilyDoctorName() + "\nPatient family doctor phone number: "
					+ patients.returnData(index).getFamilyDoctorNumber() + "\nPatient family doctor address: "
					+ patients.returnData(index).getFamilyDoctorAddress());
			
			String[] prescriptionList = patients.returnData(index).getActivePrescriptions().returnInfo();
			if (prescriptionList.length > 0) {
			System.out.println("\nActive Prescriptions\n");
			for (int i = 0; i < prescriptionList.length; i++){
				System.out.println(prescriptionList[i]);
			}
			}
			else {
				System.out.println("No active prescriptions");
			}
			
			String[] pastPrescriptions = patients.returnData(index).getArchivedPrescriptions().returnInfo();
			if (pastPrescriptions.length > 0) {
			System.out.println("\nPast Prescriptions\n");
			for (int i = 0; i < pastPrescriptions.length; i++) {
				System.out.println(pastPrescriptions[i]);
			}
			}
			else {
				System.out.println("No past prescriptions");
			}
			
			System.out.println("\nCredit Cards on file\n");
			
			for (int j = 0; j < patients.returnData(index).getCreditNum().size(); j++) {
				System.out.println("Card number: " + patients.returnData(index).getCreditNum().get(j) + "\tExpiry date: " + patients.returnData(index).getCardExpDate().get(j));
			}
			
			System.out.println("\nAllergies and dietary restrictions\n");
			
			for (int j = 0; j < patients.returnData(index).getAllergiesAndDietary().size(); j++) {
				System.out.println(patients.returnData(index).getAllergiesAndDietary().get(j));
			}
			
			System.out.println("\nMedical Conditions\n");
			
			for (int j = 0; j < patients.returnData(index).getMedicalConditions().size(); j++) {
				System.out.println(patients.returnData(index).getMedicalConditions().get(j));
			}
			
			System.out.println("\nLifestyle Habits\n");
			
			for (int j = 0; j < patients.returnData(index).getLifestyleHabits().size(); j++) {
				System.out.println(patients.returnData(index).getLifestyleHabits().get(j));
			}
			
			System.out.println("\nInsurance Information\n");
			
			for (int j = 0; j < patients.returnData(index).getInsuranceInformation().size(); j++) {
				System.out.println("Company: " + patients.returnData(index).getInsuranceInformation().get(j).getCompany() + "\tInsurance number: " + patients.returnData(index).getInsuranceInformation().get(j).getNumber());
			}
			
			
			
			
		} else {
			System.out.println("Unable to find patient");
		}
	}


	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Date date = new Date();

		PatientList patients = new PatientList();

		PrescriptionList addListOfPrescriptions = addListOfPrescriptions();
		createPatient("John", 17, "413 ABC Street", "March", 3, 2006, addListOfPrescriptions, addListOfPrescriptions,
				1234567890, "jbbbb@gmail.com", 1234567890123456L, 1224, null, null, null, null, null, patients);
		// addListOfPrescriptions.setHead(null);
		// patients.returnData(0).printPatientInfo();

		int index = searchPatientByNameAndPhoneNumber("John", patients, patients.returnData(0).getPhoneNumber());

		if (index == -1) {
			System.out.println("Patient not found");
		} else {
			patients.returnData(index).printPatientInfo();
		}

		scan.close();
	}

}
