import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 
 * @author Angel Selva Rodriguez 
 * @version 07/29/2020
 *
 */
public class Driver {
	public static Scanner userInput = new Scanner(System.in);// for input from user
	public static int countCountries;
	private static final HashTable_via_DoubleEnded_SinglyLinkedList hTable = new HashTable_via_DoubleEnded_SinglyLinkedList();

	public static void main(String[] args) {

		System.out.println("COP3530 Project 3 - Angel Selva Rodriguez");
		System.out.println(
				"Hash Table with Separate Chaining to handle Collisions using a Double-Ended Singly-Linked-List");
		System.out.println("Please enter file name: ");

		/**
		 * 1. Read the Countries3.csv file of countries and create a hash table by
		 * calling the insert method, and display the hash table by calling the display
		 * method.
		 */
		String fileName = userInput.next();

		readFile(fileName, hTable, userInput);
		System.out.println("There were " + countCountries + " country records read into the HashTable_via_DoubleEnded_SinglyLinkedList.");
		System.out.println("");
		hTable.display();
		System.out.println("");
		/**
		 * 2. Delete countries Cyprus, Kazakhstan, Hungary and Japan from the hash table
		 * by calling the delete method
		 **/
		System.out.println("");
		hTable.delete("Cyprus");
		hTable.delete("Kazakhstan");
		hTable.delete("Hungary");
		hTable.delete("Japan");
		/**
		 * 3. Search for countries Costa Rica, North Cyprus and Hungary by calling the
		 * find method. For the found country, print out its GDP per capita
		 */
		System.out.println("");
		hTable.find("Costa Rica");
		hTable.find("North Cyprus");
		hTable.find("Hungary");
		/**
		 * 4. delete countries Zambia, Canada, Egypt, Yemen, India and Singapore from
		 * the hash table, and display the hash table by calling the display method.
		 */

		System.out.println("");
		hTable.delete("Zambia");
		hTable.delete("Canada");
		hTable.delete("Egypt");
		hTable.delete("Yemen");
		hTable.delete("India");
		hTable.delete("Singapore");
		System.out.println("");
		hTable.display();

		/**
		 * 5. Print the number of empty cells and the number of cells with collision
		 */

		System.out.println("");
		hTable.printFreeAndCollisions();
		System.out.println("Exiting program...Goodbye!");
		// resource on this as I did have to look it up -->
		// https://stackoverflow.com/questions/2670956/how-to-quit-a-java-app-from-within-the-program
		Runtime.getRuntime().exit(0);// call to JVM to create a normal termination of the program

	}// end Main

	/**
	 * ReadFile method will read CSV file and call insert function to create BST
	 * Users path and bufferedReader to get the correct csv. Allows user two tries
	 * to get the name right otherwise it will crash Get's headers and lines -->
	 * then splits the values into an Array from that array the correct index is
	 * used to insert the values into the HashTable_via_DoubleEnded_SinglyLinkedList
	 * 
	 * @param fileName
	 * @param hTable
	 * @param userInput
	 */
	private static void readFile(String fileName, HashTable_via_DoubleEnded_SinglyLinkedList hTable, Scanner userInput) {
		while (!fileName.equalsIgnoreCase("Countries3.csv")) {
			System.out.println("Please input correct file name...should be 'Countries3.csv'... ");
			fileName = userInput.next();
			break;
		}
		Path findPath = Paths.get(fileName);
		try (BufferedReader buffy = Files.newBufferedReader(findPath)) {
			buffy.readLine();
			String line = buffy.readLine().trim();// get rid of any extra spaces with trim
			while (line != null) {
				// create array of the incoming file

				String[] BSTArray = line.split(",");

				String countryName = BSTArray[0];
				Double countryPop = Double.parseDouble(BSTArray[3]);
				Double countryGDP = Double.parseDouble(BSTArray[4]);
				Double gdpPerCapita = countryGDP / countryPop;
				// to calculate the gdp per capita divide the given countryGDP/countryPop;
				hTable.insert(countryName, gdpPerCapita);
				countCountries++;// keep track of the countries
				// System.out.println(countryName + " " + gdpPerCapita);
				// HTable.insert(countryName, gdpPerCapita);

				line = buffy.readLine();// null ends while loop
			}

		} catch (IOException e) {
			System.out.println("The file: " + e.getLocalizedMessage() + " was not found...");
			System.out.println("Only two chances are given, try again once you have found the correct filename...");
			e.printStackTrace();
		}
	}// end ReadFile

}// end Project 3
/**
 * hTable.hash("Australia"); 
 * hTable.hash("United States"); 
 * hTable.hash("South Africa");
 *  hTable.hash("Russian Federation");
 *  Used for testing to make sure the values were correct. 
 */
