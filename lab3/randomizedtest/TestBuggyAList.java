package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        // Create a new AListNoResizing
        AListNoResizing<Integer> an = new AListNoResizing<>();
        // Create a new BuggyAList
        BuggyAList<Integer> ba = new BuggyAList<>();


        // Add three items to both lists
        an.addLast(4);
        ba.addLast(4);

        an.addLast(5);
        ba.addLast(5);

        an.addLast(6);
        ba.addLast(6);

        // Test
        assertEquals(an.removeLast(), ba.removeLast());
        assertEquals(an.removeLast(), ba.removeLast());
        assertEquals(an.removeLast(), ba.removeLast());
    }

    /**
     * Random Test
     */
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BL = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BL.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int sizeBL = BL.size();
                assertEquals(sizeBL, size);
            } else if (operationNumber == 2) {
                if (L.size() > 0) {
                    int least = L.getLast();
                    int leastBL = BL.getLast();
                    assertEquals(leastBL, least);
                }
            } else if (operationNumber == 3) {
                if (L.size() > 0) {
                    int removed = L.removeLast();
                    int removedBL = BL.removeLast();
                    assertEquals(removedBL, removed);
                }
            }
        }
    }
}
