import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

/** This is a Heap Sort
 *  @author Philip Papadopoulos 
 *  @date 28 April 2014
 */
public class HeapSort12 implements Sort12
{
	public  <T extends Comparable<? super T>> void sort(List<T> list)
	{
		if ( list == null )
			throw new NullPointerException("Null argument to sort");

		// Create the arrayList that we will sort 
		Heap12<T> heap = new Heap12<T>(list.size(), false);

		// Copy the data into the min-heap
		for (int i = 0; i < list.size(); i++)
			heap.offer(list.get(i));

		// Copy the min-heap into the inputed list
		for (int i = 0; i < list.size(); i++)
			list.set(i, heap.poll());
	}
}
// vim:ts=4:sw=4:sw=78
