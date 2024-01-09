package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class findAlternatives {
    static final String sep = System.getProperty("file.separator");
    //example usage:
//    String[] test = findAlternative("02513889");
//    for (int i = 0; i < test.length; i++) System.out.println(test[i]);
    public static String[][] findAlternative(String inDIN) {
       try {
           ArrayList<String> alts = new ArrayList<>();
           ArrayList<String> names = new ArrayList<>();
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
                       }
                   }
                   break;
               }
           }
           String[][] toReturn = new String[alts.size()][2];
           for (int i = 0; i < alts.size(); i++) {
        	   toReturn[i][0] = alts.get(i);
        	   toReturn[i][1] = names.get(i);
           }
           return toReturn;

       } catch (Exception e) {
           logErrors.log(e.getMessage() + " in findAlternative in findAlternatives for DIN " + inDIN);
           return null;
       }
    }



}
