package inventory;

import org.json.JSONArray;
import org.json.JSONObject;
import utilities.findAlternatives;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class drugFinder {
    static final String sep = System.getProperty("file.separator");

    //call this method to get the drug information
    //Drug drugName = drugFinder.getDrug(*put DIN here*);

    public static Drug getDrug(String DIN) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));

        String line;
        String line2;

        while ((line = br.readLine()) != null) {
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
            }
        }
        return null;
    }

    private static String[] getATC(String drugCode) throws IOException {
        String[] response = new String[2];
        URL url = new URL("https://health-products.canada.ca/api/drug/therapeuticclass/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }


        br.close();
        result = new StringBuilder(result.substring(1, result.length() - 1));
        JSONObject record = new JSONObject(result.toString());
        response[0] = record.optString("tc_atc_number");
        response[1] = record.optString("tc_atc");
        return response;
    }

    private static String getSchedule(String drugCode) throws IOException {
        URL url = new URL("https://health-products.canada.ca/api/drug/schedule/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }


        br.close();
        result = new StringBuilder(result.substring(1, result.length() - 1));
        JSONObject record = new JSONObject(result.toString());
        return record.optString("schedule_name");
    }

    private static String[] getProduct(String DIN) throws IOException {
        String[] response = new String[2];
        URL url = new URL("https://health-products.canada.ca/api/drug/drugproduct/?lang=en&type=json&din=" + DIN);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        br.close();
        result = new StringBuilder(result.substring(1, result.length() - 1));
        JSONObject record = new JSONObject(result.toString());
        response[0] = record.optString("company_name");
        response[1] = record.optString("descriptor");
        return response;
    }

    private static String getForm(String drugCode) throws IOException {
        URL url = new URL("https://health-products.canada.ca/api/drug/form/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }


        br.close();
        result = new StringBuilder(result.substring(1, result.length() - 1));
        JSONObject record = new JSONObject(result.toString());
        return record.optString("pharmaceutical_form_name");
    }

    private static String[][] getDosage(String drugCode) throws IOException {
        ArrayList<String[]> list = new ArrayList<>();

        URL url = new URL("https://health-products.canada.ca/api/drug/activeingredient/?lang=en&type=json&id=" + drugCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        br.close();

        JSONArray jsonA = new JSONArray(result.toString());

        for (int i = 0; i < jsonA.length(); i++) {
            JSONObject record = jsonA.getJSONObject(i);
            String[] temp = new String[3];
            temp[0] = record.optString("ingredient_name");
            temp[1] = record.optString("strength");
            temp[2] = record.optString("strength_unit");
            list.add(temp);
        }
        String[][] ret = new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

}
