package bstmap;

import org.junit.Test;

public class BSTMapTest {
    @Test
    public void insertTest() {
        BSTMap<String, Integer> map = new BSTMap<>();
        map.put("h", 0);
        map.put("b", 1);
        map.put("z", 2);
        map.put("r", 3);
        // add an duplicate node
        map.put("b", 4);
        // get
        System.out.println(map.get("z"));
        System.out.println(map.get("a"));
        System.out.println(map.containsKey("a"));
        System.out.println(map.containsKey("b"));
        map.printInOrder();
    }
}
