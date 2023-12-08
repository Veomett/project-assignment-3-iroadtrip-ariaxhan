import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class readFiles {
	// constructor
	public readFiles(
			HashMap<String, String> stateNameMap,
			HashMap<String, Integer> capDistMap,
			HashMap<String, List<String>> bordersMap,
			HashMap<String, String> reverseStateMap,
			HashMap<String, String> edgeCases,
			HashMap<String, String> edgeCasesbyString) {
		// use previously made map to filter out invalid countries
		// read in edge cases
		readEdgeCases(edgeCases, edgeCasesbyString);
		// read in state names and make keys
		readStateName(stateNameMap, reverseStateMap);
		// use previously made map to filter out invalid countries
		readBorders(bordersMap, reverseStateMap, edgeCasesbyString);
		// use previously made maps to filter out invalid countries and get distances
		// between bordering countries
		readCapDist(capDistMap, bordersMap, stateNameMap, reverseStateMap, edgeCases);
	}

	// method to check if a country is an edge case
	public void readEdgeCases(HashMap<String, String> edgeCases, HashMap<String, String> edgeCasesbyString) {
		try {
			// check to see if it is an edge case read in edge cases file
			// make reader
			BufferedReader bufReader = new BufferedReader(new FileReader("edgecases.tsv"));
			// make string and string array to hold values
			String strCurrentLine;
			String[] currentValues;
			// loop through file and set each line as the current line
			while ((strCurrentLine = bufReader.readLine()) != null) {
				// split current line based on tab
				currentValues = strCurrentLine.split("\t");
				// place each part of the line in the appropriate place
				String code = "";
				String stringBorders = "";
				String stringState = "";
				// loop through values to check if it is an edge case
				for (int p = 0; p < currentValues.length; p++) {
					// get 0, which is the country code
					if (p == 0) {
						code = currentValues[p];
					} else if (p == 1) {
						stringState = currentValues[p];
					} else if (p == 2) {
						stringBorders = currentValues[p];
					} else {
						continue;
					}
				}
				// put in edge cases hashmap
				edgeCases.put(code, stringState + "_" + stringBorders);
				edgeCasesbyString.put(stringState, code);
			}
			bufReader.close();
		} catch (Exception e) {
			System.out.println("Error in reading edge cases : " + e);
		}
	}

	// read state_name
	public void readStateName(HashMap<String, String> stateMap, HashMap<String, String> reverseStateMap) {
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
						System.out.println("country 1 " + c1);
					} else if (i == 2) {
						c2 += currentValues[i];
						System.out.println("country name " + c2);
					} else if (i == 4) {
						value += currentValues[i] + "\n";
						System.out.println("date " + value);
					} else {
						continue;
					}
				}
				// make sure the country still exists, aka the date is 2020-12-31
				if (value.equals("2020-12-31\n")) {
					// add to hashmap
					stateMap.put(c1, c2);
					reverseStateMap.put(c2, c1);
				}
			}
			reader2.close();
		} catch (Exception e) {
			System.out.println("Error in readFile: " + e);
		}
	}

	// read capdist
	public void readCapDist(HashMap<String, Integer> mapName, HashMap<String, List<String>> bordersMap,
			HashMap<String, String> stateNameMap, HashMap<String, String> reverseStateMap,
			HashMap<String, String> edgeCases) {
		System.out.println("Starting readCapDist...");
		// read files using buffered reader
		int count = 0;
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
				// System.out.println("current line : " + strCurrentLine);
				currentValues = strCurrentLine.split(",");
				if (currentValues != null) {
					for (int i = 0; i < 5; i++) {
						// get the second and fourth values, which are the country names
						if (i == 1) {
							key += currentValues[i] + "_";
						} else if (i == 3) {
							key += currentValues[i];
							// get the fifth value, which is the distance
						} else if (i == 4) {
							value = Integer.parseInt(currentValues[i]);
							// System.out.println("trying to get distance");
							// System.out.println("distance " + value);
						} else {
							continue;
						}
					}
					// compare against stateNameMap to make sure the country still exists
					// split key again to get the two countries
					String[] countries = key.split("_");
					String country1 = countries[0];
					String country2 = countries[1];
					if (stateNameMap.get(country1) != null || stateNameMap.get(country2) != null) {
						// check against borders to make sure the countries border each other
						List<String> internalMap = bordersMap.get(country1);
						if (internalMap != null) {
							for (int r = 0; r < internalMap.size() - 1; r++) {
								String countryname = internalMap.get(r);
								String countryCode = reverseStateMap.get(countryname);
								if (countryCode != null && countryCode.equals(country2)) {
									if (!mapName.containsKey(key)) { // Check if the key is already present
										mapName.put(key, value);
										count++;
									}
								}
							}
						}
					}
				}
			}
			System.out.println("count " + count);
			reader1.close();
		} catch (Exception e) {
			System.out.println("Error in readFile: " + e);
		}
	}

	// read borders
	public void readBorders(HashMap<String, List<String>> mapName, HashMap<String, String> reverseStateMap,
			HashMap<String, String> edgeCasesbyString) {
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
				// put main country and list of its bordering countries into the hashmap convert
				// countries to codes
				String countryCode = reverseStateMap.get(countryName);
				if (countryName == null || countryCode == null) {
					countryCode = edgeCasesbyString.get(countryName);
					if (countryCode != null) {
						mapName.put(countryCode, borderCountries);
					}
				} else {
					mapName.put(countryCode, borderCountries);
				}
			}
			reader3.close();
		} catch (Exception e) {
			System.out.println("Error in readFile: " + e);
		}
	}
}