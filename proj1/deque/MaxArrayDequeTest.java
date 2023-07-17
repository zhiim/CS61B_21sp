package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    // Compare string using the first char
    private class FirstComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    // Compare string using the second char
    private class SecondComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return Character.compare(a.charAt(1), b.charAt(1));
        }
    }

    /** Test the function of find max element using the internal comparator */
    @Test
    public void testInternalMax() {
        FirstComparator ac = new FirstComparator();
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(ac);
        mad.addFirst("abc");
        mad.addFirst("bcd");
        mad.addFirst("cad");
        assertEquals("cad", mad.max());
    }


    /** Test the function of find max element using the internal comparator */
    @Test
    public void testExternalMax() {
        SecondComparator bc = new SecondComparator();
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(bc);
        mad.addFirst("abc");
        mad.addFirst("bcd");
        mad.addFirst("cad");
        assertEquals("bcd", mad.max());
    }
}
