package patientUI;

import PatientManagement.*;
import inventory.*;

import java.util.*;

public class TempMain {

	public static void main(String[] args) {
		String[][] idk = { { "h", "h" }, { "h", "h" } };
		String[] alts = { "h", "h" };
		Drug drug = new Drug("00015", "Advil", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
		Drug drug2 = new Drug("00015", "notAdvil", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
		Drug drug3 = new Drug("00015", "defNotAdvil", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
		Drug drug4 = new Drug("00015", "notAdvilAtAll", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
		FamilyDoctor doc = new FamilyDoctor("Joe Mama", "123 Address Street", "5191234566");
		Prescription script = new Prescription(drug, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
		Prescription script2 = new Prescription(drug2, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
		Prescription script3 = new Prescription(drug3, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
		Prescription script4 = new Prescription(drug4, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
		PrescriptionList scripts = new PrescriptionList();
		scripts.insert(script);
		scripts.insert(script2);
		PrescriptionList pastScripts = new PrescriptionList();
		pastScripts.insert(script3);
		pastScripts.insert(script4);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
		Insurance insurance = new Insurance("123 ABC CORP", 12345, "Notes");
		Insurance insurance2 = new Insurance("ABC DEF", 123, "Notes");
		Insurance insurance3 = new Insurance("XYZ Company", 12543, "Notes");
		LinkedList<Insurance> insuranceList = new LinkedList<Insurance>();
		insuranceList.add(insurance);
		insuranceList.add(insurance2);
		insuranceList.add(insurance3);

		Patient patient = new Patient("John", 17, "413 ABC Street", 3, 3, 2006, scripts, pastScripts,
				"1234567890", "jbbbb@gmail.com", 1234567890123456L, 1224, null, null, null, doc, insuranceList,
				"0000-000-000-AB");
		Patient patient2 = new Patient("Brayden", 17, "123 CDE Road", 10, 12, 2006, scripts, pastScripts,
				"1234567890", "jbbbb@gmail.com", 1234567890123456L, 1224, null, null, null, doc, insuranceList,
				"0000-000-000-AB");
		Patient patient3 = new Patient("John", 17, "413 123 Street", 3, 3, 2006, scripts, pastScripts,
				"1234567890", "jbbbb@gmail.com", 1234567890123456L, 1224, null, null, null, doc, insuranceList,
				"0000-000-000-AB");
		PatientList patients = new PatientList();
		for (int i = 0; i < 20000; i++) {
			Patient newPatient = new Patient("John", 17, "413 ABC Street" + i, 3, 3, 2006, scripts, pastScripts,
				"1234567890", "jbbbb@gmail.com", 1234567890123456L, 1224, null, null, null, doc, insuranceList,
				"0000-000-000-AB");
			patients.insert(newPatient);
		}
//		patients.insert(patient);
//		patients.insert(patient3);
		patients.insert(patient2);
		SearchAddUI oui = new SearchAddUI("ManageRx", patient, patients);

		oui.setVisible(true);
	}
}
