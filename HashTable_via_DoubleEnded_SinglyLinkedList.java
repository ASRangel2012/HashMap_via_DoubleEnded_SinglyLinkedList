
/**
 * Project 3: Hash Table Implementation with double-ended singly linked lists using Separate Chaining to handle collisions
 * The HashTable_via_DoubleEnded_SinglyLinkedList class has two nested classes, private class Node and class DoubleEndedSinglyLinkedList. 
 * The private node class creates the object that will be stored in the hashTable and provides the nextNode for the singly linked list.
 * The public class DoubleEndendSinglyLinkedList allows the user to access to the head and tail nodes and specific methods --> see explanation above class 
 * <p> The Hash Table class allows the user to insert in the end of the double-ended singly linked list, delete a desired country, find a specific country and it's gdp per capita
 * as well as returning the amount of free spaces while reporting incidents of collisions. 
 * Allows the user to display the table as needed with a call to the display method. 
 * @author Angel Selva Rodriguez
 * @version 07/29/2020
 *
 */
public class HashTable_via_DoubleEnded_SinglyLinkedList {
	private class Node {
		String name;// countryNode name
		double gdpPerCapita;// CountryNode gdpPerCapita
		Node nextNode;// access to the nextNode in the SLL

		/**
		 * Constructor with parameters to create a new Country Node object 
		 * @param name
		 * @param gdpPerCapita
		 */
		public Node(String name, double gdpPerCapita) {
			this.name = name;
			this.gdpPerCapita = gdpPerCapita;
		}

		/**
		 * No parameter void print function which prints the node country object to specific widths as per print formatting 
		 */
		public void printNode() {
			System.out.printf("%-25s%,-20.2f\n", name, gdpPerCapita);
		}
	}// end node Class

	/**
	 * DoubleEndedSinglyLinkedList created is used for creating access to the head(first country object) in each individual collision that may occur upon insertion. 
	 * This is what allows the Hash Table class to use separate-chaining while inserting into the Hash Table.
	 * Provides access to tail or the last value inserted  --> This allows the user to insert in the last known position of the table instead of iterating thru the table and locating the null value to insert next.
	 * Provides method to insert and find a desired Node 
	 * @author Angel Selva Rodriguez
	 */
	class DoubleEndedSinglyLinkedList {
		Node head;// points to the first value
		Node tail;// points to the last value

		/**
		 * Constructor allows creation of the individual heads/tails of upon any given insertion/collision by using the Node country as the input parameter 
		 * Will create a head/tail reference inside the array used in the HashTable_via_DoubleEnded_SinglyLinkedList class
		 * @param country
		 */
		public DoubleEndedSinglyLinkedList(Node country) {
			head = country;
			tail = country;
		}

		/**
		 * Helper method used to properly insert into the hashTable. 
		 * First checks if the head is null, to make sure the initial value of the list is inserted, if it's not null then insert at the end
		 * Head and tail will call the nextNode to point --> to the next available space for insertion 
		 * @param countryNode
		 */
		public void insertLast(Node countryNode) {
			if (head == null) {
				// list is empty insert after first
				head.nextNode = countryNode;
			} else {
				tail.nextNode = countryNode;
				tail = countryNode;
			}
		}// end insertLast

		/**
		 * Helper Find method to locate a given country by the name. Create a node current to keep track of the head. 
		 * Using current, iterate thru as long as it is not null and if current matches the name while ignoring case there is a match and returns given country names gdp per capita
		 * @param name
		 * @return -1.0 if not found, otherwise will return correct GDP per capita 
		 */
		public double find(String name) {
			Node current = head;
			while (current != null) {
				if (current.name.compareToIgnoreCase(name) == 0) {
					return current.gdpPerCapita;
				} else {
					current = current.nextNode;
				}
			}
			return -1.0;
		}// end FIND

