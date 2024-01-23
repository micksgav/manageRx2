/**
***********************************************
 @Name: getInteractions
 @Author           : Gavin Micks
 @Creation Date    : December 14, 2023
 @Modified Date	   : January 02, 2024
   @Description    : gets all drug interactions from cached dataset
   
************
**/
package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class getInteractions {
	static final String sep = System.getProperty("file.separator"); // "/" Equivalent for OS

	/** Method Name: listInteractions
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description lists drug interactions from a single DIN for all drugs
	* @Parameters  String DIN - DIN to check
	* @Returns String[][] = interactions info
	* Dependencies: BufferedReader, FileReader, ArrayList, logErrors
	* Throws/Exceptions: 
    */
	public static String[][] listInteractions(String DIN) {
		String ATC = DINtoATC(DIN); // Get ATC code from DIN

		try {
			// reader to read interactions list of ATC
			BufferedReader br = new BufferedReader(
					new FileReader("data" + sep + "drugs" + sep + "interactions" + sep + ATC + ".txt"));
			ArrayList<String[]> interactions = new ArrayList<>(); // arraylist of interactions found

			String line; // line read
			// while there are lines to read
			while ((line = br.readLine()) != null) {
				String temp; // temp reading
				String[] tempArray = new String[3]; // temp array to return of interaction info
				tempArray[0] = line.substring(1, line.indexOf(",")); // Name
				temp = line.substring(line.indexOf(",") + 2);
				tempArray[1] = ATC; // ATC
				temp = temp.substring(temp.indexOf(",") + 2);
				tempArray[2] = temp.substring(0, temp.length() - 1); // description
				interactions.add(tempArray); // add read-in interaction
			} // end while

			String[][] array = new String[interactions.size()][3]; // array to return
			// convert arraylist to array
			for (int i = 0; i < interactions.size(); i++) {
				array[i] = interactions.get(i);
			}
			return array; // return array
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in listInteractions in getInteractions for DIN " + DIN);
			return null; //if error occurs
		} //end try-catch
	} //end listInteractions

	/** Method Name: search
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description finds interactions between 2 drugs
	* @Parameters  String DIN1- first DIN to check, String DIN2 - second DIN to check
	* @Returns String[]  - interactions found
	* Dependencies: DINtoATC, listInteractions
	* Throws/Exceptions: N/A
    */
	public static String[] search(String DIN1, String DIN2) {
		try {
			String checkATC = DINtoATC(DIN2); //atc of second drug
			String checkATC2 = DINtoATC(DIN1); //atc of first drug
			String[][] allInteractions = listInteractions(DIN1); //get interactions of first drug
			String[][] allInteractions2 = listInteractions(DIN2); //get interactions of second drug

			//check for matching interactions
			for (String[] allInteraction : allInteractions) {
				if (allInteraction[0].compareTo(checkATC) == 0) {
					return allInteraction; //return found interaction
				} //end if
			} //end for
			
			//check other matching interactions
			for (String[] strings : allInteractions2) {
				if (strings[0].compareTo(checkATC2) == 0) {
					return strings; //return found interaction
				} //end if
			} //end for
		} catch (Exception e) {
			//error handling
			logErrors.log(e.getMessage() + " in Search in getInteractions for DINS " + DIN1 + " " + DIN2);
		}
		return null; //return null if error
	} //end search

	/** Method Name: arraySearch
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description finds interactions list of drugs
	* @Parameters  String[] DINS - DINS to check
	* @Returns String[]  - interactions found
	* Dependencies: search, ArrayList
	* Throws/Exceptions: N/A
    */
	public static String[][] arraySearch(String[] DINS) {
		try {
			boolean add = true; //add interaction (not duplicate)
			ArrayList<String[]> interactions = new ArrayList<>(); //list of matching interactions
			for (int i = 0; i < DINS.length; i++) { //for each din
				// for every other DIN
				for (int j = 0; j < DINS.length; j++) { 
					if (j != i) {
						String[] result = search(DINS[i], DINS[j]); //result of checking 2 DINS
						if (result != null) { //if it isn't null
							//check it isn't already stores
							for (String[] interaction : interactions) {
								if (result[2].equals(interaction[2])) {
									add = false; //don't add if stored
									break; //exit
								} //end if 
							} //end for 
							if (add)interactions.add(result); //add if it isnt a duplicate
						} //end if
					} //end if
				} //end for
			} //end for

			//convert arraylist to array
			String[][] array = new String[interactions.size()][3];
			for (int k = 0; k < interactions.size(); k++) {
				array[k] = interactions.get(k);
			} //end for
			
			return array; //return the array
		} catch (Exception e) {
			//error handling
			//error to store
			StringBuilder err = new StringBuilder(e.getMessage() + " in arraySearch in getInteractions for DINS: ");
			for (String din : DINS) err.append(" ").append(din);
			logErrors.log(err.toString());
		} //end try-catch
		return null; //return null in error
	} //end arraySearch


	/** Method Name: DINtoATC
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description converts DIN to ATC
	* @Parameters  String DIN - DIN to convert
	* @Returns String - ATC Found
	* Dependencies: bufferedreader, filereader
	* Throws/Exceptions: N/A
    */
	private static String DINtoATC(String DIN) {
		try {
			//reader for ATCs
			BufferedReader br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "DIN2ATC.txt"));

			//find ATC with matching DIN
			String line;
			while ((line = br.readLine()) != null) {
				if (line.substring(0, line.indexOf(" ")).compareTo(DIN) == 0) return line.substring(line.indexOf(" ") + 1);
			} //end while
		} catch (Exception e) {
			//error handling
			logErrors.log(e.getMessage() + " in DINtoATC in getInteractions for DIN " + DIN);
		} //end try-catch
		return "0"; //return 0 on error
	} //end DINtoATC
} //end getInteractions
