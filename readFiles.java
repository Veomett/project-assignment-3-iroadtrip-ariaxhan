import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class readFiles {

	// constructor
	public readFiles(HashMap<String, String> capDistMap, HashMap<String, String> stateNameMap, HashMap<String, String> bordersMap) {
		readCapDist(capDistMap);
		readStateName(stateNameMap);
		readBorders(bordersMap);
	}
  
  // read capdist
  public void readCapDist(HashMap<String, String> mapName) {
	// set up lines and return keys
	String key = "";
	String value = "";
	// read files using buffered reader
	try {
		BufferedReader reader1 = new BufferedReader(new FileReader("capdist.csv"));
		 // put each value in a list
      String strCurrentLine;
      String[] currentValues;
      while ((strCurrentLine = reader1.readLine()) != null) {
       // split current line
       currentValues = strCurrentLine.split(",");
        for (int i = 0; i < currentValues.length; i++){
          // get the second and fourth values, which are the country names
          if (i == 1) {
            key += currentValues[i] + "_";
          } else if (i == 3) {
            key += currentValues[i];
          } else if (i == 4) {
            value += currentValues[i];
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

  // read state_name
  public void readStateName(HashMap<String, String> mapName) {
	// set up lines and return keys
	String key = "";
	String value = "";
// read files using buffered reader
	try {
		BufferedReader reader2 = new BufferedReader(new FileReader("state_name.tsv"));
      String strCurrentLine;
      String[] currentValues;
      while ((strCurrentLine = reader2.readLine()) != null) {
       // split current line based on tab
	   currentValues = strCurrentLine.split("\t");
        for (int i = 0; i < currentValues.length; i++){
          // get the second and fourth values, which are the country names
          if (i == 1) {
            key += currentValues[i] + "_";
          } else if (i == 2) {
            key += currentValues[i];
          } else if (i == 4) {
            value += currentValues[i];
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
	// set up lines and return keys
	String key = "";
	String value = "";
	// read files using buffered reader
	try {
		BufferedReader reader3 = new BufferedReader(new FileReader("borders.txt"));
		// read each line, split by '='
		String strCurrentLine;
		while ((strCurrentLine = reader3.readLine()) != null) {
			// split line 
			String[] fullLine = strCurrentLine.split("=");
			// get the first value, the country name
			String countryName = fullLine[0].trim();
			// get the second value, the borders
			String borders = fullLine[1];
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
						value = border[1];
						mapName.put(key, value);}
				}
			}
		}
	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
	  }
}