		public void remove(Node country, String name) {
			while (true) {
				if (country.nextNode == null) {
					System.out.print(name + " is not found, so it can not be deleted");
					return;
				} else if (country.nextNode.name.compareToIgnoreCase(name) == 0) {
					System.out.println(name + " has been deleted from the Hash Table.");
					country.nextNode = country.nextNode.nextNode;
					return;
				} else {
					country = country.nextNode;
				}
			}
		}// end REMOVE

	}// end double ended class

	// HashTable_via_DoubleEnded_SinglyLinkedList variables
	final static int HTSIZE = 311;// created as this is the final size of the table as per project specifications
	/*
	 * 	Countries_SinglyLinkedList is an array which is used to allow access to the countries being stored in the hashTable
	 * as well as pointers to head/tail and all methods as defined in the given class.
	 */
	DoubleEndedSinglyLinkedList[] COUNTRIES_SLL; //
	static int countCollisions;// keep track of collisions
	static int countEmpty;// keep track of empty

	/**
	 * No-arg constructor to create a new empty hashtable 
	 */

	HashTable_via_DoubleEnded_SinglyLinkedList() {
		COUNTRIES_SLL = new DoubleEndedSinglyLinkedList[HTSIZE];
	}

	/**
	 * Calls the private function hashHelper(name). The private function creates
	 * character array and iterates thru each given name char array to add up it's
	 * unicode values
	 * 
	 * array --> HashVal = uniCodeNameVal % HTSIZE;
	 * 
	 * @param String name --> given from input file
	 * @return hash value calculated from helper function
	 */
	public int hash(String name) {
		return hashHelper(name);
	}

	/**
	 * Private function to help calculate the hashValue. Simply creates a char[]
	 * array of the given string and will iterate thru each character and up the
	 * total to return it's unicode value hashVal is given by taking the resulting
	 * unicode value % HTSIZE
	 * 
	 * @param String name --> given from hash(name) function
	 * @return Integer hashValue
	
	 */
	private int hashHelper(String name) {
		int unicode = 0;
		char[] arrayName = name.toCharArray();
		for (int i = 0; i < arrayName.length; i++) {
			unicode += (int) arrayName[i];
			// System.out.println("Country: " + name + " at index: " + i + " numValue using
			// Character : " + unicode + " this char : " + arrayName[i]); USED FOR TESTING
		}
		// System.out.println("total Unicode thus far at : " + unicode + " for country:
		// " + name); USED FOR TESTING

		int hashVal = unicode % HTSIZE;
		// System.out.println("Country: " + name + " " + hashVal); USED FOR TESTING:
		return hashVal;
	}
	/*
	 * Note: Using unicode = Character.getNumericValue(arrayName[i]) does not yield
	 * the same result... getNumericValue is supposed to return the unicode
	 * representation of the char value... Research further to understand.
	 */

	/**
	 * Calls private function to check if the beginning of the COUNTRIES_SLL is null or not. If it is null, it will return true, otherwise false
	 * @param name
	 * @return true if null 
	 * @return false if not null
	 */
	public boolean isEmpty(String name) {
		return isEmptyHelper(name);
	}

	/**
	 * Private function to check if COUNTRIES_SLL IS NULL OR NOT
	 * @param name
	 * @return True/False depending on the hashed index value
	 */
	private boolean isEmptyHelper(String name) {
		return COUNTRIES_SLL[hash(name)] == null;
	}

	/**
	 * Creates a new Node country with correct parameters, checks if the SLL is empty 
	 * If is empty, insert first else call function insertLast to insert to the tail end of the list
	 * @param countryName
	 * @param gdpPerCapita
	 */
	public void insert(String countryName, double gdpPerCapita) {
		Node country = new Node(countryName, gdpPerCapita);
		int index = hash(countryName);
		if (isEmpty(countryName)) {
			COUNTRIES_SLL[index] = new DoubleEndedSinglyLinkedList(country);
		} else {
			COUNTRIES_SLL[index].insertLast(country);
		}
	}

