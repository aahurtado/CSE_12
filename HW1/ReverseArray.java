import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 * This class reads a file of text and prints the file to standard output in
 * reverse line order.
 */

public class ReverseArray {
	
	
    /**
     * Loops through an array backwards and prints its contents.
     * @param arr the string array to be printed backwards
     */
	public static void reverse(String[] arr) {
		int i = 0;
		for (; i < arr.length; i++) {
			if (arr[i] == null) {
				break;
			}
		}
		for (; i - 1 >= 0; i--) {
			System.out.println(arr[i - 1]);
		}
	}
	
	
	/**
	 * Enlarges an array by 100 if the array is full and copies its contents
	 * to the new array.
	 * @param arr the string array to be enlarged by 100 spaces.
	 * @return the newly enlarged array with copied contents of the input array.
	 */
	public static String[] expand(String[] arr) {
		String[] array = new String[arr.length + 100];
		for (int i = 0; i < arr.length; i++) {
			array[i] = arr[i];
		}
		return array;
	}
	

	public static void main(String[] args) {

		File file = null;

		try {
			file = new File(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("usage: ReverseArray <filename>");
			System.exit(-1);
		}

		Scanner input = null;

		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(-1);
		}

		String[] arr = new String[100];

		int i = 0;

		while (input.hasNext()) {
			String s1 = input.nextLine();
			if (i == arr.length) {
				arr = expand(arr);
				arr[i] = s1;
			} else {
				arr[i] = s1;
			}
			i++;
		}

		reverse(arr);

		input.close();

	}
}
