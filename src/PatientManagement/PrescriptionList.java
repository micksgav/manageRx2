package PatientManagement;



/**
 ***********************************************
 * @Author : Kyle McKay, modified by John Brown
 * @Originally made : Unknown
 * @Last Modified: December 16, 2023
 * @Description: This program is a StringList object with various methods to edit a linked list containing Strings as the payload
 ***********************************************
 */


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
	
	public Prescription[] toArray() {
		Prescription[] array = new Prescription[this.length()];
		Node runner = head;
		for (int i = 0; runner != null; i ++, runner = runner.link) {
			array[i] = runner.info;
		}
		return array;
	}
	
	public void delete(Prescription prescription) {
		Prescription[] array = this.toArray();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == prescription) {
				array[i] = null;
			}
		}
		head = null;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
			this.insert(array[i]);
			}
		}
	}
	
	public void delete(int index) {
		Node runner = head;
		if (index == 0 && head.link != null) {
			head = head.link;
		}
		else if (index == 0 && head.link == null) {
			head = null;
		}
		for (int i = 0; i < index-1; i ++, runner = runner.link) {
		}
		runner.link = runner.link.link;
	}

	public String[] returnInfo() {
		String[] all = new String[this.length()];
		Node runner = head;
		for (int i = 0; runner != null; runner = runner.link, i++) {
			all[i] = "Brand name: " + runner.info.getBrandName() + "\nBranderic name: " + runner.info.getBrandName() + "\nDate prescribed: " + runner.info.getDate() + "\nNumber of refills: " + runner.info.getRefills() + "\nQuantity: " + runner.info.getQuantity() + " pills\nDosage: " + runner.info.getDosage() + "mg\nInstructions: " + runner.info.getInstructions() + "\nPrescribed duration: " + runner.info.getDuration();
		}
		return all;
	}

	public Prescription returnPrescription(String drugName) {
		Node runner = head;
		for (int i = 0; runner != null; runner = runner.link, i++) {
			if (runner.info.getBrandName().equals(drugName)) {
				return runner.info;
			}
		}
		return null;
	}
	
	public Prescription atIndex(int i) {
		Node runner = head;
		for (int k = 0; k < i; runner = runner.link, k++) {
			
		}
		return runner.info;
	}
	
	public int length() {
		int counter = 0;
		for (Node runner = head; runner != null; runner = runner.link) {
			counter ++;
		}
		return counter;
	}

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
	 * Method Name: midToFront Author: John Brown Creation Date: November 26, 2023
	 * Modified Date: November 26, 2023 Description: Takes the element in the middle
	 * of the list and moves it to the front
	 *
	 * @Parameters: none
	 * 
	 * @Return Value: none Data Type: void Dependencies: numRecs, delete, addToFront
	 * Throws/Exceptions: none
	 */
	public void midToFront() {
		int halfPoint = this.numRecs() / 2;
		Node runner = head;
		for (int i = 0; i < halfPoint; i++, runner = runner.link) {
		} // end for
		Prescription info = runner.info;
		this.delete(runner.info.getBrandName());
		this.addToFront(info);
	} // end midToFront

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
		int count = 0;
		if (head == null) {
			return count;
		} // end if
		for (Node runner = head; runner != null; runner = runner.link) {
			count++;
		} // end for
		return count;
	} // end numRecs

	/*
	 * Method Name: printList Author: John Brown Creation Date: November 26, 2023
	 * Modified Date: November 26, 2023 Description: prints the list
	 *
	 * @Parameters: none
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void printList() {
		for (Node temp = head; temp != null; temp = temp.link) {
			System.out.println(temp.info);
		} // end for
	} // end printList

	/*
	 * Method Name: delete Author: Kyle McKay Creation Date: Unknown Modified Date:
	 * November 28, 2023 Description: Delete a specified item from the list, if that
	 * item is present. If multiple copies of the item are present in the list, only
	 * the first one is deleted
	 *
	 * @Parameters: deleteItem: the item that is to be deleted
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void delete(String deleteItem) {

		if (head == null) {
			// The list is empty, so it certainly doesn't contain deleteString.
			return;
		} // end if
		else if (head.info.getBrandName().equals(deleteItem)) {
			// The string is the first info of the list. Remove it.
			head = head.link;
			return;
		} // end else if
		else {
			// The string, if it occurs at all, is somewhere beyond the
			// first element of the list. Search the list.
			Node runner; // A node for traversing the list.
			Node previous; // Always points to the node preceding runner.
			runner = head.link; // Start by looking at the SECOND list node.
			previous = head;
			while (runner != null && runner.info.getBrandName().compareTo(deleteItem) < 0) {
				// Move previous and runner along the list until runner
				// falls off the end or hits a list element that is
				// greater than or equal to deleteItem. When this
				// loop ends, runner indicates the position where
				// deleteItem must be, if it is in the list.
				previous = runner;
				runner = runner.link;
			} // end while
			if (runner != null && runner.info.getBrandName().equals(deleteItem)) {
				// Runner points to the node that is to be deleted.
				// Remove it by changing the pointer in the previous node.
				previous.link = runner.link;
				return;
			} // end if
			else {
				// The info does not exist in the list.
				return;
			} // end else
		} // end else
	} // end delete()

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

