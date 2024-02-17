import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int maxradix = 0;
        for (String s : asciis){
            if (s.length() > maxradix)
                maxradix = s.length();
        }
        String[] buffer = new String[asciis.length];
        System.arraycopy(asciis,0,buffer,0,asciis.length);
        Deque<String>[] sorted = new Deque[257];
        for (int i = 0; i < 257; i++)
            sorted[i] = new ArrayDeque<>();
        for (int i = 1; i <= maxradix; i++){
            for (String s : buffer){
                int index = maxradix - i;
                if (index >= s.length())
                    sorted[0].addLast(s);
                else{
                    sorted[(int)s.charAt(index)].addLast(s);
                }
            }
            int pos = 0;
            for (int j = 0; j < 257; j++){
                while (!sorted[j].isEmpty())
                    buffer[pos++] = sorted[j].removeFirst();
            }
        }
        return buffer;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args){
        String[] unsort = {"23", "2", "100", "1234", "3"};
        String[] sorted = sort(unsort);
        for (String s : sorted)
            System.out.print(s + " ");
    }
}
