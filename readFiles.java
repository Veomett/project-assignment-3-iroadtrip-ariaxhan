  // read capdist
  public readCapDist(HashMap<String, String> mapName) {
	// set up lines and return keys
	String key = "";
	String value = "";
	// read files using buffered reader
	try {
		BufferedReader reader1 = new BufferedReader(new FileReader(capdist.csv));
		 // put each value in a list
      String strCurrentLine;
      String[] currentValues;
      while ((strCurrentLine = reader1.readLine()) != null) {
       // split current line
       currentValues = strCurrentLine.split(divider);
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
		BufferedReader reader2 = new BufferedReader(new FileReader(state_name.tsv));

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
	} catch (Exception e) {
      System.out.println("Error in readFile: " + e);
	  }
	  }

  // function to read in the files and use the putinHash function to put in hashmap
  public void readFiles( String filename, HashMap<String, String> mapName, String divider) {
         // set up lines and return keys
         String key = "";
         String value = "";
    try {
      // read files using buffered reader
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      // continue reading till end of file
      // put each value in a list
      String strCurrentLine;
      String[] currentValues;
      while ((strCurrentLine = reader.readLine()) != null) {
       // split current line
       currentValues = strCurrentLine.split(divider);
        for (int i = 0; i < currentValues.length; i++){
          // get the second and fourth values, which are the country names
          if (i == 1) {
            key += currentValues[i] + "_";
          } else if (i == 3) {
            key += currentValues[i];
          }
          // get the 5th, which is the distance
          if (i == 4) {
            value += currentValues[i];
          }
       }
       mapName.put(key, value);
       }
      
      // close the reader
      reader.close();
      
    } catch (Exception e) {
      System.out.println("Error in readFile: " + e);
      }
    
      }