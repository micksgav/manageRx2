/**
 ***********************************************
 * @Author : Kyle McKay, modified by John Brown
 * @Originally made : Unknown
 * @Last Modified: January 21, 2024
 * @Description: This program is a StringList object with various methods to edit a linked list containing Strings as the payload
 ***********************************************
 */

package patientManagement;

/**
 * An object of type StringList represents a list of strings. Methods are
 * provided to insert a string into the list, to delete a string from the list,
 * and to check whether a given string occurs in the list. (For testing
 * purposes, a method is also provided that will return an array containing all
 * the strings in the list.) Strings that are inserted into the list must be
 * non-null. Inserting a null string will cause NullPointerExceptions when the
 * list is used in subsequent operations. Note that this class is certainly NOT
 * meant to be a full-featured List class. It is for demonstration only.
 */
public class PatientList {

	/**
	 * Internally, the list of strings is represented as a linked list of nodes
	 * belonging to the nested class Node. The strings in the list are stored in
	 * increasing order (using the order given by the compareTo() method from the
	 * string class, which is the same as alphabetical order if all the strings are
	 * made up of lower case letters).
	 */
	private static class Node {
		Patient info; // One of the infos in the list
		Node link; // Pointer to the node containing the link info.
					// In the last node of the list, link is null.

		// constructor sets info to a String value and link to either null or the next
		// item in the list
		Node(Patient info, Node link) {
			this.info = info;
			this.link = link;
		} // end Node

		// blank constructor does not set any values
		Node() {
		} // end Node
	} // end Node

	private Node head; // A pointer to the first node in the linked list.
						// If the list is empty, the value is null.

	// set the head to a new value
	public void setHead(Node newHead) {
		head = newHead;
	} // end setHead

	public Node getHead() {
		return head;
	} // end getHead

	// find a patient in the list by name and birthday
	public int[] findPatientByBirthday(String name, int birthMonth, int birthDay, int birthYear) {
		Node runner = head;
		int i = 0;
		int[] indexes = new int[this.numRecs()];
		for (int j = 0; j < indexes.length; j++) {
			indexes[j] = -1;
		} // end for
		int j = 0;
		for (runner = head; runner != null; runner = runner.link, i++) {

			if (runner.info.getName().toLowerCase().equals(name.toLowerCase())
					&& runner.info.getBirthYear() == birthYear // added lowercase check - gavin
					&& runner.info.getDateOfBirthDay() == birthDay && runner.info.getDateOfBirthMonth() == birthMonth) {
				indexes[j] = i;
				j++;

			} // end if
		} // end for
		return indexes;
	} // end findPatientByBirthday

	/*
	 * Method Name: isEmpty Author: John Brown Creation Date: November 28, 2023
	 * Modified Date: November 28, 2023 Description: returns true if the list is
	 * empty, and false if the list is not empty
	 *
	 * @Parameters: none
	 * 
	 * @Return Value: true or false for list emptiness Data Type: boolean
	 * Dependencies: none Throws/Exceptions: none
	 */
	public boolean isEmpty() {
		if (head == null) {
			return true;
		} // end if
		else {
			return false;
		} // end else
	} // end isEmpty

	/*
	 * Method Name: returnData Author: John Brown Creation Date: November 26, 2023
	 * Modified Date: November 26, 2023 Description: Returns the data at position in
	 * the list
	 *
	 * @Parameters: int position: the position in the linked list to get data for
	 * 
	 * @Return Value: runner.info: value at position in the list Data Type: String
	 * Dependencies: none Throws/Exceptions: none
	 */
	public Patient returnData(int position) {
		Node runner = head;
		if (position >= 0) {
			for (int i = 0; i < position; i++, runner = runner.link) {
			} // end for
			return runner.info;
		} // end if
		else {
			return null;
		} // end else
	} // end returnData

	/*
	 * Method Name: addToFront Author: John Brown Creation Date: November 23, 2023
	 * Modified Date: November 26, 2023 Description: Adds a new element to the start
	 * of the list
	 *
	 * @Parameters: String info: info to add to the front of the list
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void addToFront(Patient info) {
		head = new Node(info, head);
	} // end addToFront

	/*
	 * Method Name: numRecs Author: John Brown Creation Date: November 26, 2023
	 * Modified Date: November 26, 2023 Description: counts the number of elements
	 * in the list
	 *
	 * @Parameters: none
	 * 
	 * @Return Value: count: number of items in the list Data Type: int
	 * Dependencies: none Throws/Exceptions: none
	 */
	public int numRecs() {
		int count = 0; // number of records
		if (head == null) {
			return count;
		} // end if
		for (Node runner = head; runner != null; runner = runner.link) {
			count++;
		} // end for
		return count;
	} // end numRecs

	/*
	 * Method Name: insert Author: Kyle McKay Creation Date: Unknown Modified Date:
	 * November 26, 2023 Description: Insert a specified item to the list, keeping
	 * the list in order
	 *
	 * @Parameters: insertItem: the item that is to be inserted
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void insert(Patient insertItem) {
		Node newNode; // A Node to contain the new info.
		newNode = new Node();
		newNode.info = insertItem; // (N.B. newNode.link is null.)

		if (head == null) {
			// The new info is the first (and only) one in the list.
			// Set head to point to it.
			head = newNode;
		} // end if
		else if (head.info.getName().compareTo(insertItem.getName()) >= 0) {
			// The new info is less than the first info in the list,
			// so it has to be inserted at the head of the list.
			newNode.link = head;
			head = newNode;
		} // end else if
		else {
			// The new info belongs somewhere after the first info
			// in the list. Search for its proper position and insert it.
			Node runner; // A node for traversing the list.
			Node previous; // Always points to the node preceding runner.
			runner = head.link; // Start by looking at the SECOND position.
			previous = head;
			while (runner != null && runner.info.getName().compareTo(insertItem.getName()) < 0) {
				// Move previous and runner along the list until runner
				// falls off the end or hits a list element that is
				// greater than or equal to insertItem. When this
				// loop ends, previous indicates the position where
				// insertItem must be inserted.
				previous = runner;
				runner = runner.link;
			} // end while
			newNode.link = runner; // Insert newNode after previous.
			previous.link = newNode;
		} // end else

	} // end insert()

} // end StringList
