package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchForDIN {
	
	static final String sep = System.getProperty("file.separator");

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String DIN = getDIN("adderall");

		System.out.println(DIN);
	}
	
	public static String getDIN(String drugName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));

        String line;

        while ((line = br.readLine()) != null) {
        	br.readLine();
            if (line.substring(line.indexOf(" ")).toLowerCase().contains(drugName.toLowerCase())) {
                System.out.println(drugName + " has been found.");
                return (line.substring(0, line.indexOf(" ")));
            }
        }
        
        return null;
	}

}
