package tests;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.TreeSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import implementation.BinarySearchTree;
import implementation.GetDataIntoArrays;
import implementation.KeyValuePair;
import implementation.TreeIsEmptyException;
import implementation.TreeIsFullException;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {

	/* Remove the following test and add your tests */
	@Test
	public void test1() {
		String result = "======== Marker #1 ========\n" + "EMPTY TREE\n" + "Empty Tree?: true\n" + "\n"
				+ "======== Marker #2 ========\n" + "{Aman:1000}{Bob:50000}{Ronaldo:60}\n" + "Full Tree?: false\n"
				+ "Size: 3";
		String answer = "";
		System.out.println("======== Marker #1 ========");
		answer += "======== Marker #1 ========" + "\n";
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 5;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		System.out.println(bst);
		answer += bst + "\n";
		System.out.println("Empty Tree?: " + bst.isEmpty());
		answer += "Empty Tree?: " + bst.isEmpty() + "\n";
		System.out.println("\n======== Marker #2 ========");
		answer += "\n======== Marker #2 ========" + "\n";
		try {
			bst.add("Aman", 1000).add("Bob", 50000).add("Ronaldo", 60);
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
			answer += "full tree" + "\n";
		}
		System.out.println(bst);
		answer += bst + "\n";
		System.out.println("Full Tree?: " + bst.isFull());
		answer += "Full Tree?: " + bst.isFull() + "\n";
		System.out.println("Size: " + bst.size());
		answer += "Size: " + bst.size();

		assertTrue(answer.toString().equals(result.toString()));
	}

	@Test
	public void test2() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 5;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		System.out.println("\n======== Marker #3 ========");
		try {
			bst.add("Aman", 1000).add("Bob", 50000).add("Ronaldo", 60);
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}
		try {
			KeyValuePair<String, Integer> maximum = bst.getMaximumKeyValue();
			KeyValuePair<String, Integer> minimum = bst.getMinimumKeyValue();
			System.out.println("Maximum: " + maximum.getKey() + "/" + maximum.getValue());
			System.out.println("Minimum: " + minimum.getKey() + "/" + minimum.getValue());
		} catch (TreeIsEmptyException e) {
			System.out.println("empty tree");
		}
	}

	@Test
	public void test3() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 5;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		System.out.println("\n======== Marker #4 ========");
		try {
			KeyValuePair<String, Integer> maximum = bst.getMaximumKeyValue();
			KeyValuePair<String, Integer> minimum = bst.getMinimumKeyValue();
			System.out.println("Maximum: " + maximum.getKey() + "/" + maximum.getValue());
			System.out.println("Minimum: " + minimum.getKey() + "/" + minimum.getValue());
		} catch (TreeIsEmptyException e) {
			System.out.println("empty tree");
		}
	}

	@Test
	public void test4() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 5;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("Aman", 1000).add("Bob", 50000).add("Ronaldo", 60);
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}
		System.out.println("\n======== Marker #5 ========");
		KeyValuePair<String, Integer> found = bst.find("Ronaldo");
		System.out.println(found == null ? "NOT FOUND" : found.getKey() + "/" + found.getValue());

		System.out.println("\n======== Marker #6 ========");
		GetDataIntoArrays<String, Integer> callback = new GetDataIntoArrays<String, Integer>();
		bst.processInorder(callback);
		System.out.println("Keys: " + callback.getKeys());
		System.out.println("Values: " + callback.getValues());
	}

	@Test
	public void test5() {
		System.out.println("\n======== Marker #7 ========");
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 5;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("Aman", 1000).add("Bob", 50000).add("Ronaldo", 60);
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}
		BinarySearchTree<String, Integer> subTree = bst.subTree("Oliver", "Tracy");
		System.out.println("Tree: " + bst);
		System.out.println("SubTree: " + subTree);

		System.out.println("\n======== Marker #8 ========");
		TreeSet<Integer> leavesValuesSet = bst.getLeavesValues();
		System.out.println(leavesValuesSet);

		System.out.println("\n======== Marker #9 ========");
		try {
			bst.delete("Bob");
		} catch (TreeIsEmptyException e) {
			System.out.println("empty tree");
		}
		System.out.println(bst);
	}
}
