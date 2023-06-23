package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes1() {
        IntList lst = IntList.of(1, 5, 100, 101, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 25 -> 100 -> 10201 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes2() {
        IntList lst = IntList.of(22, 331, 111, 33, 33);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("22 -> 109561 -> 111 -> 33 -> 33", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes3() {
        IntList lst = IntList.of(22, 0, 111, 33, 33);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("22 -> 0 -> 111 -> 33 -> 33", lst.toString());
        assertFalse(changed);
    }
}
