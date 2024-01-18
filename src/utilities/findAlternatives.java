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
    static final String sep = System.getProperty("file.separator");
    //example usage:
//    String[] test = findAlternative("02513889");
//    for (int i = 0; i < test.length; i++) System.out.println(test[i]);
    public static String[][] findAlternative(String inDIN) {
       try {
           ArrayList<String> alts = new ArrayList<>();
           ArrayList<String> names = new ArrayList<>();
           ArrayList<String> dosages = new ArrayList<>();
           BufferedReader br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));

           String line;
           String line2;
           String code;

           while ((line = br.readLine()) != null) {
               if (line.substring(0, line.indexOf(" ")).equals(inDIN)) {
                   line2 = br.readLine();
                   code = line2.substring(0, line2.indexOf(" "));
                   br.close();
                   br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));
                   while ((line = br.readLine()) != null) {
                       line2 = br.readLine();
                       if (line2.substring(0, line2.indexOf(" ")).compareTo(code) == 0) {
                           alts.add(line.substring(0, line.indexOf(" ")));
                           names.add(line.substring(line.indexOf(" ")));

                           String[][] dosage = getDosage(line2.substring(line2.indexOf(" ") + 1));
                           dosages.add(dosage[0][1] + " " + dosage[0][2]);
                       
                       }
                   }
                   break;
               }
           }
           String[][] toReturn = new String[alts.size()][3];
           for (int i = 0; i < alts.size(); i++) {
        	   toReturn[i][0] = alts.get(i);
        	   toReturn[i][1] = names.get(i);
        	   toReturn[i][2] = dosages.get(i);
           }
           return toReturn;

       } catch (Exception e) {
           logErrors.log(e.getMessage() + " in findAlternative in findAlternatives for DIN " + inDIN);
           return null;
       }
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
