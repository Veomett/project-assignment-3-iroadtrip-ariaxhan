import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class readFiles {
	// constructor
	public readFiles(HashMap<String, String> stateNameMap, HashMap<String, Integer> capDistMap,
			HashMap<String, List<String>> bordersMap, HashMap<String, String> reverseStateMap) {
		// read in state names and make keys
		readStateName(stateNameMap, reverseStateMap);
		// use previously made map to filter out invalid countries
		readCapDist(capDistMap, stateNameMap);
		// use previously made map to filter out invalid countries
		readBorders(bordersMap, capDistMap);
	}

	// read state_name
	public void readStateName(HashMap<String, String> mapName, HashMap<String, String> reverseStateMap) {
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
				String c1 = "";
				String c2 = "";
				String value = "";
				for (int i = 0; i < currentValues.length; i++) {
					// get the second and fourth values, which are the country names
					if (i == 1) {
						c1 = currentValues[i];
					} else if (i == 2) {
						c2 += currentValues[i];
					} else if (i == 4) {
						value += currentValues[i] + "\n";
					} else {
						continue;
					}
				}
				// make sure the country still exists, aka the date is 2020-12-31
				if (value.equals("2020-12-31\n")) {
					// add to hashmap
					mapName.put(c1, c2);
					reverseStateMap.put(c2, c1);
				}
			}
			reader2.close();
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
			// skip first line
			reader1.readLine();
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
				// split key again to get the two countries

				mapName.put(key, value);
			}
			reader1.close();
		} catch (Exception e) {
			System.out.println("Error in readFile: " + e);
		}
	}

	// read borders
	public void readBorders(HashMap<String, List<String>>mapName,HashMap<String, Integer> capDistMap) {
		System.out.println("Starting readBorders...");
		// read files using buffered reader
		try {
			BufferedReader reader3 = new BufferedReader(new FileReader("borders.txt"));
			// read each line, split by '='
			String strCurrentLine;
			while ((strCurrentLine = reader3.readLine()) != null) {
				// split line 
				String[] fullLine = strCurrentLine.split("=");
				if (fullLine.length < 2) {
					System.out.println("Invalid line format: " + strCurrentLine);
					continue;
				}
				// get the first value, the country name
				String countryName = fullLine[0].trim();
				String[] borders = fullLine[1].split(";");
				// create a list to store the bordering countries
				List<String> borderCountries = new ArrayList<String>();

				// loop through borders and add them to the list
				for (int i = 0; i < borders.length; i++) {
					// split the corder string by space
					String border = borders[i];
					String borderCountry = border.trim().split(" ")[0];
					// check if country name is empty
					if (!borderCountry.isEmpty()) {
						borderCountries.add(borderCountry);
					}
				}
				// put main country and list of its bordering countries into the hashmap
				// make sure to exclude islands
				if (borderCountries.size() > 0) {

					mapName.put(countryName, borderCountries);

				}

			}
			reader3.close();
		} catch (Exception e) {
			System.out.println("Error in readFile: " + e);
		}
	}
}