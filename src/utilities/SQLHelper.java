package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import PatientManagement.Insurance;
import PatientManagement.Patient;
import PatientManagement.PatientList;
import PatientManagement.Prescription;
import PatientManagement.PrescriptionList;

public class SQLHelper {
	ExecutorService service;
	boolean status;
	int IDnum;
	Statement statement;
	Connection connect = null;

	// EXAMPLE MAIN CODE:
	// SQLHelper sql = new SQLHelper();

	// sql.addPatientBG("test1", 9, 10, 2007, "111 Street St", "1234567890",
	// "hhfhfh@nuef.ca", "43975433223AB", "ahhhhh", "Dr. Smith", "123 Road Rd",
	// "1112223333");
	// sql.addPatientBG("test555", 9, 10, 2007, "113 Street St", "1234567890",
	// "hhfhfh@nuef.ca", "43975433223AB", "ahhhhh", "Dr. Smith", "123 Road Rd",
	// "1112223333");
	// System.out.println("This code will run while patient is being added");

	public SQLHelper() {
		service = Executors.newFixedThreadPool(1);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://managerx.cxa8k0i6mls8.ca-central-1.rds.amazonaws.com:3306/manageRx", "manageRxAdmin",
					"manageRxSQL*!");
			statement = connect.createStatement();
			connect.setAutoCommit(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " during connecting in SQLHelper");
		}

	}

	public void updateBG(String table, String column, Object obj, int ID) {
		service.submit(new Runnable() {
			public void run() {
				status = update(table, column, obj, ID);
			}
		});
	}

	public void updateInsuranceBG(String table, String column, Object obj, int ID) {
		service.submit(new Runnable() {
			public void run() {
				status = updateInsurance(table, column, obj, ID);
			}
		});
	}
	
	public void updatePrescriptionBG(String table, String column, Object obj, int ID) {
		service.submit(new Runnable() {
			public void run() {
				status = updatePrescription(table, column, obj, ID);
			}
		});
	}

	public void addPatientBG(Patient p) {
		service.submit(new Runnable() {
			public void run() {
				IDnum = addPatient(p);
				System.out.println("patient added: " + IDnum);
			}
		});
	}

	public int addPrescriptionBG(Prescription p) {
		service.submit(new Runnable() {
			public void run() {
				IDnum = addPrescription(p);
				p.setID(IDnum);
				System.out.println("prescription added: " + IDnum);			
			}
		});
		return p.getID();
	}

	// table = table name, column = column name, obj = value to update to (String or
	// int -- maybe double...), ID - ID of row
	public boolean update(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for
		// pulling from
		// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for
		// pushing to
		try {
			statement.executeUpdate("UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE ID = " + ID);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj + ", ID "
					+ ID + " in update in SQLHelper");
			return false;
		}
	}

	// table = table name, column = column name, obj = value to update to (String or
	// int -- maybe double...), ID - ID of row
	public boolean updateInsurance(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for
		// pulling from
		// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for
		// pushing to
		try {
			statement.executeUpdate(
					"UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE insuranceID = " + ID);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj + ", insuranceID "
					+ ID + " in update in SQLHelper");
			return false;
		}
	}

	// table = table name, column = column name, obj = value to update to (String or
	// int -- maybe double...), ID - ID of row
	public boolean updatePrescription(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for
		// pulling from
		// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for
		// pushing to
		try {
			statement.executeUpdate(
					"UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE prescriptionID = " + ID);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj + ", prescriptionID "
					+ ID + " in update in SQLHelper");
			return false;
		}
	}

	public int addPatient(Patient p) {
		String name = p.getName();
		int birthDay = p.getDateOfBirthDay();
		int birthMonth = p.getDateOfBirthMonth();
		int birthYear = p.getBirthYear();
		String address = p.getAddress();
		String phoneNumber = p.getPhoneNumber();
		String email = p.getEmail();
		String healthCard = p.getHealthCardNumber();
		String additionalNotes = p.getAdditionalNotes();
		String familyDoctorName = p.getFamilyDoctorName();
		String familyDoctorAddress = p.getFamilyDoctorAddress();
		String familyDoctorPhoneNumber = p.getFamilyDoctorNumber();
		// fix
		String familyDoctorFax = "test";
		String gender = "test";
		double weight = 100.00;
		int ID = p.getId();

		try {
			statement.executeUpdate("INSERT INTO PatientInfo values ( " + ID + " , \"" + name + "\" , " + birthDay
					+ " , " + birthMonth + " , " + birthYear + " , \"" + address + "\" , \"" + phoneNumber + "\" , \""
					+ email + "\" , \"" + healthCard + "\" , \"" + additionalNotes + "\" , \"" + familyDoctorName
					+ "\" , \"" + familyDoctorAddress + "\" , \"" + familyDoctorPhoneNumber + "\" , \""
					+ familyDoctorFax + "\" , \"" + gender + "\" , " + weight + " )");
			return ID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " in addPatient in SQLHelper");
			return -1;
		}
	}

	public Patient getPatient(int ID) {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM manageRx.PatientInfo WHERE ID = " + ID);
			Patient temp = new Patient();
			temp.setId(resultSet.getInt("ID"));
			temp.setName(resultSet.getString("name"));
			temp.setDateOfBirthDay(resultSet.getInt("birthDay"));
			temp.setDateOfBirthMonth(resultSet.getInt("birthMonth"));
			temp.setBirthYear(resultSet.getInt("birthYear"));
			temp.setAddress(resultSet.getString("address"));
			temp.setPhoneNumber(resultSet.getString("phoneNumber"));
			temp.setEmail(resultSet.getString("email"));
			temp.setHealthCardNumber(resultSet.getString("healthCard"));
			temp.setAdditionalNotes(resultSet.getString("addtionalNotes")); // typo in SQL column name...
			temp.setFamilyDoctorName(resultSet.getString("familyDoctorName"));
			temp.setFamilyDoctorAddress(resultSet.getString("familyDoctorAddress"));
			temp.setFamilyDoctorNumber(resultSet.getString("familyDoctorPhoneNumber"));
			// add fax, gender, weight...
			return temp;
		} catch (Exception e) {
			logErrors.log(e.getMessage() + " for getPatient in SQLHelper");
			return null;
		}
	}

	public PatientList getAllPatients() {
		PatientList pList = new PatientList();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PatientInfo");

			while (resultSet.next()) {
				Patient temp = new Patient();
				temp.setId(resultSet.getInt("ID"));
				temp.setName(resultSet.getString("name"));
				temp.setDateOfBirthDay(resultSet.getInt("birthDay"));
				temp.setDateOfBirthMonth(resultSet.getInt("birthMonth"));
				temp.setBirthYear(resultSet.getInt("birthYear"));
				temp.setAddress(resultSet.getString("address"));
				temp.setPhoneNumber(resultSet.getString("phoneNumber"));
				temp.setEmail(resultSet.getString("email"));
				temp.setHealthCardNumber(resultSet.getString("healthCard"));
				temp.setAdditionalNotes(resultSet.getString("additionalNotes"));
				temp.getFamilyDoctor().setName(resultSet.getString("familyDoctorName"));
				temp.getFamilyDoctor().setAddress(resultSet.getString("familyDoctorAddress"));
				temp.setFamilyDoctorNumber(resultSet.getString("familyDoctorPhoneNumber"));
				temp.setGender(resultSet.getString("gender"));
				temp.setWeight(resultSet.getDouble("weight"));
				temp.getFamilyDoctor().setFax(resultSet.getString("familyDoctorFax"));
				pList.insert(temp);
			}
		} catch (Exception e) {
			logErrors.log(e.getMessage() + " for getAllPatients in SQLHelper");
		}
		return pList;
	}

	public int addPrescription(Prescription p) {
		int patientID = p.getPatientID();
		int numRefills = p.getRefills();
		int quantity = p.getQuantity();
		String dosage = p.getDosage()[0][0]; // fix array stuff
		String instructions = p.getInstructions();
		String prescribedDuration = p.getDuration();
		String datePrescribed = p.getDate();
		String drugName = p.getName();
		String DIN = p.getDIN();
		String form = p.getForm(); // fix
		boolean current = p.getCurrent();
		int curr;
		if (current) {
			curr = 1;
		}
		else {
			curr = 0;
		}

		try {
			ResultSet resultSet = statement.executeQuery(
					"SELECT prescriptionID FROM PrescriptionInfo WHERE prescriptionID = (SELECT MAX(prescriptionID) FROM PrescriptionInfo)");
			resultSet.next();
			int ID = resultSet.getInt("prescriptionID") + 1;
			statement.executeUpdate("INSERT INTO PrescriptionInfo values ( " + patientID + " , \"" + numRefills
					+ "\" , " + quantity + " , \"" + dosage + "\" , \"" + instructions + "\" , \"" + prescribedDuration
					+ "\" , \"" + datePrescribed + "\" , \"" + drugName + "\" , \"" + DIN + "\" , \"" + form + "\" , "
					+ ID + " , " + curr + " )");
			return ID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " in addPrescription in SQLHelper");
			return -1;
		}
	}

	public Prescription getPrescription(int ID) {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM manageRx.PrescriptionInfo WHERE prescriptionID = " + ID);
			Prescription temp = new Prescription();
			String[][] dosage = new String[1][1];
			temp.setPatientID(resultSet.getInt("patientID"));
			temp.setRefills(resultSet.getInt("numRefills"));
			temp.setQuantity(resultSet.getInt("quantity"));
			dosage[0][0] = resultSet.getString("dosage");
			temp.setDosage(dosage); // fix
			temp.setInstructions(resultSet.getString("instructions"));
			temp.setDuration(resultSet.getString("prescribedDuration"));
			temp.setDate(resultSet.getString("datePrescribed"));
			temp.setBrandName(resultSet.getString("drugName"));
			temp.setDIN(resultSet.getString("DIN"));
			temp.setForm(resultSet.getString("form"));
			temp.setID(resultSet.getInt("prescriptionID")); // FIX IN SQL
			return temp;
		} catch (Exception e) {
			logErrors.log(e.getMessage() + " for getPrescription in SQLHelper");
			return null;
		}
	}

	public PrescriptionList getAllPrescriptions() {
		PrescriptionList pList = new PrescriptionList();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PrescriptionInfo");
			String[][] dosage = new String[1][1];
			while (resultSet.next()) {
				Prescription temp = new Prescription();
				temp.setPatientID(resultSet.getInt("patientID"));
				temp.setRefills(resultSet.getInt("numRefills"));
				temp.setQuantity(resultSet.getInt("quantity"));
				dosage[0][0] = resultSet.getString("dosage");
				temp.setDosage(dosage);
				temp.setInstructions(resultSet.getString("instructions"));
				temp.setDuration(resultSet.getString("prescribedDuration"));
				temp.setDate(resultSet.getString("datePrescribed"));
				temp.setBrandName(resultSet.getString("drugName"));
				temp.setDIN(resultSet.getString("DIN"));
				temp.setForm(resultSet.getString("form"));
				temp.setID(resultSet.getInt("prescriptionID"));
				temp.setCurrent(resultSet.getInt("current"));
				pList.insert(temp);
			}
		} catch (Exception e) {
			logErrors.log(e.getMessage() + " for getAllPrescriptions in SQLHelper");
		}
		return pList;
	}

	public LinkedList<Insurance> getAllInsurance() {
		LinkedList<Insurance> insuranceList = new LinkedList<Insurance>();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM InsuranceInfo");
			while (resultSet.next()) {
				Insurance temp = new Insurance();
				temp.setPatientID(resultSet.getInt("patientID"));
				temp.setCompany(resultSet.getString("company"));
				temp.setNumber(resultSet.getInt("number"));
				temp.setNotes(resultSet.getString("notes"));
				temp.setID(resultSet.getInt("insuranceID"));
				insuranceList.add(temp);
			}
		} catch (Exception e) {
			logErrors.log(e.getMessage() + " for getAllInsurance in SQLHelper");
		}
		return insuranceList;
	}

	public int addInsurance(String company, int number, String notes, int patientID) {
		try {
			ResultSet resultSet = statement.executeQuery(
					"SELECT insuranceID FROM InsuranceInfo WHERE insuranceID = (SELECT MAX(insuranceID) FROM InsuranceInfo)");
			resultSet.next();
			int ID = resultSet.getInt("insuranceID") + 1;
			statement.executeUpdate("INSERT INTO InsuranceInfo values ( " + patientID + " , \"" + company + "\" , "
					+ number + " , \"" + notes + "\" , " + ID + " )");
			return ID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " in addInsurance in SQLHelper");
			return -1;
		}
	}

	public void removeInsurance(int ID) {
		try {
			statement.executeUpdate("DELETE FROM InsuranceInfo WHERE insuranceID = \"" + ID + "\"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " in addPrescription in SQLHelper");
		}
	}

}
