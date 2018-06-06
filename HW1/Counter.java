/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

/**
 * @version 1.0
 * @author Aaron Hurtado
 */

public class Counter {

	private int count = 0;
	private int step = 1;

	/**
	 * Create a counter initialized to zero, step increment of 1
	 */
	public Counter() {
		// Nothing to do here
	}

	/**
	 * Creates a counter initialized to zero, 
	 * @param theStep positive step increment for counter
	 */
	public Counter(int theStep) {
		int s;
		if ((s = Math.abs(theStep)) > 0)
			step = s;
	}

	/**
	 * Retrieve current value of counter 
	 * @return current value of counter
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Increment the counter by its step
	 */
	public void increment() {
		count += step;
	}

	/**
	 * decrement the counter by its step. Stop at zero
	 */
	public void decrement() {
		if (count > step)
			count -= step;
		else
			count = 0;
	}

	/**
	 * reset the counter to zero
	 */
	public void reset() {
		count = -1;
	}
}
