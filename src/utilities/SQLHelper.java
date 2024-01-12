package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import PatientManagement.Patient;

public class SQLHelper {
	ExecutorService service;
	boolean status;
	int IDnum;
	Statement statement;
	Connection connect = null;
	
	//EXAMPLE MAIN CODE:
	//SQLHelper sql = new SQLHelper();
	
	//sql.addPatientBG("test1", 9, 10, 2007, "111 Street St", "1234567890", "hhfhfh@nuef.ca", "43975433223AB", "ahhhhh", "Dr. Smith", "123 Road Rd", "1112223333");
	//sql.addPatientBG("test555", 9, 10, 2007, "113 Street St", "1234567890", "hhfhfh@nuef.ca", "43975433223AB", "ahhhhh", "Dr. Smith", "123 Road Rd", "1112223333");
	//System.out.println("This code will run while patient is being added");

	public SQLHelper() {
	    service = Executors.newFixedThreadPool(1);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://managerx.cxa8k0i6mls8.ca-central-1.rds.amazonaws.com:3306/manageRx", "manageRxAdmin", "manageRxSQL*!");
	        statement = connect.createStatement();
	        connect.setAutoCommit(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage()+ " during connecting in SQLHelper");
		}
		
	}
	
	public void updateBG(String table, String column, Object obj, int ID) {
		service.submit(new Runnable() {
	        public void run() {
	            status = update(table, column, obj, ID);
	        }
	    });
	}
	
	public void addPatientBG(String name, int birthDay, int birthMonth, int birthYear, String address, String phoneNumber, String email, String healthCard, String additional, String familyDoctorName, String familyDoctorAddress, String familyDoctorPhoneNumber) {
		service.submit(new Runnable() {
	        public void run() {
	            IDnum = addPatient(name, birthDay, birthMonth, birthYear, address, phoneNumber, email, healthCard, additional, familyDoctorName, familyDoctorAddress, familyDoctorPhoneNumber);
	            System.out.println("patient added: " + IDnum);
	        }
	    });
	}
	
	//table = table name, column = column name, obj = value to update to (String or int), ID - ID of row
	public boolean update(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for pulling from
				// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for pushing to		
				try {
		            statement.executeUpdate("UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE ID = " + ID);
		            return true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj + ", ID " + ID + " in update in SQLHelper");
					return false;
				}
	}
	public int addPatient(String name, int birthDay, int birthMonth, int birthYear, String address, String phoneNumber, String email, String healthCard, String additional, String familyDoctorName, String familyDoctorAddress, String familyDoctorPhoneNumber) {
		try {
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM PatientInfo WHERE ID = (SELECT MAX(ID) FROM PatientInfo)");
            resultSet.next();
            int ID = resultSet.getInt("ID") + 1;
            statement.executeUpdate("INSERT INTO PatientInfo values ( " + ID + " , \"" + name + "\" , " + birthDay + " , " + birthMonth + " , " + birthYear + " , \"" + address + "\" , \"" + phoneNumber + "\" , \"" + email + "\" , \"" + healthCard + "\" , \"" + additional + "\" , \"" + familyDoctorName + "\" , \"" + familyDoctorAddress + "\" , \"" + familyDoctorPhoneNumber + "\")");
            return ID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logErrors.log(e.getMessage() + " in addPatient in SQLHelper");
			return -1;

		}
		
		
	}
}
