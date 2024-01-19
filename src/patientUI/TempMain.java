package patientUI;

import java.sql.*;

import PatientManagement.*;
import inventory.*;
import utilities.SQLHelper;
import utilities.WaitDialog;

import java.util.*;

public class TempMain {

	public static void main(String[] args) throws SQLException {
//		String[][] idk = { { "400", "400" }, { "400", "400" } };
//		String[] alts = { "h", "h" };
//		Drug drug = new Drug("00015", "Advil", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
//		Drug drug2 = new Drug("00015", "notAdvil", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
//		Drug drug3 = new Drug("00015", "defNotAdvil", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
//		Drug drug4 = new Drug("00015", "notAdvilAtAll", "a class", "idk", "Pfizer", "Advil 400 mg", "Pill", idk);
//		FamilyDoctor doc = new FamilyDoctor("Joe Mama", "123 Address Street", "5191234566", "1");
//		Prescription script = new Prescription(drug, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
//		Prescription script2 = new Prescription(drug2, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
//		Prescription script3 = new Prescription(drug3, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
//		Prescription script4 = new Prescription(drug4, "December 28, 2023", 3, 25, idk, "Take this", "6 Months");
//		PrescriptionList scripts = new PrescriptionList();
//		scripts.insert(script);
//		scripts.insert(script2);
//		PrescriptionList pastScripts = new PrescriptionList();
//		pastScripts.insert(script3);
//		pastScripts.insert(script4);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		scripts.insert(script);
//		Insurance insurance = new Insurance("123 ABC CORP", 12345, "Notes");
//		Insurance insurance2 = new Insurance("ABC DEF", 123, "Notes");
//		Insurance insurance3 = new Insurance("XYZ Company", 12543, "Notes");
//		LinkedList<Insurance> insuranceList = new LinkedList<Insurance>();
//		insuranceList.add(insurance);
//		insuranceList.add(insurance2);
//		insuranceList.add(insurance3);
//
//		Patient patient = new Patient(0, "John", "413 ABC Street", 3, 3, 2006, scripts, pastScripts,
//				"1234567890", "jbbbb@gmail.com", doc, insuranceList,
//				"0000-000-000-AB", "male", 150.0);
//		Patient patient2 = new Patient(0, "Brayden", 17, "123 CDE Road", 10, 12, 2006, scripts, pastScripts,
//				"1234567890", "jbbbb@gmail.com", 1234567890123456L, 1224, null, null, null, doc, insuranceList,
//				"0000-000-000-AB");
//		Patient patient3 = new Patient(0, "John", 17, "413 123 Street", 3, 3, 2006, scripts, pastScripts,
//				"1234567890", "jbbbb@gmail.com", 1234567890123456L, 1224, null, null, null, doc, insuranceList,
//				"0000-000-000-AB");
//		PatientList patients = new PatientList();
//		for (int i = 0; i < 20000; i++) {
//			Patient newPatient = new Patient(0, "John", "413 ABC Street" + i, 3, 3, 2006, scripts, pastScripts,
//					"1234567890", "jbbbb@gmail.com", doc, insuranceList,
//					"0000-000-000-AB", "male", 150.0);
//			patients.insert(newPatient);
//		}
//		patients.insert(patient);
//		patients.insert(patient3);
		// patients.insert(patient2);

		WaitDialog.showWait();
		
		SQLHelper helper = new SQLHelper();

		PatientList patients = helper.getAllPatients();
		AllStock stock = new AllStock();
		PrescriptionList allScripts = helper.getAllPrescriptions();
		LinkedList<Insurance> allInsurance = helper.getAllInsurance();

//		for (int i = 0; i < allScripts.length(); i++) {
//			for (int j = 0; j < patients.numRecs(); j++) {
//				if (patients.returnData(j).getId() == allScripts.atIndex(i).getPatientID()) {
//					if (allScripts.atIndex(i).getCurrent()) {
//						patients.returnData(j).addActivePrescription(allScripts.atIndex(i));
//					} 
//					else {
//						patients.returnData(j).addArchivedPrescription(allScripts.atIndex(i));
//					}
//				}
//			}
//		}
//		for (int i = 0; i < allInsurance.size(); i++) {
//			for (int j = 0; j < patients.numRecs(); j++) {
//				if (patients.returnData(j).getId() == allInsurance.get(i).getPatientID()) {
//						patients.returnData(j).addNewInsuranceInfo(allInsurance.get(i));
//				}
//			}
//		}

		WaitDialog.disposeWait();
		
		
		Patient patient = new Patient();
		SearchAddUI oui = new SearchAddUI("ManageRx", patient, patients, stock);
		
		

		oui.setVisible(true);
	}
}

//		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for pulling from
//		// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for pushing to
//		Connection connect = null;
//		
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			connect = DriverManager.getConnection("jdbc:mysql://managerx.cxa8k0i6mls8.ca-central-1.rds.amazonaws.com:3306/manageRx", "manageRxAdmin", "manageRxSQL*!");
//			Statement statement;
//            statement = connect.createStatement();
//            ResultSet resultSet;
//            connect.setAutoCommit(true);
//
//            statement.executeUpdate("UPDATE PatientInfo SET name = \"JOHNNN\" WHERE ID = 0");
//            resultSet = statement.executeQuery(
//                "select * from PatientInfo");
//			Patient newPatient;
//			while (resultSet.next()) {
//				//newPatient = new Patient(resultSet.getInt("ID"), resultSet.getString("name"), 0, resultSet.getString("address"), resultSet.getInt("birthMonth"), resultSet.getInt("birthDay"), resultSet.getInt("birthYear"), null, null, resultSet.getString("phoneNumber"), resultSet.getString("email"), 0L, 0, null, null, null, null, null, resultSet.getString("healthCard"));
//				//System.out.println(newPatient.getName());
//				//patients.insert(newPatient);
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}