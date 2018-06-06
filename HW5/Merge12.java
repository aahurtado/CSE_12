/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;

public class Merge12 implements Sort12 {

	public <T extends Comparable<? super T>> void sort(List<T> list) {

		if (list == null)
			throw new NullPointerException("Null argument to sort");

		// Create the arrayList to insert into
		ArrayList<T> inputArray = new ArrayList<T>(list.size());
		ArrayList<T> tempArray = new ArrayList<T>(list.size() / 2);

		// Copy the data into the input array
		for (int i = 0; i < list.size(); i++)
			inputArray.add(i, list.get(i));

		internalMergeSort(inputArray, tempArray, 0, inputArray.size() - 1);

		// Copy the inserted array to unsorted List
		for (int i = 0; i < list.size(); i++)
			list.set(i, inputArray.get(i));
	}

	private <T extends Comparable<? super T>> void internalMergeSort(
			ArrayList<T> inputArray, ArrayList<T> tempArray, int first, int last) {

		if ((last - first + 1) <= 1) {
			return; // base case - partition is size 1
		}

		int mid = ((first + last + 1) / 2);
		internalMergeSort(inputArray, tempArray, first, mid - 1);
		internalMergeSort(inputArray, tempArray, mid, last);
		merge(inputArray, tempArray, first, mid, last);
	}

	private <T extends Comparable<? super T>> void merge(
			ArrayList<T> inputArray, ArrayList<T> tempArray, int first,
			int mid, int last) {

		int tempSize = last - first + 1;
		int insertIndex = first;
		int firstPartitionIndex = 0;
		int secondPartitionIndex = mid;

		for (int i = 0; i < (mid - first); i++)
			tempArray.add(i, inputArray.get(first + i));

		while ((firstPartitionIndex < (mid - first))
				&& (secondPartitionIndex <= last)) {

			if (tempArray.get(firstPartitionIndex).compareTo(
					inputArray.get(secondPartitionIndex)) < 0) {
				inputArray.set(insertIndex, tempArray.get(firstPartitionIndex));
				firstPartitionIndex++;
			} else {
				inputArray.set(insertIndex,
						inputArray.get(secondPartitionIndex));
				secondPartitionIndex++;
			}
			insertIndex++;
		}

		while (firstPartitionIndex < (mid - first)) {
			inputArray.set(insertIndex, tempArray.get(firstPartitionIndex));
			firstPartitionIndex++;
			insertIndex++;
		}

	}
}
// vim:ts=4:sw=4:sw=78