	/**
	 * Uses private function helper to locate a given country provided by the String name to get the gdp per capita. 
	 * 
	 * @param name
	 * @return Either -1.0 if country not found or the correct gdp 
	 */
	public double find(String name) {
		return findHelper(name);
	}

	/**
	 * Checks if the SLL is empty, which would mean there is no country
	 * If not, uses function from DoubleEndedSinglyLinkedList to locate the given country by name
	 * @param name
	 * @return -1.0 or correct GDP per capita with correct return message 
	 */
	private double findHelper(String name) {
		double gdp;

		int hashTableIndex = hash(name);
		if (COUNTRIES_SLL[hashTableIndex] == null) {
			System.out.println(name + " is not found");
			return -1.0;
		} else {
			gdp = COUNTRIES_SLL[hashTableIndex].find(name);
			if (gdp != -1.0) {
				System.out.printf(name + " is found with a GDP per capita of " + "%,-20.2f\n", gdp);
			} else {
				System.out.println(name + " is not found");
				gdp = -1.0;
			}
			return gdp;
		}
	}

	/**
	 * Delete any given country by using the name of the country as a parameter.
	 * index is given value from hash function 
	 * Using this index, search using the COUNTRIES_SLL[index].head
	 * create a new temporary node named country to keep track of the head 
	 * If the head is null, return not found
	 * if the head is found and matches the name, return the name
	 * if not call remove fuction, input name and country to iterate thru the rest of the singly linked list. 
	 * @param name
	 * 
	 */
	public void delete(String name) {
		int index = hash(name);
		Node country = COUNTRIES_SLL[index].head;

		if (COUNTRIES_SLL[index] == null) {
			System.out.print(name + " is not found, so it can not be deleted");
			return;
		}
		if (COUNTRIES_SLL[index].head.name.compareToIgnoreCase(name) == 0) {
			System.out.println(name + " has been deleted from the Hash Table.");
			COUNTRIES_SLL[index].head = COUNTRIES_SLL[index].head.nextNode;
			return;
		}

		COUNTRIES_SLL[index].remove(country, name);
	}

	/**
	 * Function to display the Hash Table contents, iterate thru the list and for every empty value specify this and keep track of its' count
	 * Else, check the temp head value and keep track of the empty as well
	 * The countCollision node is used to keep track of the temp variable and simply iterate thru the nextNode of the countCollision node, not temp. Yields correct count of collisions
	 * Temp will iterate thru and print all values, which yields incorrect count.
	 * 
	 */
	public void display() {
		countCollisions = 0;
		countEmpty = 0;
		System.out.println("Hash Table content: ");
		Node temp;
		for (int i = 0; i < COUNTRIES_SLL.length; i++) {
			System.out.print(i + ". ");
			if (COUNTRIES_SLL[i] == null) {
				countEmpty++;
				System.out.println("\tEMPTY");
			} else {
				temp = COUNTRIES_SLL[i].head;
				if (temp == null) {
					countEmpty++;
					System.out.println("\tEMPTY");
				} else {
					Node countCollision = temp;// create a copy to be able to use later and keep correct display results
						if (countCollision.nextNode != null)
							countCollisions++;//21 --> change to while will show the number of countries that collided --> 28
							countCollision = countCollision.nextNode;
							
				} // will exit and needs another if statement to re-enter for the temp node
					// total collisions manual count is 28...
					// total empty manual count is 194
				if (temp != null) {// needed to re-enter to correct print out the remaining values
					while (temp != null) {
						// countCollisions++ == 145 here --> this was when I did not have the while loop
						// with the NODE COUNTCOLLISION
						System.out.print("\t");
						temp.printNode();
						temp = temp.nextNode;
					}
					// 120 countCollisions++; Incorrect, should be 28.
				}
			}
		}
	}// end display

	/**
	 * Calls the static integers and prints the following message to show the free spaces and collisions!
	 */
	public void printFreeAndCollisions() {
		System.out.println("There are currently " + countEmpty + " free spaces available and there are "
				+ countCollisions + " collisions in the hash table.");
	}
}// end HASHTABLE CLASS
