
/**
 ***********************************************
 * @Author : Kyle McKay, modified by John Brown
 * @Originally made : Unknown
 * @Last Modified: January 21, 2024
 * @Description: This program is a StringList object with various methods to edit a linked list containing Strings as the payload
 ***********************************************
 */

package PatientManagement;

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
public class PrescriptionList {

	/**
	 * Internally, the list of strings is represented as a linked list of nodes
	 * belonging to the nested class Node. The strings in the list are stored in
	 * increasing order (using the order given by the compareTo() method from the
	 * string class, which is the same as alphabetical order if all the strings are
	 * made up of lower case letters).
	 */
	private static class Node {
		Prescription info; // One of the infos in the list
		Node link; // Pointer to the node containing the link info.
					// In the last node of the list, link is null.

		// constructor sets info to a String value and link to either null or the next
		// item in the list
		Node(Prescription info, Node link) {
			this.info = info;
			this.link = link;
		} // end Node

		// blank constructor does not set any values
		Node() {
		} // end Node
	} // end Node

	private Node head; // A pointer to the first node in the linked list.
						// If the list is empty, the value is null.

	/*
	 * Method Name: toArray Author: John Brown Creation Date: January 3, 2024
	 * Modified Date: January 3, 2024 Description: converts PrsecriptionList to an
	 * array of Prescriptions
	 *
	 * @Parameters: none
	 * 
	 * @Return Value: array: contains complete list as Prescription Objects Data
	 * Type: Prescription[] Dependencies: Prescription Throws/Exceptions: none
	 */
	public Prescription[] toArray() {
		Prescription[] array = new Prescription[this.length()];
		Node runner = head;
		for (int i = 0; runner != null; i++, runner = runner.link) {
			array[i] = runner.info;
		} // end for
		return array;
	} // end toArray

	/*
	 * Method Name: toArray Author: John Brown Creation Date: January 3, 2024
	 * Modified Date: January 3, 2024 Description: deletes an item from the list
	 *
	 * @Parameters: Prescription prescription: prescription to delete
	 * 
	 * @Return Value: none Data Type: void Dependencies: toArray, Prescription,
	 * insert Throws/Exceptions: none
	 */
	public void delete(Prescription prescription) {
		Prescription[] array = this.toArray();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == prescription) {
				array[i] = null;
			} // end if
		} // end for
		head = null;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				this.insert(array[i]);
			} // end if
		} // end for
	} // end delete

	/*
	 * Method Name: atIndex Author: John Brown Creation Date: December 18, 2023
	 * Modified Date: December 18, 2023 Description: returns the prescription at the
	 * specified index
	 *
	 * @Parameters: int i: index to return at
	 * 
	 * @Return Value: Prescription at index i Data Type: Prescription Dependencies:
	 * none Throws/Exceptions: none
	 */
	public Prescription atIndex(int i) {
		Node runner = head;
		for (int k = 0; k < i; runner = runner.link, k++) {
		} // end for
		return runner.info;
	} // end atIndex

	/*
	 * Method Name: length Author: John Brown Creation Date: December 18, 2023
	 * Modified Date: December 18, 2023 Description: returns the length of the list
	 *
	 * @Parameters: none
	 * 
	 * @Return Value: length of list Data Type: int Dependencies: none
	 * Throws/Exceptions: none
	 */
	public int length() {
		int counter = 0;
		for (Node runner = head; runner != null; runner = runner.link) {
			counter++;
		} // end for
		return counter;
	} // end length

	// set the head to a new value
	public void setHead(Node newHead) {
		head = newHead;
	} // end setHead

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
	 * Method Name: addToFront Author: John Brown Creation Date: November 23, 2023
	 * Modified Date: November 26, 2023 Description: Adds a new element to the start
	 * of the list
	 *
	 * @Parameters: String info: info to add to the front of the list
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void addToFront(Prescription info) {
		head = new Node(info, head);
	} // end addToFront

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
	public void insert(Prescription insertItem) {
		Node newNode; // A Node to contain the new info.
		newNode = new Node();
		newNode.info = insertItem; // (N.B. newNode.link is null.)

		if (head == null) {
			// The new info is the first (and only) one in the list.
			// Set head to point to it.
			head = newNode;
		} // end if
		else if (head.info.getBrandName().compareTo(insertItem.getBrandName()) >= 0) {
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
			while (runner != null && runner.info.getBrandName().compareTo(insertItem.getBrandName()) < 0) {
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
