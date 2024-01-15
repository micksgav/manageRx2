package utilities;

import PatientManagement.PatientList;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SQLHelper sql = new SQLHelper();
		
		PatientList list = sql.getAllPatients();
		System.out.println(list.returnData(2).getName());
		
		System.out.println(list.numRecs());
	}

}
