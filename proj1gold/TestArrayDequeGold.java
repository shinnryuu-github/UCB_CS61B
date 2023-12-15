import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void randomTest(){
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        int count = 0, size = 0;
        String message = "";
        for (int i = 0; i < 1000; i++){
            int randomnumber = StdRandom.uniform(4);
            int randomadd = StdRandom.uniform(1000);
            Integer expected, actuall;
            if (randomnumber == 0){
                solution.addFirst(randomadd);
                student.addFirst(randomadd);
                size++;
                message += "addFirst(" + randomadd + ")\n";
            }
            else if (randomnumber == 1){
                solution.addLast(randomadd);
                student.addLast(randomadd);
                size++;
                message +="addLast(" + randomadd + ")\n";
            }
            else if (randomnumber == 2 && size != 0) {
                size--;
                expected = solution.removeFirst();
                actuall = student.removeFirst();
                message += "removeFirst()\n";
                assertEquals(message, expected, actuall);
            }
            else if (randomnumber == 2 && size == 0) {
                solution.addFirst(randomadd);
                student.addFirst(randomadd);
                size++;
                message += "addFirst(" + randomadd + ")\n";
            }
            else if (randomnumber == 3 && size != 0) {
                size--;
                expected = solution.removeLast();
                actuall = student.removeLast();
                message += "removeLast()\n";
                assertEquals(message, expected, actuall);
            }
            else {
                solution.addLast(randomadd);
                student.addLast(randomadd);
                size++;
                message += "addLast(" + randomadd + ")\n";
            }
        }
    }
}