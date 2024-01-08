package inventory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class findAlternatives {
    //example usage:
//    String[] test = findAlternative("02513889");
//    for (int i = 0; i < test.length; i++) System.out.println(test[i]);
    public static String[] findAlternative(String inDIN) {
       try {
           ArrayList<String> alts = new ArrayList<>();
           BufferedReader br = new BufferedReader(new FileReader("data\\drugs\\drugData.txt"));

           String line;
           String line2;
           String code;

           while ((line = br.readLine()) != null) {
               if (line.substring(0, line.indexOf(" ")).equals(inDIN)) {
                   line2 = br.readLine();
                   code = line2.substring(0, line2.indexOf(" "));
                   br.close();
                   br = new BufferedReader(new FileReader("data\\drugs\\drugData.txt"));
                   while ((line = br.readLine()) != null) {
                       line2 = br.readLine();
                       if (line2.substring(0, line2.indexOf(" ")).equals(code)) {
                           alts.add(line.substring(0, line.indexOf(" ")));
                       }
                   }
                   break;
               }
           }
           String[] toReturn = new String[alts.size()];
           for (int i = 0; i < alts.size(); i++) toReturn[i] = alts.get(i);
           return toReturn;

       } catch (Exception e) {
           //add log logic
           System.out.println(e.getMessage());
           return null;
       }
    }



}
