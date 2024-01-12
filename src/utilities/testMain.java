package utilities;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SQLHelper sql = new SQLHelper();
		
		sql.addPatientBG("test1", 9, 10, 2007, "111 Street St", "1234567890", "hhfhfh@nuef.ca", "43975433223AB", "ahhhhh", "Dr. Smith", "123 Road Rd", "1112223333");
		sql.addPatientBG("test555", 9, 10, 2007, "113 Street St", "1234567890", "hhfhfh@nuef.ca", "43975433223AB", "ahhhhh", "Dr. Smith", "123 Road Rd", "1112223333");
		System.out.println("This code will run while patient is being added");
	}

}
