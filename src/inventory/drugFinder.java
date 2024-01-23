/**
***********************************************
 @Name: drugFinder
 @Author           : Gavin Micks
 @Creation Date    : December 17, 2023
 @Modified Date	   : January 15, 2024
   @Description    : Find information about a drug using Health Canada
   
************
**/
package inventory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class drugFinder {
    static final String sep = System.getProperty("file.separator"); //file seperator

    //call this method to get the drug information
    //Drug drugName = drugFinder.getDrug(*put DIN here*);

	/** Method Name: getDrug
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description main method to get drug info
	* @Parameters  String DIN - DIN of drug to get info for 
	* @Returns Drug
	* Dependencies: getATC, getSchedule, getProduct, getDosage, getForm, BufferedReader, FileReader
	* Throws/Exceptions: IOException
    */
    public static Drug getDrug(String DIN) throws IOException {
    	//open drugData
        BufferedReader br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));

        String line; //DIN and Name
        String line2; //DPC

        while ((line = br.readLine()) != null) {
        	//look for DIN
            if (line.substring(0, line.indexOf(" ")).equals(DIN)) {
                line2 = br.readLine();
                String drugNameBrand = line.substring(line.indexOf(" ") + 1); //Brand Name - from drugData.txt;
                String DPC = line2.substring(line2.indexOf(" ") + 1); //used in backend - from drugData.txt;

                String[] therapeuticClass = getATC(DPC);
                String drugClass = therapeuticClass[1]; //Drug Class - HC DPD Therapeutic Class X


                String drugSchedule = getSchedule(DPC); //Schedule - HC DPD Schedule X

                String[] drugProduct = getProduct(DIN);
                String company = drugProduct[0]; //Company that makes it - HC DPD Drug Product X
                String description = drugProduct[1]; //Description (maybe used?) - HC DPD Drug Product

                String drugForm = getForm(DPC); //Drug Form (pill, cream, etc)

                String dosage = getDosage(DPC)[0][0] + " " + getDosage(DPC)[0][1] + " " + getDosage(DPC)[0][2]; //each column is an active ingredient, dosage, and unit

                return new Drug(DIN, drugNameBrand, drugClass, drugSchedule, company, description, drugForm, dosage);
            } //end if
        } //end while
        return null;
    } //end getDrug

	/** Method Name: getATC
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description gets ATC from health canada
	* @Parameters  String drugCode - code of drug to get info for 
	* @Returns String[] - ATC and ATC num
	* Dependencies: HttpURLConnectionm URL, BufferedReader, StringBuilder, JSON, InputStream
	* Throws/Exceptions: IOException
    */
    private static String[] getATC(String drugCode) throws IOException {
 
        String[] response = new String[2]; //atc and atc num
        //api call to health canada
        URL url = new URL("https://health-products.canada.ca/api/drug/therapeuticclass/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //read in result from api call
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        } //end while


        br.close(); //close reader
        result = new StringBuilder(result.substring(1, result.length() - 1)); //formatting
        JSONObject record = new JSONObject(result.toString()); //store as JSONObject
        //parse data
        response[0] = record.optString("tc_atc_number");
        response[1] = record.optString("tc_atc");
        return response;
    } //end getATC

    /** Method Name: getSchedule
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description gets schedule from health canada
	* @Parameters  String drugCode - code of drug to get info for 
	* @Returns String - schedule of drug
	* Dependencies: HttpURLConnectionm URL, BufferedReader, StringBuilder, JSON, InputStream
	* Throws/Exceptions: IOException
    */
    private static String getSchedule(String drugCode) throws IOException {
    	//API Calls to health canada
        URL url = new URL("https://health-products.canada.ca/api/drug/schedule/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //read in result from API call
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }


        br.close(); //close reader
        result = new StringBuilder(result.substring(1, result.length() - 1));
        JSONObject record = new JSONObject(result.toString()); //store as JSON Object
        return record.optString("schedule_name");
    } //end getSchedule

	/** Method Name: getProduct
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description gets product from health canada
	* @Parameters  String DIN - DIN of drug to get info for 
	* @Returns String[] - product info
	* Dependencies: HttpURLConnectionm URL, BufferedReader, StringBuilder, JSON, InputStream
	* Throws/Exceptions: IOException
    */
    private static String[] getProduct(String DIN) throws IOException {
        String[] response = new String[2]; //prodcut info
        //api calls to health canada
        URL url = new URL("https://health-products.canada.ca/api/drug/drugproduct/?lang=en&type=json&din=" + DIN);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //reading api results
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        br.close(); //close reader
        result = new StringBuilder(result.substring(1, result.length() - 1));
        JSONObject record = new JSONObject(result.toString()); //store as JSOn object
        //parse response
        response[0] = record.optString("company_name");
        response[1] = record.optString("descriptor");
        return response;
    } //end getProduct

	/** Method Name: getForm
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description gets form from health canada
	* @Parameters  String drugCode - code of drug to get info for 
	* @Returns String - form of drug
	* Dependencies: HttpURLConnectionm URL, BufferedReader, StringBuilder, JSON, InputStream
	* Throws/Exceptions: IOException
    */
    private static String getForm(String drugCode) throws IOException {
    	
    	//API call to health Canada
        URL url = new URL("https://health-products.canada.ca/api/drug/form/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //read api result
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }


        br.close(); //close reader
        result = new StringBuilder(result.substring(1, result.length() - 1));
        JSONObject record = new JSONObject(result.toString()); //convert to JSONObject
        return record.optString("pharmaceutical_form_name"); //parse result
    } //end getForm

	/** Method Name: getDosage
	* @Author Gavin Micks
	* @Date December 17, 2023
	* @Modified December 20, 2024
	* @Description gets dosage from health canada
	* @Parameters  String drugCode - code of drug to get info for 
	* @Returns String[][] - dosage info
	* Dependencies: HttpURLConnectionm URL, BufferedReader, StringBuilder, JSON, InputStream
	* Throws/Exceptions: IOException
    */
    private static String[][] getDosage(String drugCode) throws IOException {
        ArrayList<String[]> list = new ArrayList<>(); //dosage response list

        //api calls to health canada
        URL url = new URL("https://health-products.canada.ca/api/drug/activeingredient/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //reading api response
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        br.close(); //close reader

        JSONArray jsonA = new JSONArray(result.toString()); //store in JSONArray

        //parse data
        for (int i = 0; i < jsonA.length(); i++) {
            JSONObject record = jsonA.getJSONObject(i);
            String[] temp = new String[3];
            temp[0] = record.optString("ingredient_name");
            temp[1] = record.optString("strength");
            temp[2] = record.optString("strength_unit");
            list.add(temp);
        }//end for
        
        //convert to array
        String[][] ret = new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        } //end for
        return ret;
    } //end getDosage

} //end drugFinder
