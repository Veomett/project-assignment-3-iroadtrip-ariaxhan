import java.util.List;

public class IRoadTrip {


/*
 * Constructor
 * @param args command line arguments
 * files: borders.txt capdist.csv state_name.tsv. 
 * Read the files and prepare to execute the implementation to respond to requests. 
 * The implementation must halt on any failure here.
 */
    public IRoadTrip (String [] args) {
        // Replace with your code
        try {
            String borders = args[0];
            String capdist = args[1];
            String state_name = args[2];
            if (borders != "borders.txt" || capdist != "capdist.csv" || state_name != "state_name.tsv") {
                System.out.println("Invalid ");
            }

        } catch (Exception e) {
            System.out.println("IRoadTrip - constructor");
        }
    }


    public int getDistance (String country1, String country2) {
        // Replace with your code
        return -1;
    }


    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        return null;
    }


    public void acceptUserInput() {
        // Replace with your code
        System.out.println("IRoadTrip - skeleton");
    }


    public static void main(String[] args) {
        IRoadTrip a3 = new IRoadTrip(args);

        a3.acceptUserInput();
    }

}

