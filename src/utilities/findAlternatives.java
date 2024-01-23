/**
***********************************************
 @Name: findAlternatives
 @Author           : Gavin Micks
 @Creation Date    : December 17, 2023
 @Modified Date	   : January 15, 2024
   @Description    : Finds other drugs that may match
   
************
**/
package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class findAlternatives {
	static final String sep = System.getProperty("file.separator"); // "/" Equivalent for OS

	/** Method Name: findAlternative
	* @Author Gavin Micks
	* @Date December 22, 2023
	* @Modified January 04, 2024
	* @Description finds alternatives for a DIN
	* @Parameters  String inDIN - DIN to check alts for
	* @Returns String[][] //alternative drug data
	* Dependencies: BufferedReader, FileReader, logErrors
	* Throws/Exceptions: N/A
    */
	public static String[][] findAlternative(String inDIN) {
		try {
			ArrayList<String> alts = new ArrayList<>(); // alternative DINS
			ArrayList<String> names = new ArrayList<>(); // alternative names
			ArrayList<String> dosages = new ArrayList<>(); // alternative dosages

			// reader for drug data
			BufferedReader br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));

			String line; // line with name and DIN
			String line2; // line with drugCode
			String code; // drugCode

			// while there is a next line in drug data
			while ((line = br.readLine()) != null) {
				if (line.substring(0, line.indexOf(" ")).equals(inDIN)) { // if the DIN is found
					line2 = br.readLine();
					code = line2.substring(0, line2.indexOf(" ")); // get drugCode
					br.close(); // close BR to reset
					// reopen BR
					br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));

					// while there is a next line
					while ((line = br.readLine()) != null) {
						line2 = br.readLine();
						if (line2.substring(0, line2.indexOf(" ")).compareTo(code) == 0) { // if drugCode matches
							// add as an alternative
							alts.add(line.substring(0, line.indexOf(" ")));
							names.add(line.substring(line.indexOf(" ")));
							// find the dosage of the DIN
							String[][] dosage = getDosage(line2.substring(line2.indexOf(" ") + 1));
							dosages.add(dosage[0][1] + " " + dosage[0][2]);
						} // end if
					} // end while
					break;
				} // end if
			} // end while

			String[][] toReturn = new String[alts.size()][3]; // formatting for return
			// convert arraylists to arrays
			for (int i = 0; i < alts.size(); i++) {
				toReturn[i][0] = alts.get(i);
				toReturn[i][1] = names.get(i);
				toReturn[i][2] = dosages.get(i);
			} // end for
			return toReturn; // return formatted array

		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in findAlternative in findAlternatives for DIN " + inDIN);
			return null; // if error occurs return null
		} // end try-catch
	} // end findAlternative

	/** Method Name: getDosage
	* @Author Gavin Micks
	* @Date December 22, 2023
	* @Modified January 04, 2024
	* @Description gets dosage of a drug from drugCode
	* @Parameters  String drugCode - code of drug to find dosage for
	* @Returns String[][] //dosage info
	* Dependencies: HttpURLConnectionm URL, BufferedReader, StringBuilder, JSON, InputStream
	* Throws/Exceptions: N/A
    */
	private static String[][] getDosage(String drugCode) {

		try {
			ArrayList<String[]> list = new ArrayList<>(); //dosages found

			//api calls to health canada
			URL url = new URL(
					"https://health-products.canada.ca/api/drug/activeingredient/?lang=en&type=json&id=" + drugCode);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			InputStream is = conn.getInputStream(); //api response
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); //reader to read api response
			StringBuilder result = new StringBuilder(); //result of response
			String line; //line read from json response

			//while there is lines in the api response
			while ((line = br.readLine()) != null) {
				result.append(line); //store line
			} //end while

			br.close(); //close reader

			JSONArray jsonA = new JSONArray(result.toString()); //store as JSONArray

			//convert jsonArray to array
			for (int i = 0; i < jsonA.length(); i++) {
				JSONObject record = jsonA.getJSONObject(i);
				String[] temp = new String[3]; //dosage entry
				temp[0] = record.optString("ingredient_name");
				temp[1] = record.optString("strength");
				temp[2] = record.optString("strength_unit");
				list.add(temp);
			} //end for
			
			//convert arraylist list to array ret
			String[][] ret = new String[list.size()][3];
			for (int i = 0; i < list.size(); i++) {
				ret[i] = list.get(i);
			}
			return ret; //return dosages
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in getDosage in findAlternatives");
			return null;
		} // end try-catch
	} //end getDosage
} //end findAlternatives
