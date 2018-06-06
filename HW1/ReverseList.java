import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 * This class reads a file of text and prints the file to standard output in
 * reverse line order.
 */

public class ReverseList {
	

	public static void main(String[] args) {

		LinkedList<String> list = new LinkedList<String>();

		File file = null;

		try {
			file = new File(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("usage: ReverseList <filename>");
			System.exit(-1);
		}

		Scanner input = null;

		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(-1);
		}

		while (input.hasNext()) {
			String s1 = input.nextLine();
			list.addFirst(s1);
		}

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		input.close();

	}

}
