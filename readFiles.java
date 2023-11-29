import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class readFiles {

	// constructor
	public readFiles(HashMap<String, String> capDistMap, HashMap<String, String> stateNameMap, HashMap<String, String> bordersMap) {
		System.out.println("Enterted readFiles constructor...");
		readCapDist(capDistMap);
		readStateName(stateNameMap);
		readBorders(bordersMap);
	}
  
  // read capdist
  public void readCapDist(HashMap<String, String> mapName) {
	System.out.println("Starting readCapDist...");
	// read files using buffered reader
	try {
		BufferedReader reader1 = new BufferedReader(new FileReader("capdist.csv"));
		 // put each value in a list
      String strCurrentLine;
      String[] currentValues;
      while ((strCurrentLine = reader1.readLine()) != null) {
		// set up lines and return keys
		String key = "";
		String value = "";
       // split current line
       currentValues = strCurrentLine.split(",");
        for (int i = 1; i < currentValues.length; i++){
          // get the second and fourth values, which are the country names
          if (i == 1) {
            key += currentValues[i] + "_";
          } else if (i == 3) {
            key += currentValues[i];
          } else if (i == 4) {
            value += currentValues[i] + "\n";
          } else {
			continue;
		  }
       }
       mapName.put(key, value);
	  // System.out.println("Key: " + key + " Value: " + value);
       }

	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
  }

  // read state_name
  public void readStateName(HashMap<String, String> mapName) {
	System.out.println("Starting readStateName...");
// read files using buffered reader
	try {
		BufferedReader reader2 = new BufferedReader(new FileReader("state_name.tsv"));
      String strCurrentLine;
      String[] currentValues;
      while ((strCurrentLine = reader2.readLine()) != null) {
			// set up lines and return keys
	String key = "";
	String value = "";
       // split current line based on tab
	   currentValues = strCurrentLine.split("\t");
        for (int i = 0; i < currentValues.length; i++){
          // get the second and fourth values, which are the country names
          if (i == 1) {
            key += currentValues[i] + "_";
          } else if (i == 2) {
            key += currentValues[i];
          } else if (i == 4) {
            value += currentValues[i]  + "\n";
          } else {
			continue;
		  }
       }
       mapName.put(key, value);
       }	
	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
	  }

  // read borders
  public void readBorders(HashMap<String, String> mapName) {
	System.out.println("Starting readBorders...");
	// read files using buffered reader
	try {
		BufferedReader reader3 = new BufferedReader(new FileReader("borders.txt"));
		// read each line, split by '='
		String strCurrentLine;
		while ((strCurrentLine = reader3.readLine()) != null) {
			System.out.println(strCurrentLine);
			// split line 
			String[] fullLine = strCurrentLine.split("=");
			 if (fullLine.length < 2) {
				System.out.println("Invalid line format: " + strCurrentLine);
				continue;
            }
			// get the first value, the country name
			String countryName = fullLine[0].trim();
            String borders = fullLine[1].trim();
            if (borders.isEmpty()) {
                continue;
            }
			String countryName = fullLine[0].trim();
			System.out.println(countryName);
			// get the second value, the borders
			String borders = fullLine[1];
			System.out.println(borders);
			// if there are no borders, the country is invalid
			if (borders.equals(" ")) {
				continue;
			} // otherwise, split by semicolon 
			else {
				String[] borderArray = borders.split(";");
				// for each border, split into key and value based on space
				for (int i = 0; i < borderArray.length; i++) {
					String[] border = borderArray[i].split(" ");
					// if the border is not empty, put in hashmap
					if (!border[0].equals("")) {
						key = countryName + "_" + border[0];
						value = border[1] + "\n";
						mapName.put(key, value);}
				}
			}
		}
	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
	  }
}
