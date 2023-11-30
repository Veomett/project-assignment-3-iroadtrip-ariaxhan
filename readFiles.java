import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class readFiles {

	public readFiles(HashMap<String, String> stateNameMap, HashMap<String, Integer> capDistMap,
			HashMap<String, String> bordersMap) {
		// read in state names and make keys
		readStateName(stateNameMap);
		// use previously made map to filter out invalid countries
		readCapDist(capDistMap, stateNameMap);
		// use previously made map to filter out invalid countries
		readBorders(bordersMap, capDistMap);
	}
  

  // read state_name
  public void readStateName(HashMap<String, String> mapName) {
	System.out.println("Starting readStateName...");
// read files using buffered reader
try {
		// make reader
		BufferedReader reader2 = new BufferedReader(new FileReader("state_name.tsv"));
		// make string and string array to hold values
      String strCurrentLine;
		String[] currentValues;
	  // loop through file and set each line as the current line
		while ((strCurrentLine = reader2.readLine()) != null) {
       // split current line based on tab
		currentValues = strCurrentLine.split("\t");
		// place each part of the line in the appropriate place
		String key = "";
		String value = "";
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
	   // make sure the country still exists, aka the date is 2020-12-31
		if (value.equals("2020-12-31\n")) {
		mapName.put(key, value);
	   }
       }	
	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
	  }


  // read capdist
  public void readCapDist(HashMap<String, Integer> mapName, HashMap<String, String> stateNameMap) {
	System.out.println("Starting readCapDist...");
	// read files using buffered reader
	try {
		BufferedReader reader1 = new BufferedReader(new FileReader("capdist.csv"));
		 // put each value in a list
      String strCurrentLine;
		String[] currentValues;
	  // loop through file and set each line as the current line
      while ((strCurrentLine = reader1.readLine()) != null) {
		// set up lines and return keys
		String key = "";
		int value = 0;
       // split current line based on comma
       currentValues = strCurrentLine.split(",");
		for (int i = 1; i < currentValues.length; i++) {
			// get the second and fourth values, which are the country names
			if (i == 1) {
				key += currentValues[i] + "_";
			} else if (i == 3) {
				key += currentValues[i];
				// get the fifth value, which is the distance
			} else if (i == 4) {
				value = Integer.parseInt(currentValues[i]);
			} else {
				continue;
			}
		}
		// compare against stateNameMap to make sure the country still exists
	   
       mapName.put(key, value);
       }

	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
  }

  // read borders
  public void readBorders(HashMap<String, String> mapName, HashMap<String, Integer> capDistMap) {
	System.out.println("Starting readBorders...");
	// read files using buffered reader
	try {
		BufferedReader reader3 = new BufferedReader(new FileReader("borders.txt"));
		// read each line, split by '='
		String strCurrentLine;
		while ((strCurrentLine = reader3.readLine()) != null) {
			System.out.println("current line: " + strCurrentLine);
			// split line 
			String[] fullLine = strCurrentLine.split("=");
			 if (fullLine.length < 2) {
				System.out.println("Invalid line format: " + strCurrentLine);
				continue;
            }
			// get the first value, the country name
			String countryName = fullLine[0].trim();
            String borders = fullLine[1].trim();
			//System.out.println("country name " + countryName);
			// if there are no borders, the country is invalid
			if (borders.equals(" ")) {
				//System.out.println("Invalid country: " + countryName);
				continue;
			} // otherwise, split by semicolon 
			else {
				String[] borderArray = borders.split(";");
				for (int k = 0; k < borderArray.length; k++) {
					//System.out.println("borderarray entry: " + borderArray[k]);
				}
				// for each border, split into key and value based on space
				for (int i = 0; i < borderArray.length; i++) {
					String[] border = borderArray[i].trim().split(" ");
					ArrayList<String> onlyBorders = new ArrayList<String>();
				for (int m = 0; m < border.length; m++) {
					// check for invalid characters and add to arraylist
					if (!border[m].equals("") && !border[m].equals("km") && !isInteger(border[m]) && !border[m].equals(" ")) {
						onlyBorders.add(border[m]);
					}
				}
				for (int n = 0; n < onlyBorders.size(); n++) {
				System.out.println("only borders: " + onlyBorders.get(n));

					// if the border is not empty, put in hashmap
					
					if (!onlyBorders.get(n).equals("")) {
						String key = countryName + "_" + onlyBorders.get(n);
						String borderExists = "true \n";
						mapName.put(key, borderExists);}
					
				}
				}
			}
		}
	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
	  }

	  
	  public static boolean isInteger(String str) {
    if (str == null) {
        return false;
    }
	// regex for digits, courtesy of chatGPT
    return str.matches("\\d{1,3}([,.]\\d{3})*");
}

}
