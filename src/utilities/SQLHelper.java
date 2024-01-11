package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import PatientManagement.Patient;

public class SQLHelper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(addPatient("joe", 9, 10, 2006, "127 Britannia Ave", "5196302643", "micksgav@gmail.com", "9835734987", "github supporter be warned"));
	}
	
	//table = table name, column = column name, obj = value to update to (String or int), ID - ID of row
	public static boolean update(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for pulling from
				// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for pushing to
				Connection connect = null;
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					connect = DriverManager.getConnection("jdbc:mysql://managerx.cxa8k0i6mls8.ca-central-1.rds.amazonaws.com:3306/manageRx", "manageRxAdmin", "manageRxSQL*!");
					Statement statement;
		            statement = connect.createStatement();
		            connect.setAutoCommit(true);
		            statement.executeUpdate("UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE ID = " + ID);
		            connect.close();
		            return true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					try {
						connect.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						logErrors.log("Can't close conection in update in SQLHelper");
					}
					logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj + ", ID " + ID + " in update in SQLHelper");
					return false;
				}
	}
	public static int addPatient(String name, int birthDay, int birthMonth, int birthYear, String address, String phoneNumber, String email, String healthCard, String additional) {
		Connection connect = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://managerx.cxa8k0i6mls8.ca-central-1.rds.amazonaws.com:3306/manageRx", "manageRxAdmin", "manageRxSQL*!");
			Statement statement;
            statement = connect.createStatement();
            connect.setAutoCommit(true);
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM PatientInfo WHERE ID = (SELECT MAX(ID) FROM PatientInfo)");
            resultSet.next();
            int ID = resultSet.getInt("ID") + 1;
            statement.executeUpdate("INSERT INTO PatientInfo values ( " + ID + " , \"" + name + "\" , " + birthDay + " , " + birthMonth + " , " + birthYear + " , \"" + address + "\" , \"" + phoneNumber + "\" , \"" + email + "\" , \"" + healthCard + "\" , \"" + additional + "\")");
            
            connect.close();
            return ID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				connect.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logErrors.log("Can't close conection in update in SQLHelper");
			}
			logErrors.log(e.getMessage() + " in addPatient in SQLHelper");
			return -1;

		}
		
		
	}
}
